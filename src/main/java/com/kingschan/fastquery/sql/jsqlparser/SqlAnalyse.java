package com.kingschan.fastquery.sql.jsqlparser;

import java.util.List;



/**
 * 
*  <pre>    
* 类名称：SqlAnalyse 
* 类描述：   SQL解析器
* 创建人：陈国祥   
* 创建时间：2014-6-5 上午10:42:33   
* 修改人：Administrator   
* 修改时间：2014-6-5 上午10:42:33   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface SqlAnalyse {
	/**
	 * 得到查询的列
	 * @return
	 */
	 List<String> getSelectColumns()throws Exception;
	/**
	 * 增加查询列
	 * @param columnsName
	 */
	 void addSelectColumns(String columnsName)throws Exception;
	/**
	 * 设置查询列
	 * @param columns
	 */
	 void setSelectColumns(List<String> columns)throws Exception;
	/**
	 * 得到where条件字符串
	 * @return
	 */
	 String getWhereCondition()throws Exception;

	/**
	 * 增加where查询条件
	 * @param condition
	 * @throws Exception
     */
	 void appendCondition(String condition)throws Exception;
	/**
	 * 增加模糊查询条件
	 * @param operaction 逻辑操作符
	 * @param columnName 字段名
	 * @param value	值
	 */
	 void appendLikeCondition(LogicOperaction operaction, String columnName, String value)throws Exception;
	/**
	 * 直接设置where 字符串
	 * @param condition
	 */
	 void setWhereCondition(String condition)throws Exception;
	/**
	 * 设置SQLServer 显示条数
	 * @param rowIndex
	 */
	 void setTop(long rowIndex)throws Exception;
	/**
	 * 得到排序
	 * @return
	 */
	 String getOrderByString()throws Exception;
	/**
	 * 得到排序的字段
	 * @return
	 */
	 List<String> getOrderByColumns()throws Exception;

	/**
	 * 添加排序的字段
	 * @param column_name
	 * @throws Exception
     */
	 void appendOrderColumns(String column_name)throws Exception;
	/**
	 * 设置所有排序字段
	 * @param columns
	 */
	 void setOrderColumns(List<String> columns)throws Exception;
	/**
	 * 分页
	 * @param pageIndex 页数
	 * @param pageSize	每页显示条数
	 * @throws Exception
	 */
	 void pagnation(Integer pageIndex, Integer pageSize)throws Exception;
	/**
	 * 统计
	 * @throws Exception
	 */
	 void count()throws Exception;
	
	
}
