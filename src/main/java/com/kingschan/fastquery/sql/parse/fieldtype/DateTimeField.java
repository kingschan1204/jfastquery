package com.kingschan.fastquery.sql.parse.fieldtype;

import com.kingschan.fastquery.sql.parse.AbstractField;
import com.kingschan.fastquery.util.RegexUtil;
import com.kingschan.fastquery.vo.SqlCondition;
/**
 * 
*  <pre>    
* 类名称：DateTimeField 
* 类描述：    日期字段  年-月-日 时：分：秒
* 创建人：陈国祥   (kingschan)
* 创建时间：2014-9-5 上午9:15:46   
* 修改人：Administrator   
* 修改时间：2014-9-5 上午9:15:46   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class DateTimeField extends AbstractField{


    /**
	 * validation
	 * @param condition
	 * @return
	 * @throws Exception
     */
	private boolean validation(SqlCondition condition)throws Exception {
		String operactor=condition.getOperator();
		if (null== RegexUtil.findStrByRegx(condition.getValue1().replaceAll("[^0-9:-]", " "), RegexUtil.regex_dateTime)) {
			throw new Exception(String.format("格式错误：%s 正确格式：[2010-01-01 00:00:00]", condition.getValue1()));			
		}else if (operactor.matches("(?i)between")) {
			return null==RegexUtil.findStrByRegx(condition.getValue2(), RegexUtil.regex_dateTime);
		}
		return true;
	}
	
	@Override
	protected String mysqlAnalysis(SqlCondition condition) throws Exception {
		//date_format(now(),'%Y-%c-%d %h:%i:%s')
		String value=condition.getValue1().replaceAll("[^0-9:-]", " ");	
		String Operator=condition.getOperator();//操作符
		String filed=condition.getSqlfiled();//字段
			
		if (validation(condition)) {
			if (Operator.matches("null|!null|空|非空")) {
				return String.format("%s %s",filed, AbstractField.Operator.get(Operator));
			}
			else if (Operator.equals("between")) {
				String value2=RegexUtil.findStrByRegx(condition.getValue2().replaceAll("[^0-9:-]", " "), RegexUtil.regex_dateTime);
				return String.format("%s between '%s' and '%s' ", filed,value,value2);
			}
			String operactor=AbstractField.Operator.get(Operator);
			return String.format("date_format(%s,'%%Y-%%m-%%d %%H:%%i:%%s')%s '%s'", filed,operactor,value);
		}
		return "";
		
		
	}

	@Override
	protected String oracleAnalysis(SqlCondition condition) throws Exception {
		String Operator=condition.getOperator();//操作符
		String filed=condition.getSqlfiled();//字段
		String value=RegexUtil.findStrByRegx(condition.getValue1().replaceAll("[^0-9:-]", " "), RegexUtil.regex_dateTime);		
		//lock_date >= to_date('2010-04-02','yyyy-mm-dd hh24:mi:ss');
		if (validation(condition)) {
			if (Operator.matches("null|!null|空|非空")) {
				return String.format("%s %s",filed, AbstractField.Operator.get(Operator));
			}
			else if (Operator.equals("between")) {
				String value2=RegexUtil.findStrByRegx(condition.getValue2().replaceAll("[^0-9:-]", " "), RegexUtil.regex_dateTime);
				return String.format("%s between '%s' and '%s' ", condition.getSqlfiled(),value,value2);
			}
			String operactor=AbstractField.Operator.get(Operator);
			return String.format("%s %s to_date('%s','yyyy-mm-dd hh24:mi:ss')", condition.getSqlfiled(),operactor,value);
		}
		return "";
		
	}

	@Override
	protected String sqlserverAnalysis(SqlCondition condition) throws Exception {	
		String value=RegexUtil.findStrByRegx(condition.getValue1().replaceAll("[^0-9:-]", " "), RegexUtil.regex_dateTime);		
		String Operator=condition.getOperator();//操作符
		String filed=condition.getSqlfiled();//字段
		//CONVERT(varchar(100), GETDATE(), 120):
		if (validation(condition)) {
			if (Operator.matches("null|!null|空|非空")) {
				return String.format("%s %s",filed, AbstractField.Operator.get(Operator));
			}else if (Operator.equals("between")) {
				String value2=RegexUtil.findStrByRegx(condition.getValue2().replaceAll("[^0-9:-]", " "), RegexUtil.regex_dateTime);
				return String.format("%s between '%s' and '%s' ", condition.getSqlfiled(),value,value2);
			}
			String operactor=AbstractField.Operator.get(Operator);
			return String.format("CONVERT(varchar(30),%s,120)%s'%s'", condition.getSqlfiled(),operactor,value);
		}
		return null;
		
	}

	
}
