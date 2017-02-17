package com.kingschan.fastquery.sql.parse;

import java.util.HashMap;
import java.util.Map;

import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.vo.SqlCondition;
/**
 * 抽象字段
 *@author kingschan
 *date:2013-4-24
 *{filterfield:"logpwd",relationship:"and",Operator:"eq",value:"f"}
 */
public abstract class AbstractField implements FieldTypeAnalysis{
	protected static  Map<String,String> Operator;
	
	
	
	static{
		Operator= new HashMap<String, String>();
		Operator.put("eq", "=");
		Operator.put("notEq", "<>");
		Operator.put("Less", "<");
		Operator.put("Greater", ">");
		Operator.put("GreaterEq", ">=");
		Operator.put("LessEq", "<=");		
		Operator.put("Contain", "like '%${value}%'");
		Operator.put("likeLeft", "like '${value}%'");
		Operator.put("likeRight", "like '%${value}'");
		Operator.put("in", "in");
		Operator.put("null", "is null");
		Operator.put("!null", "is not null");
		//添加对中文的支持
		Operator.put("等于", "=");
		Operator.put("不等于", "<>");
		Operator.put("大于", ">");
		Operator.put("小于", "<");
		Operator.put("大于等于", ">=");
		Operator.put("小于等于", "<=");	
		Operator.put("包含", "like '%${value}%'");
		Operator.put("左包含", "like '${value}%'");
		Operator.put("右包含", "like '%${value}'");
		Operator.put("集合", "in");
		Operator.put("空", "is null");
		Operator.put("非空", "is not null");
	}
	/**
	 * 得到操作符
	 * @param opeactor
	 * @return
	 */
	public static String getOperactor(String opeactor){
		return Operator.get(opeactor);
	}
	/**
	 * 是否为支持的操作符
	 * @param operactor
	 * @return
	 */
	public static boolean isOperactor(String operactor){
		return Operator.containsKey(operactor);
	}
	
	public String Analysis(SqlCondition condition, DbType dbtype) throws Exception {
	    String product=null;
        switch (dbtype) {
        case MYSQL:
            product=this.mysqlAnalysis(condition);
            break;
        case SQLSERVER:
            product=this.sqlserverAnalysis(condition);
            break;
        case ORACLE:
            product=this.oracleAnalysis(condition);
            break;
        default:            
            break;
        }
        return product;
	}

	/**
	 *
	 * @param condition
	 * @return
	 * @throws Exception
     */
	protected abstract String sqlserverAnalysis(SqlCondition condition)throws Exception;

	/**
	 *
	 * @param condition
	 * @return
	 * @throws Exception
     */
	protected abstract String mysqlAnalysis(SqlCondition condition)throws Exception;

	/**
	 *
	 * @param condition
	 * @return
	 * @throws Exception
     */
	protected abstract String oracleAnalysis(SqlCondition condition)throws Exception;
}
