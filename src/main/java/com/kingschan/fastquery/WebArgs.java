package com.kingschan.fastquery;
/**
 * 前台传入有效web参数
 * @author fedora
 *date:2013-8-8
 */
public class WebArgs {
	/**
	 * 分页页码
	 * easyui:page
	 * miniui:
	 */
	public static final String Pageindex="offset";//"pageIndex"; 
	/**
	 * 每页显示条数
	 * miniui:pageSize
	 * easyui:rows
	 */
	public static final String pageSize="limit";
	/**
	 * 序列化MD5值 
	 */
	public static final String Target="target";
	/**
	 * 复杂查询时的方案
	 */
	public static final String Filter="filter";
	/**
	 * 排序的字段值  
	 * miniui:sortField
	 * easyui:sort
	 */
	public static final String Sort="sort";// 
	/**
	 * 排序的方式
	 * miniui:sortOrder
	 * easyui:order
	 */
	public static final String Order="order";// 
	/**
	 * Action 逻辑处理
	 */
	public static final String Action="action";	
	/**
	 * 模板
	 */
	public static final String Template="template";
	/**
	 * 2013-12-18 
	 * author :kingschan   
	 * remarks:disable simple query power
	 */
	
	//简单查询时的单个where条件字段
	public static final String Filterfield="filterfield";
	//简单查询时逻辑运算符  如 and or not any
	public static final String Relationship="relationship";
	//简单查询时的操作符  如  = > < like
	public static final String Operator="Operator";
	//简单查询时查询字段的值 
	public static final String Value="value";
	//简单查询时字段类型
	public static final String Type="type";
	
	/**
	 * 导出文件时的名字
	 */
	public static final String Filename="filename";
	/**
	 * 导出文件时的列标题
	 */
	public static final String Title="title";
	/**
	 * 导出文件时禁止导出的列
	 */
	public static final String Disablecloums="disablecloums";
	/**
	 * 导出文件时选择的列
	 */
	public static final String Choosefield="choosefield";
	/**
	 * 导出文件时选择的数据行
	 */
	public static final String Ids="ids";
	
	
	
	
	
}
