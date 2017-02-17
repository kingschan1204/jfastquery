package com.kingschan.fastquery.sql.connection;

import java.sql.Connection;


import com.kingschan.fastquery.sql.AbstractConnection;
import com.kingschan.fastquery.util.StringUtil;

/**
 * get connection factory
 * @author kingschan
 *date:2013-6-25
 */
public class ConnectionFactory {
	/**
	 * builder connection
	 * @param jdbcClass  sqltag jdbcconnection class name
	 * @param conn  easytag.properties class name
	 * @return
	 * @throws Exception 
	 */
	public static Connection getConn(String jdbcClass,AbstractConnection conn) throws Exception{
		Connection con=null;
		if (StringUtil.replaceSpaceLine(jdbcClass).isEmpty()) {
			con = conn.getConnection();
		}else{
			AbstractConnection absconn= (AbstractConnection) Class.forName(jdbcClass).newInstance();
			con=absconn.getConnection();
		}
		return con;
	}
}
