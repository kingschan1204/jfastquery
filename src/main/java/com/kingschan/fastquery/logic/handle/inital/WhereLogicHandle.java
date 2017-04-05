package com.kingschan.fastquery.logic.handle.inital;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.kingschan.fastquery.conf.FastQueryConfigure;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.parse.QueryArgsAnalysisFactory;
import com.kingschan.fastquery.sql.parse.QueryArgsAnalysisFactory.FiledType;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import com.kingschan.fastquery.sql.dto.SqlCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * @author kingschan
 *date:2014-8-8
 *</pre>
 */
public class WhereLogicHandle implements LogicHandle {
    private static Logger log = LoggerFactory.getLogger(WhereLogicHandle.class);
    /**
     * 将json传成对象
     * @param json
     * @return
     * @throws JSONException
     */
    private SqlCondition getCondition(JSONObject json) throws JSONException{
        String logic=json.has("logic")?json.getString("logic"):"";
        //defOperator  快捷查询
        String operator=json.has("operator")?json.getString("operator"):(json.has("defOperator")?json.getString("defOperator"):"");
        String table=json.has("table")?json.getString("table"):"";
        String field=json.has("field")?json.getString("field"):"";
        String value=json.has("value")?json.getString("value"):"";
        String type=json.has("type")?json.getString("type"):"";
        String value2=json.has("value2")?json.getString("value2"):null;
        SqlCondition sc = new SqlCondition(
                logic,
                table.isEmpty()?field:String.format("%s.%s", table,field),
                type,
                operator,
                value,
                value2
        );
        return sc;
    }
    
    public DataTransfer doLogic(Map<String, Object> args, DataTransfer dts, Connection con,DbType type) throws Exception {
        FastQueryConfigure configure =FastQueryConfigure.getInstance();
        StringBuffer filter = new StringBuffer();
        if(args.containsKey(configure.getArgs().getFilter())){
            //组合查询
            String jsonstr=java.net.URLDecoder.decode(args.get(configure.getArgs().getFilter()).toString(), "UTF-8");
            if (null==jsonstr||jsonstr.replaceAll("\\s*", "").isEmpty()) {
                jsonstr="[]"; 
            }
            JSONArray filters = new JSONArray(jsonstr);
            //解决逻辑冲突
            if (filters.length()>0) {
                JSONArray temp = new JSONArray();
              //  temp.put(0,new JSONObject("{logic:'and',field:'('}"));
                for (int i = 0; i < filters.length(); i++) {
                    temp.put(filters.get(i));
                }
              //  temp.put(new JSONObject("{logic:'and',field:')'}"));
                filters=temp;
            }
            log.debug("组合查询：{}", filters);
            //取出每个条件
            for (int i=0;i<filters.length();i++) {
                SqlCondition condition=getCondition(filters.getJSONObject(i));
                boolean parentheses=condition.getField().matches("\\(|\\)");//是否为括号
                String relationShip=condition.getLogic();//逻辑运算符
                String where_filter=null;
                if (!parentheses) {
                    //不是括号进行条件解析
                    FiledType f=FiledType.valueOf(condition.getType().toUpperCase());//字段类型
                    where_filter =QueryArgsAnalysisFactory.getAnalysis(f).Analysis(condition, type);//解析出来的条件
                }//如果是括号
                else {
                    String s=String.format("%s%s",condition.getField().matches("\\)")?"":relationShip,condition.getField());
                    filter.append(i==1?s.replaceFirst("(?i)and|or", ""):s);
                }
                if (relationShip.matches("(?i)and|or")) {
                        if (filter.toString().replaceAll("\\s*", "").matches(".*\\($"))
                            filter.append(where_filter);
                        else if (null!=where_filter&&!where_filter.isEmpty()) {
                            filter.append(String.format("%s %s", relationShip,where_filter));
                        }
                            
                }
                    
            }
            
        }
        if (filter.length()>0) {
            dts.setWhere(filter.toString());
        }
        String sort=null;
        if (args.containsKey(configure.getArgs().getSort())&&!args.get(configure.getArgs().getSort()).toString().isEmpty()) {
            sort=String.format("%s %s", args.get(configure.getArgs().getSort()),args.get(configure.getArgs().getOrder()));
        }
        Integer pageSize = args.containsKey(configure.getArgs().getPageSize())?Integer.valueOf(args.get(configure.getArgs().getPageSize()).toString()):null;
        Integer pageindex = args.containsKey(configure.getArgs().getPageIndex())?Integer.valueOf(args.get(configure.getArgs().getPageIndex()).toString()):null;
        /********************************************************************************************/ 
        dts.setPageIndex(pageindex);
        dts.setPageSize(pageSize);
        if(null!=sort&&sort.trim().length()>0){
            List<String> lis = new ArrayList<String>();
            lis.add(sort);
            dts.setOrder(lis);
        }
        return dts;
    }
    
    
    
}
