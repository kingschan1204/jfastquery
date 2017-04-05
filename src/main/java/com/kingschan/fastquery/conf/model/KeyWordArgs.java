package com.kingschan.fastquery.conf.model;

/**
 * 前台传入有效web参数
 *
 * @author kings.chan
 *  date:2013-8-8
 */
public class KeyWordArgs {
    /**
     * 分页页码
     */
    public String pageIndex;
    /**
     * 每页显示条数
     */
    public String pageSize;
    /**
     * 复杂查询时的方案
     */
    public String filter;
    /**
     * 排序的字段值
     */
    public String sort;//
    /**
     * 排序的方式
     */
    public String order;

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

	/*
    //Action 逻辑处理
	public  String Action="action";

	//简单查询时的单个where条件字段
	public  String Filterfield="filterfield";
	//简单查询时逻辑运算符  如 and or not any
	public  String Relationship="relationship";
	//简单查询时的操作符  如  = > < like
	public  String Operator="Operator";
	//简单查询时查询字段的值 
	public  String Value="value";
	//简单查询时字段类型
	public  String Type="type";
	
	*//**
     * 导出文件时的名字
     *//*
	public  String Filename="filename";
	*//**
     * 导出文件时的列标题
     *//*
	public  String Title="title";
	*//**
     * 导出文件时禁止导出的列
     *//*
	public  String Disablecloums="disablecloums";
	*//**
     * 导出文件时选择的列
     *//*
	public  String Choosefield="choosefield";
	*//**
     * 导出文件时选择的数据行
     *//*
	public  String Ids="ids";*/


}
