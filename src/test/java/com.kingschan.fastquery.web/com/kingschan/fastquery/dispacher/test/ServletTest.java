package com.kingschan.fastquery.web.com.kingschan.fastquery.dispacher.test;

import com.kingschan.fastquery.logic.handle.dispacher.LogicHandleDispacher;
import com.kingschan.fastquery.logic.handle.inital.ScanArgsLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.WhereLogicHandle;
import com.kingschan.fastquery.logic.handle.query.PaginationQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.TotalLogicHandle;
import com.kingschan.fastquery.output.impl.GridOutPut;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.util.ServletUtil;
import com.kingschan.fastquery.sql.dto.SqlCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ServletTest extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        LogicHandleDispacher dispa = new LogicHandleDispacher(req, resp);
        SqlCommand cmd =null;
//        if (target.matches("[a-zA-Z0-9]{32}")) {
//           // cmd = ActionUtil.getCmdById(target);
//        }else{
            //xml
            cmd = new SqlCommand();
            cmd.setSql("select * from blog_website");
            cmd.setDBtype(DbType.MYSQL);
//        }
        dispa.handleDispacher(ServletUtil.getParameterMap(req),cmd, new GridOutPut(),
            new Class[]{
                WhereLogicHandle.class,
                ScanArgsLogicHandle.class,
                TotalLogicHandle.class,
                PaginationQueryLogicHandle.class
            });
    }
}
