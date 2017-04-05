package com.kingschan.fastquery.sql.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 逻辑处理数据载体
 * @author kingschan
 *date:2013-6-27
 */
public class DataTransfer implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer pageIndex;
	private Integer pageSize;
	private List<String> order;
	private String where;
	private List<?> queryResult;//查询返回的数据
	private long total;//总数
	private Map<String, Object> queryNameArgs;//sql语句中用#{x x x}的参数
	private Object[] queryIndexArgs;//sql语句中?
	private long startTime;
	private long endTime;
	private Set<String> disableColumns;//禁用列
	private String chooseField;//导出时选择的字段
	private Set<String> chooseIds;//导出时选择行的id集合
	private long affected;//增删改操作受影响行数
	private String sql;
	private Map<String, Object> properties;//扩展属性
	
	public DataTransfer(){
		properties= new HashMap<String, Object>();
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
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex =pageIndex;
		
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<String> getOrder() {
		return order;
	}
	public void setOrder(List<String> order) {
		this.order = order;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	
	
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Map<String, Object> getQueryNameArgs() {
		return queryNameArgs;
	}
	public void setQueryNameArgs(Map<String, Object> queryNameArgs) {
		this.queryNameArgs = queryNameArgs;
	}
	public Object[] getQueryIndexArgs() {
		return queryIndexArgs;
	}
	public void setQueryIndexArgs(Object[] queryIndexArgs) {
		this.queryIndexArgs = queryIndexArgs;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public Set<String> getDisableColumns() {
		return disableColumns;
	}
	public void setDisableColumns(Set<String> disableColumns) {
		this.disableColumns = disableColumns;
	}
	public List<?> getQueryResult() {
		return queryResult;
	}
	public void setQueryResult(List<?> queryResult) {
		this.queryResult = queryResult;
	}
	public String getChooseField() {
		return chooseField;
	}
	public void setChooseField(String chooseField) {
		this.chooseField = chooseField;
	}
	public Set<String> getChooseIds() {
		return chooseIds;
	}
	public void setChooseIds(Set<String> chooseIds) {
		this.chooseIds = chooseIds;
	}
	public long getAffected() {
		return affected;
	}
	public void setAffected(long affected) {
		this.affected = affected;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
}
