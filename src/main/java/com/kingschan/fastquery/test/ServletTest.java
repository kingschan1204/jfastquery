package com.kingschan.fastquery.test;

import com.kingschan.fastquery.logic.handle.dispacher.LogicHandleDispacher;
import com.kingschan.fastquery.logic.handle.inital.ScanArgsLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.WhereLogicHandle;
import com.kingschan.fastquery.logic.handle.query.PaginationQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.TotalLogicHandle;
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


        LogicHandleDispacher dispa = new LogicHandleDispacher(req, resp);
        SqlCommand cmd =null;
//        if (target.matches("[a-zA-Z0-9]{32}")) {
//           // cmd = ActionUtil.getCmdById(target);
//        }else{
            //xml
            cmd = new SqlCommand();
            cmd.setSql("select * from blog_request_log a limit 10");
            cmd.setDBtype(DbType.MYSQL);
//        }
        dispa.handleDispacher(map,cmd, new StandardOutPut(),
            new Class[]{
                WhereLogicHandle.class,
                ScanArgsLogicHandle.class,
                TotalLogicHandle.class,
                PaginationQueryLogicHandle.class
            });
    }
}
