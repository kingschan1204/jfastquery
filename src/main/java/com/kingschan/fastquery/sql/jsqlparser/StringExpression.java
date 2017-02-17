package com.kingschan.fastquery.sql.jsqlparser;

import net.sf.jsqlparser.expression.StringValue;


/**
 * 
 * <pre>    
* 类名称：StringExpression 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2015-7-14 下午6:31:03   
* 修改人：Administrator   
* 修改时间：2015-7-14 下午6:31:03   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class StringExpression extends StringValue {

    // 去掉单引号
    private boolean removeQuoteMark;

    /**
     * 是否去掉单引号
     * 
     * @param escapedValue
     * @param removeQuoteMark
     */
    public StringExpression(String escapedValue, boolean removeQuoteMark) {
        super(escapedValue);
        this.removeQuoteMark = removeQuoteMark;
        // romoving "'" at the start and at the end
        if (!removeQuoteMark) {
            setValue(escapedValue.replaceAll("^[',\"]{1}|[',\"]{1}$", ""));
        } else {
            setValue(escapedValue);
        }

    }

    @Override
    public String toString() {
        if (removeQuoteMark) {
            return getValue();
        } else {
            return String.format("'%s'", getValue());
        }
    }

}
