package com.kingschan.fastquery.logic.handle.execute;

import java.sql.Connection;
import java.util.Map;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.jsqlparser.DefaultSqlParser;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.util.StringUtil;
import com.kingschan.fastquery.vo.DataTransfer;
/**
 * jdbc操作处理(增，删除，改)
 * @author kingschan
 *date:2013-09-29
 */
public class ExecuteJdbcLogicHandle implements LogicHandle {

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
        String finalSQL=sqb.getSql();
        if (!StringUtil.null2Empty(sqb.getWhere()).isEmpty()) {
            DefaultSqlParser gsp = new DefaultSqlParser(finalSQL, type);
            gsp.appendCondition(sqb.getWhere());
            finalSQL=gsp.toString();
        }
        long affected = JdbcTemplete.executeSQL(con, finalSQL);
        sqb.setAffected(affected);
        return sqb;
    }
	

}
