package com.kingschan.fastquery.web.com.kingschan.fastquery.dispacher.test;

import com.kingschan.fastquery.logic.handle.dispacher.LogicHandleDispacher;
import com.kingschan.fastquery.logic.handle.inital.BuildVariableLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildConditionLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteMapQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildPagingLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteTotalLogicHandle;
import com.kingschan.fastquery.sql.dto.SqlCondition;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import com.kingschan.fastquery.sql.dto.SqlCommand;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingschan on 2017/4/1.
 */
public class QueryDispacherTest {
    private static Logger log = LoggerFactory.getLogger(QueryDispacherTest.class);

    public static void main(String[] args) {
        List<SqlCondition> conditions=new ArrayList<SqlCondition>();
        conditions.add(new SqlCondition("and","a.req_datetime","DATE","bw","2017-01-01","2017-02-01"));
        String filter=JSONArray.fromObject(conditions).toString();
        //查询方案所有传入参数
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("filter",filter);//查询条件
        map.put("sort","req_datetime");//排序字段
        map.put("order","desc");//排序的方式
        map.put("limit","10");//显示条数
        map.put("offset","1");//第几页

        map.put("blog","kingschan");//传入变量blog


        SqlCommand cmd = new SqlCommand();
        cmd.setSql("select req_ip,count(1) total from blog_request_log a where a.req_blog='${blog}'  group by req_ip");
        cmd.setDBtype(DbType.MYSQL);
        DataTransfer dt=  new LogicHandleDispacher().handleDispacher(map,cmd,new Class[]{
                BuildConditionLogicHandle.class,
                BuildVariableLogicHandle.class,
                BuildPagingLogicHandle.class,
                ExecuteTotalLogicHandle.class,
                ExecuteMapQueryLogicHandle.class
        });
        log.info("{}", JSONObject.fromObject(dt));
    }
}
