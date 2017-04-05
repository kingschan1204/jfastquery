package com.kingschan.fastquery.sql.connection;

import java.sql.Connection;


import com.kingschan.fastquery.conf.FastQueryConfigure;
import com.kingschan.fastquery.sql.AbstractConnection;
import com.kingschan.fastquery.util.StringUtil;

/**
 * get connection factory
 * @author kingschan
 *date:2013-6-25
 */
public class ConnectionFactory {
	/**
	 *
	 * @param jdbcClass
	 * @return
	 * @throws Exception
     */
	public static Connection getConn(String jdbcClass) throws Exception{
		Connection con=null;
		if (StringUtil.replaceSpaceLine(jdbcClass).isEmpty()) {
			con = FastQueryConfigure.getInstance().getDefaultCon();
		}else{
			AbstractConnection absconn= (AbstractConnection) Class.forName(jdbcClass).newInstance();
			con=absconn.getConnection();
		}
		return con;
	}
}
