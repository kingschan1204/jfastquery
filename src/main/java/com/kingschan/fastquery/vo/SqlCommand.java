package com.kingschan.fastquery.vo;

import com.kingschan.fastquery.sql.jsqlparser.DbType;

import java.util.HashMap;
import java.util.Map;




/**
 * sql语句
 * 
 * @author kingschan 2014-03-24
 * 
 */
public class SqlCommand implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private DbType DBtype;

	private String jdbcConnection;

	private String sql;

	private Map<String, Object> properties;
	
	
	public SqlCommand(){
		properties=new HashMap<String, Object>();
	}
	
	/**
	 * 增加一个属性
	 * @param key
	 * @param value
	 */
	public void addProperties(String key,Object value){
		properties.put(key, value);
	}
	/**
	 * 删除一个属性
	 * @param key
	 */
	public void romoveProperties(String key ){
		properties.remove(key);
	}
	/**
	 * 得到一个属性
	 * @param key
	 * @return
	 */
	public Object getProperties(String key){
		return properties.get(key);
	}
	public DbType getDBtype() {
		return DBtype;
	}

	public void setDBtype(DbType dBtype) {
		DBtype = dBtype;
	}

	public String getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(String jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
