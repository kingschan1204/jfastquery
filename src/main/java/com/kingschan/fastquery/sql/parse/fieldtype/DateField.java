package com.kingschan.fastquery.sql.parse.fieldtype;

import com.kingschan.fastquery.sql.parse.AbstractField;
import com.kingschan.fastquery.util.RegexUtil;
import com.kingschan.fastquery.vo.SqlCondition;
/**
 * 
*  <pre>    
* 类名称：DateField (年月日)
* 类描述：   日期字段
* 创建人：陈国祥   (kingschan)
* 创建时间：2013-4-24 
* 修改人：Administrator   
* 修改时间：2013-4-24   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class DateField extends AbstractField{

	/**
	 * validation
	 * @param condition
	 * @return
	 * @throws Exception
     */
	private boolean validation(SqlCondition condition)throws Exception {
		String operactor=condition.getOperator();
		if (null== RegexUtil.findStrByRegx(condition.getValue1(), RegexUtil.regex_date)) {
			throw new Exception(String.format("格式错误：%s 正确格式：[2010-01-01]", condition.getValue1()));			
		}else if (operactor.matches("(?i)between")) {
			return null==RegexUtil.findStrByRegx(condition.getValue2(), RegexUtil.regex_date);
		}
		return true;
	}
	
	@Override
	protected String mysqlAnalysis(SqlCondition condition) throws Exception {
		//date_format(field,'%Y-%m-%d')
		String value=RegexUtil.findStrByRegx(condition.getValue1(), RegexUtil.regex_date);		
		String Operator=condition.getOperator();//操作符
		String filed=condition.getSqlfiled();//字段
			
		if (validation(condition)) {
			if (Operator.matches("null|!null|空|非空")) {
				return String.format("%s %s",filed, AbstractField.Operator.get(Operator));
			}
			else if (Operator.equals("between")) {
				String value2=RegexUtil.findStrByRegx(condition.getValue2(), RegexUtil.regex_date);
				return String.format("%s between '%s' and '%s' ", filed,value,value2);
			}
			String operactor=AbstractField.Operator.get(Operator);
			return String.format("date_format(%s,'%%Y-%%m-%%d')%s '%s'", filed,operactor,value);
		}
		return "";
		
		
	}

	@Override
	protected String oracleAnalysis(SqlCondition condition) throws Exception {
		String Operator=condition.getOperator();//操作符
		String filed=condition.getSqlfiled();//字段
		String value=RegexUtil.findStrByRegx(condition.getValue1(), RegexUtil.regex_date);		
		//lock_date >= to_date('2010-04-02','YYYY-mm-dd');
		if (validation(condition)) {
			if (Operator.matches("null|!null|空|非空")) {
				return String.format("%s %s",filed, AbstractField.Operator.get(Operator));
			}
			else if (Operator.equals("between")) {
				String value2=RegexUtil.findStrByRegx(condition.getValue2(), RegexUtil.regex_date);
				return String.format("%s between '%s' and '%s' ", condition.getSqlfiled(),value,value2);
			}
			String operactor=AbstractField.Operator.get(Operator);
			return String.format("%s %s to_date('%s','YYYY-mm-dd')", condition.getSqlfiled(),operactor,value);
		}
		return "";
		
	}

	@Override
	protected String sqlserverAnalysis(SqlCondition condition) throws Exception {	
		String value=RegexUtil.findStrByRegx(condition.getValue1(), RegexUtil.regex_date);		
		String Operator=condition.getOperator();//操作符
		String filed=condition.getSqlfiled();//字段
		//CONVERT(varchar(100), GETDATE(), 23):
		if (validation(condition)) {
			if (Operator.matches("null|!null|空|非空")) {
				return String.format("%s %s",filed, AbstractField.Operator.get(Operator));
			}else if (Operator.equals("between")) {
				String value2=RegexUtil.findStrByRegx(condition.getValue2(), RegexUtil.regex_date);
				return String.format("%s between '%s' and '%s' ", condition.getSqlfiled(),value,value2);
			}
			String operactor=AbstractField.Operator.get(Operator);
			return String.format("CONVERT(varchar(10),%s,23)%s'%s'", condition.getSqlfiled(),operactor,value);
		}
		return null;
		
	}

	
}
