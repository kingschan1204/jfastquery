package com.kingschan.fastquery.sql.parse.fieldtype;

import com.kingschan.fastquery.sql.parse.AbstractField;
import com.kingschan.fastquery.util.RegexUtil;
import com.kingschan.fastquery.util.StringUtil;
import com.kingschan.fastquery.sql.dto.SqlCondition;
/**
 *数字类型解析
 * @author kingschan
 *date:2013-4-24
 */
public class NumberField extends AbstractField{

	private String generic(SqlCondition condition) throws Exception{
		String value=condition.getValue();
		String field =condition.getField();
		String Operator=condition.getOperator();//操作符
		StringBuffer sb = new StringBuffer();
		sb.append(field).append(" ");
		if (condition.getOperator().matches("Contain|likeLeft|likeRight")) {
            return "";
        }else if(Operator.matches("...")){
            sb.append(" in (").append(StringUtil.convertStrToSqlInstr(value, true)).append(")");
        }else  if (Operator.matches("nl|nnl")) {
            sb.append(AbstractField.Operator.get(Operator));
        }else if (null==value||!value.matches(RegexUtil.regex_number)) {
			return "";
		}else{
			sb.append(AbstractField.getOperactor(condition.getOperator())).append(value);
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
