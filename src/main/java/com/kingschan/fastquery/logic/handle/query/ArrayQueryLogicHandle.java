package com.kingschan.fastquery.logic.handle.query;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.jsqlparser.DefaultSqlParser;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.util.StringUtil;
import com.kingschan.fastquery.vo.DataTransfer;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
/**
 * 普通查询
 * @author kingschan
 *2013-09-22
 */
public class ArrayQueryLogicHandle implements LogicHandle {

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
        if (!StringUtil.null2Empty(sqb.getWhere()).isEmpty()) {
            DefaultSqlParser dsp = new DefaultSqlParser(sqb.getSql(),type);
            dsp.appendCondition(sqb.getWhere());
            sqb.setSql(dsp.toString());
        }
        System.err.println(sqb.getSql());
        List<Object[]> lis= JdbcTemplete.queryForListArray(con, sqb.getSql());
        sqb.setQueryResult(lis);
        return sqb;
    }
    

}
