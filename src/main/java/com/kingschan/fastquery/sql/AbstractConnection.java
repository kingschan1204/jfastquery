package com.kingschan.fastquery.sql;

import java.sql.Connection;


/**
 * 
 * <pre>
 *@author kingschan
 *@date:2012-11-1
 *@description:return sql connection
 *</pre>
 */
public interface AbstractConnection {
	/**
	 * get connection
	 * @return
	 * @throws Exception
     */
	Connection getConnection() throws Exception;
}
