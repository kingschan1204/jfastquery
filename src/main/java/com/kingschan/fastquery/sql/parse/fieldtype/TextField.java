package com.kingschan.fastquery.sql.parse.fieldtype;

import com.kingschan.fastquery.sql.parse.AbstractField;
import com.kingschan.fastquery.util.StringUtil;
import com.kingschan.fastquery.sql.dto.SqlCondition;

/**
 * Text
 * @author kingschan
 *date:2013-4-24
 */
public class TextField extends AbstractField{


	/**
	 * 通用条件分析
	 * @param condition
	 * @return
	 * @throws Exception
     */
	private String generic(SqlCondition condition) throws Exception{
		String value=condition.getValue();//值
		String Operator=condition.getOperator();//操作符
		String filed=condition.getField();//字段
		
		if (StringUtil.null2Empty(value).isEmpty()) {
			return "";
		}
		value=java.net.URLDecoder.decode(value, "UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append(filed).append(" ");		
		if (Operator.matches("c|sw|ew")) {
			sb.append(AbstractField.Operator.get(Operator).replace("${value}", value));
		}else if (Operator.matches("nl|nnl")) {
			sb.append(AbstractField.Operator.get(Operator));
		}else if(Operator.equals("...")){
			sb.append(" in (").append(StringUtil.convertStrToSqlInstr(value, false)).append(")");
		}
		else{
			sb.append(AbstractField.Operator.get(Operator)).append("'").append(value).append("'");
		}
		return sb.toString();
		
	}
	
	
	@Override
	protected String mysqlAnalysis(SqlCondition condition) throws Exception {		
		return this.generic(condition);
	}

	@Override
	protected String oracleAnalysis(SqlCondition condition) throws Exception {		
		return this.generic(condition);
	}

	@Override
	protected String sqlserverAnalysis(SqlCondition condition) throws Exception {		
		return this.generic(condition);
	}


}
