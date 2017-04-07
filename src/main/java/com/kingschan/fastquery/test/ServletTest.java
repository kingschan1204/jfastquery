package com.kingschan.fastquery.test;

import com.kingschan.fastquery.logic.handle.dispacher.WebLogicHandleDispacher;
import com.kingschan.fastquery.logic.handle.inital.BuildVariableLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildConditionLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildPagingLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteMapQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteTotalLogicHandle;
import com.kingschan.fastquery.output.impl.StandardOutPut;
import com.kingschan.fastquery.sql.dto.SqlCondition;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.util.ServletUtil;
import com.kingschan.fastquery.sql.dto.SqlCommand;
import net.sf.json.JSONArray;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServletTest extends HttpServlet{

    private static final long serialVersionUID = 1L;

    //http://localhost/jfastquery/test?offset=1&limit=30&sort=req_datetime&order=desc
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        //查询方案所有传入参数
        Map<String,Object> map =ServletUtil.getParameterMap(req);
        List<SqlCondition> conditions= new ArrayList<SqlCondition>();
        conditions.add( new SqlCondition("and","a.req_datetime","DATE","bw","2017-01-01","2017-02-01"));
        conditions.add( new SqlCondition("and","a.req_blog","STRING","eq","kingschan",null));
        JSONArray jons=JSONArray.fromObject(conditions);
        map.put("filter",jons);//查询条件
        map.put("sort","id");//排序字段
        map.put("order","desc");//排序的方式
        map.put("limit","10");//显示条数
        map.put("offset","1");//第几页


        WebLogicHandleDispacher dispa = new WebLogicHandleDispacher(req, resp);
        SqlCommand cmd = new SqlCommand();
        cmd.setSql("select * from blog_request_log a limit 10");
        cmd.setDBtype(DbType.MYSQL);
        dispa.handleDispacher(map,cmd, new StandardOutPut(),
            new Class[]{
                BuildConditionLogicHandle.class,
                BuildVariableLogicHandle.class,
                BuildPagingLogicHandle.class,
                ExecuteTotalLogicHandle.class,
                ExecuteMapQueryLogicHandle.class
            });
    }
}
