package com.kingschan.fastquery.logic.handle.query;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.sql.dto.DataTransfer;
/**
 * Map查询
 * @author kingschan
 *2013-09-22
 */
public class ExecuteMapQueryLogicHandle implements LogicHandle {

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
        List<Map<String, Object>> lis= JdbcTemplete.queryForListMap(con, sqb.getSql());
        sqb.setQueryResult(lis);
        return sqb;
    }
	

}
