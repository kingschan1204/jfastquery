package com.kingschan.fastquery.conf.ds.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.kingschan.fastquery.conf.Configure;
/**
 * 
*    
* 类名称：DruidDatasourceConfigure   
* 类描述：   
* 创建人：kings.chan
* 创建时间：2016-9-10 上午11:58:50   
* 修改人：   
* 修改时间：
* 项目：EasyQuery
* 修改备注：   
* @version    
*
 */
public class DruidDatasourceConfigure implements Configure{

	@Override
	public DataSource getDataSource() throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("url", "jdbc:mysql://localhost:3306/blog");
		properties.put("username", "root");
		properties.put("password", "root");
		properties.put("filters", "stat");
		properties.put("maxActive", "20");
		properties.put("initialSize", "1");
		properties.put("maxWait", "60000");
		properties.put("minIdle", "1");
		properties.put("timeBetweenEvictionRunsMillis", "60000");
		properties.put("minEvictableIdleTimeMillis", "300000");
		properties.put("testWhileIdle", "true");
		properties.put("testOnBorrow", "false");
		properties.put("testOnReturn", "false");
		properties.put("poolPreparedStatements", "true");
		properties.put("maxOpenPreparedStatements", "20");
//		DruidDataSource ds = (DruidDataSource) ;
		return DruidDataSourceFactory.createDataSource(properties);
	}

}
