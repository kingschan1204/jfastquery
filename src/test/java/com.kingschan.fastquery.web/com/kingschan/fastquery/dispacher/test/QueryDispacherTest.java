package com.kingschan.fastquery.web.com.kingschan.fastquery.dispacher.test;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.logic.handle.inital.ScanArgsLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.WhereLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ArrayQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.MapQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.PaginationQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.TotalLogicHandle;
import com.kingschan.fastquery.sql.connection.ConnectionFactory;
import com.kingschan.fastquery.sql.dto.SqlCondition;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import com.kingschan.fastquery.sql.dto.SqlCommand;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingschan on 2017/4/1.
 */
public class QueryDispacherTest {
    private Logger log = LoggerFactory.getLogger(QueryDispacherTest.class);
    private static Map<Class, LogicHandle> map ;
    static{
        map = new HashMap<Class, LogicHandle>();
        map.put(ScanArgsLogicHandle.class, new ScanArgsLogicHandle());
        map.put(WhereLogicHandle.class, new WhereLogicHandle());
        map.put(ArrayQueryLogicHandle.class, new ArrayQueryLogicHandle());
        map.put(MapQueryLogicHandle.class, new MapQueryLogicHandle());
        map.put(PaginationQueryLogicHandle.class, new PaginationQueryLogicHandle());
        map.put(TotalLogicHandle.class, new TotalLogicHandle());
    }


    /**
     * 逻辑处理调度
     * @param cmd sql命令
     * @param handles	逻辑处理执行顺序
     * @throws Exception
     */
    public void handleDispacher(Map<String, Object> req_args, SqlCommand cmd,Class<LogicHandle>[] handles)  {
        Connection conn=null;
        DataTransfer dt =new DataTransfer();
        dt.setSql(cmd.getSql());

        try {
            conn = ConnectionFactory.getConn(cmd.getJdbcConnection());
            for (Class c : handles) {
                dt =map.get(c).doLogic(req_args, dt, conn, cmd.getDBtype());
            }
            JSONObject json =JSONObject.fromObject(dt);
            log.debug("{}",json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcTemplete.closeDB(null, null, conn);
        }

    }

    public static void main(String[] args) {
        //查询条件
        JSONArray filter = new JSONArray();
        //filter.add(JSONObject.fromObject("{\"\"}"));
        JSONObject condition;
        //括号测试
        condition=new JSONObject();
        condition.put("field","(");
        filter.add(condition);

        condition=new JSONObject();
        condition.put("logic","and"); // AND & OR 运算符
        condition.put("operator","eq");//WHERE 子句中的运算符
        condition.put("table","a");//表别名
        condition.put("field","req_blog");//字段
        condition.put("value","kingschan");//值
        condition.put("value2","");
        condition.put("type","STRING");
        filter.add(condition);
        // or 测试
        condition=new JSONObject();
        condition.put("logic","or"); // AND & OR 运算符
        condition.put("operator","eq");//WHERE 子句中的运算符
        condition.put("table","a");//表别名
        condition.put("field","req_blog");//字段
        condition.put("value","about");//值
        condition.put("value2","");
        condition.put("type","STRING");
        filter.add(condition);

        //括号测试
        condition=new JSONObject();
        condition.put("field",")");//字段
        filter.add(condition);


       /* // 日期测试
        condition=new JSONObject();
        condition.put("logic","and"); // AND & OR 运算符
        condition.put("operator","gte");//WHERE 子句中的运算符
        condition.put("table","a");//表别名
        condition.put("field","req_datetime");//字段
        condition.put("value","2017-03-30");//值
        condition.put("value2","");
        condition.put("type","DATETIME");
        filter.add(condition);

        // 日期测试
        condition=new JSONObject();
        condition.put("logic","and"); // AND & OR 运算符
        condition.put("operator","lte");//WHERE 子句中的运算符
        condition.put("table","a");//表别名
        condition.put("field","req_datetime");//字段
        condition.put("value","2017-04-01");//值
        condition.put("value2","");
        condition.put("type","DATETIME");
        filter.add(condition);*/

        // 集合测试
        condition=new JSONObject();
        condition.put("logic","and"); // AND & OR 运算符
        condition.put("operator","...");//WHERE 子句中的运算符
        condition.put("table","a");//表别名
        condition.put("field","req_url");//字段
        condition.put("value","/,/lable_lis");//值
        condition.put("value2","");
        condition.put("type","STRING");
        filter.add(condition);

        //between 测试
        condition=new JSONObject();
        condition.put("logic","and"); // AND & OR 运算符
        condition.put("operator","bw");//WHERE 子句中的运算符
        condition.put("table","a");//表别名
        condition.put("field","req_datetime");//字段
        condition.put("value","2017-01-01");//值
        condition.put("value2","2017-02-01");
        condition.put("type","DATE");
        filter.add(condition);

        SqlCondition sc = new SqlCondition("and","a.req_datetime","DATE","bw","2017-01-01","2017-02-01");

        //查询方案所有传入参数
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("filter","["+sc.toString()+"]");//查询条件
        map.put("sort","id");//排序字段
        map.put("order","desc");//排序的方式
        map.put("limit","10");//显示条数
        map.put("offset","1");//第几页


        SqlCommand cmd = new SqlCommand();
        cmd.setSql("select * from blog_request_log a ");
        cmd.setDBtype(DbType.MYSQL);
        new QueryDispacherTest().handleDispacher(map,cmd,new Class[]{
                WhereLogicHandle.class,
                ScanArgsLogicHandle.class,
                TotalLogicHandle.class,
                PaginationQueryLogicHandle.class
        });
    }
}
