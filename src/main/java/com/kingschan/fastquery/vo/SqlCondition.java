package com.kingschan.fastquery.vo;
/**
 * 
*  <pre>    
* 类名称：SqlCondition 
* 类描述：   sql查询条件
* 创建人：陈国祥   (kingschan)
* 创建时间：2014-8-4 上午11:02:39   
* 修改人：Administrator   
* 修改时间：2014-8-4 上午11:02:39   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class SqlCondition {
    
	private String logic;//逻辑操作符
	private String sqlfiled;//sql查询字段 如有表名格式 如：a.name
	private String fieldtype;//字段类型
	private String operator;//操作符 like = > < <> in...
	private String value1;//值 
	private String value2;//如果操作符是 between 则此值启用
	
	
	
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public String getSqlfiled() {
		return sqlfiled;
	}
	public void setSqlfiled(String sqlfiled) {
		this.sqlfiled = sqlfiled;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getFieldtype() {
		return fieldtype;
	}
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
	
	
	
}
