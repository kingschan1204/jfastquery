package com.kingschan.fastquery.logic.handle.query;

import java.sql.Connection;
import java.util.Map;

import com.kingschan.fastquery.conf.FastQueryConfigure;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.jsqlparser.DefaultSqlParser;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.vo.DataTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 纺计总计录条数
 * @author fedora
 *date:2013-8-19
 *</pre>
 */
public class TotalLogicHandle implements LogicHandle {
	private static Logger log = LoggerFactory.getLogger(TotalLogicHandle.class);

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
        FastQueryConfigure configure =FastQueryConfigure.getInstance();
        /**
         * 判断是否为分页模式
         */
        if (args.containsKey(configure.getArgs().getPageIndex())&&args.containsKey(configure.getArgs().getPageSize())) {
            DefaultSqlParser sp = new DefaultSqlParser(sqb.getSql(),type);
            //构建where条件
            sp.appendCondition(sqb.getWhere());
            sp.count();
            log.debug("pagination:{}",sp.toString());
            Object total = JdbcTemplete.UniqueQuery(con, sp.toString());
            if (null!=total) {
                sqb.setTotal(Long.valueOf(total.toString())); 
            }
        }else{
            log.error("page and rows not found... ...so abort execute total query");
        }
        return sqb;
    }
	

	
}
