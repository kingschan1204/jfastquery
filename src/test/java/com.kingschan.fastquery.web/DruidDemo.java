package com.kingschan.fastquery.web;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;


public class DruidDemo {

	public static void main(String[] args) throws Exception {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("url", "jdbc:mysql://localhost:3306/blog");
		properties.put("username", "root");
		properties.put("password", "kingschan");
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
		DruidDataSource ds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
		System.out.println(ds.getConnection().isOracle());
	}
}
