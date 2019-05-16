package com.kingschan.fastquery.logic.handle.inital;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingschan.fastquery.conf.FastQueryConfigure;
import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import com.kingschan.fastquery.sql.dto.SqlCondition;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.parse.QueryArgsAnalysisFactory;
import com.kingschan.fastquery.sql.parse.QueryArgsAnalysisFactory.FiledType;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * @author kingschan
 *date:2014-8-8
 *</pre>
 */
@Slf4j
public class BuildConditionLogicHandle implements LogicHandle {
    /**
     * 将json传成对象
     * @param json
     * @return
     */
    private SqlCondition getCondition(JSONObject json) throws Exception {
        String logic=json.containsKey("logic")?json.getString("logic"):"";
        //defOperator  快捷查询
        String operator=json.containsKey("operator")?json.getString("operator"):(json.containsKey("defOperator")?json.getString("defOperator"):"");
        String table=json.containsKey("table")?json.getString("table"):"";
        String field=json.containsKey("field")?json.getString("field"):"";
        String value=json.containsKey("value")?json.getString("value"):"";
        String type=json.containsKey("type")?json.getString("type"):"";
        String value2=json.containsKey("value2")?json.getString("value2"):null;
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
            String jsonstr=args.get(configure.getArgs().getFilter()).toString();
            if (null==jsonstr||jsonstr.replaceAll("\\s*", "").isEmpty()) {
                jsonstr="[]"; 
            }
            JSONArray filters = JSONArray.parseArray(jsonstr);
            //解决逻辑冲突
            if (filters.size()>0) {
                JSONArray temp = new JSONArray();
              //  temp.put(0,new JSONObject("{logic:'and',field:'('}"));
                for (int i = 0; i < filters.size(); i++) {
                    temp.add(filters.get(i));
                }
              //  temp.put(new JSONObject("{logic:'and',field:')'}"));
                filters=temp;
            }
            log.debug("组合查询：{}", filters);
            //取出每个条件
            for (int i=0;i<filters.size();i++) {
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
