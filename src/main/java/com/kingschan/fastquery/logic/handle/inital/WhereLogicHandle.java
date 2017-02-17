package com.kingschan.fastquery.logic.handle.inital;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kingschan.fastquery.sql.jsqlparser.DbType;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kingschan.fastquery.WebArgs;
import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.parse.QueryArgsAnalysisFactory;
import com.kingschan.fastquery.sql.parse.QueryArgsAnalysisFactory.FiledType;
import com.kingschan.fastquery.vo.DataTransfer;
import com.kingschan.fastquery.vo.SqlCondition;
/**
 * <pre>
 * @author kingschan
 *date:2014-8-8
 *</pre>
 */
public class WhereLogicHandle implements LogicHandle {
    private static Logger log = Logger.getLogger(WhereLogicHandle.class);
    public static final String default_condition_key="0xfa";
    public static final String constraint_key="0xfb";
    /**
     * 将json传成对象
     * @param json
     * @return
     * @throws JSONException
     */
    private SqlCondition getCondition(JSONObject json) throws JSONException{
        SqlCondition sc = new SqlCondition();       
        String logic=json.has("logic")?json.getString("logic"):"";
        //defOperator  快捷查询
        String operator=json.has("operator")?json.getString("operator"):(json.has("defOperator")?json.getString("defOperator"):"");
        String table=json.has("table")?json.getString("table"):"";
        String field=json.has("field")?json.getString("field"):"";
        String value=json.has("value")?json.getString("value"):"";
        String type=json.has("type")?json.getString("type"):"";
        sc.setLogic(logic);
        sc.setOperator(operator);
        sc.setSqlfiled(table.isEmpty()?field:String.format("%s.%s", table,field));
        sc.setValue1(value);
        sc.setFieldtype(type);
        if (json.has("value2")) {
            sc.setValue2(json.getString("value2"));
        }
        return sc;
    }
    
    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,DbType type) throws Exception {
        StringBuffer filter = new StringBuffer();
        if(args.containsKey(WebArgs.Filter)){
            //组合查询
            String jsonstr=java.net.URLDecoder.decode(args.get(WebArgs.Filter).toString(), "UTF-8");
            if (null==jsonstr||jsonstr.replaceAll("\\s*", "").isEmpty()) {
                jsonstr="[]"; 
            }
            JSONArray filters = new JSONArray(jsonstr);
            //解决逻辑冲突
            if (filters.length()>0) {
                JSONArray temp = new JSONArray();
                temp.put(0,new JSONObject("{logic:'and',field:'('}"));
                for (int i = 0; i < filters.length(); i++) {
                    temp.put(filters.get(i));
                }
                temp.put(new JSONObject("{logic:'and',field:')'}"));
                filters=temp;
            }
            log.debug(String.format("组合查询：%s", filters));           
            //取出每个条件
            for (int i=0;i<filters.length();i++) {
                SqlCondition condition=getCondition(filters.getJSONObject(i));
                boolean parentheses=condition.getSqlfiled().matches("\\(|\\)");//是否为括号
                String relationShip=condition.getLogic();//逻辑运算符
                String where_filter=null;
                if (!parentheses) {
                    //不是括号进行条件解析
                    FiledType f=FiledType.valueOf(condition.getFieldtype().toUpperCase());//字段类型
                    where_filter =QueryArgsAnalysisFactory.getAnalysis(f).Analysis(condition, type);//解析出来的条件
                }
                if (relationShip.matches("(?i)and|or")) {
                    if (parentheses) {
                        String s=String.format("%s%s",condition.getSqlfiled().matches("\\)")?"":relationShip,condition.getSqlfiled());
                        filter.append(i==1?s.replaceFirst("(?i)and|or", ""):s);
                    }else{
                        if (filter.toString().replaceAll("\\s*", "").matches(".*\\($")) 
                            filter.append(where_filter);
                        else if (null!=where_filter&&!where_filter.isEmpty()) {
                            filter.append(String.format("%s %s", relationShip,where_filter));
                        }
                            
                    }
                }
                    
            }
            
        }
        if (filter.length()>0) {
            sqb.setWhere(filter.toString());
        }
        String sort=null;
        if (args.containsKey(WebArgs.Sort)&&!args.get(WebArgs.Sort).toString().isEmpty()) {
            sort=String.format("%s %s", args.get(WebArgs.Sort),args.get(WebArgs.Order));
        }
        Integer pageSize = args.containsKey(WebArgs.pageSize)?Integer.valueOf(args.get(WebArgs.pageSize).toString()):null;
        Integer pageindex = args.containsKey(WebArgs.Pageindex)?Integer.valueOf(args.get(WebArgs.Pageindex).toString())/pageSize:null;
        /********************************************************************************************/ 
        sqb.setPageIndex(pageindex);
        sqb.setPageSize(pageSize);
        if(null!=sort&&sort.trim().length()>0){
            List<String> lis = new ArrayList<String>();
            lis.add(sort);
            sqb.setOrder(lis);
        }
        return sqb;
    }
    
    
    
}
