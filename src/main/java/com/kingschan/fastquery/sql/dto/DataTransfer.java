package com.kingschan.fastquery.sql.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 逻辑处理数据载体
 * kingschan
 * 20170407
 */
public class DataTransfer implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer pageIndex;
    private Integer pageSize;
    private List<String> order;
    private String where;
    private List<?> queryResult;//查询返回的数据
    private long total;//总数
    private long startTime;
    private long endTime;
    private String sql;//sql语句
    private String totalSql;//汇总sql语句
    private Map<String, Object> properties;//扩展属性

    public DataTransfer() {
        properties = new HashMap<String, Object>();
    }

    /**
     * 增加一个属性
     *
     * @param key
     * @param value
     */
    public void addProperties(String key, Object value) {
        properties.put(key, value);
    }

    /**
     * 删除一个属性
     *
     * @param key
     */
    public void romoveProperties(String key) {
        properties.remove(key);
    }

    /**
     * 得到一个属性
     *
     * @param key
     * @return
     */
    public Object getProperties(String key) {
        return properties.get(key);
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;

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

    public List<?> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<?> queryResult) {
        this.queryResult = queryResult;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTotalSql() {
        return totalSql;
    }

    public void setTotalSql(String totalSql) {
        this.totalSql = totalSql;
    }

}
