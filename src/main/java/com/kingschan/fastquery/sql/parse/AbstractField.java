package com.kingschan.fastquery.sql.parse;

import java.util.HashMap;
import java.util.Map;

import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.dto.SqlCondition;
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
		Operator.put("neq", "<>");
		Operator.put("gt", ">");
		Operator.put("lt", "<");
		Operator.put("gte", ">=");
		Operator.put("lte", "<=");
		Operator.put("c", "like '%${value}%'");
		Operator.put("sw", "like '${value}%'");
		Operator.put("ew", "like '%${value}'");
		Operator.put("...", "in");
		Operator.put("nl", "is null");
		Operator.put("nnl", "is not null");
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
