package com.kingschan.fastquery.logic.handle.query;

import java.sql.Connection;
import java.util.Map;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 纺计总计录条数
 * @author fedora
 *date:2013-8-19
 *</pre>
 */
public class ExecuteTotalLogicHandle implements LogicHandle {
	private static Logger log = LoggerFactory.getLogger(ExecuteTotalLogicHandle.class);

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer dt, Connection con,
            DbType type) throws Exception {
        if (null!=dt.getTotalSql()&&!dt.getTotalSql().isEmpty()) {
            Object total = JdbcTemplete.UniqueQuery(con, dt.getTotalSql());
            if (null!=total) {
                dt.setTotal(Long.valueOf(total.toString()));
            }
        }
        return dt;
    }
	

	
}
