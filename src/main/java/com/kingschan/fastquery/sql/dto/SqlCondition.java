package com.kingschan.fastquery.sql.dto;

import net.sf.json.JSONObject;

/**
 * <pre>
 * 类名称：SqlCondition
 * 类描述：   sql查询条件
 * 创建人：陈国祥   (kingschan)
 * 创建时间：2014-8-4 上午11:02:39
 * 修改人：kingschan
 * 修改时间：2017-4-7
 * 修改备注：
 * @version V2.0
 * </pre>
 */
public class SqlCondition {


    private String logic;//逻辑操作符
    private String field;//sql查询字段 如有表名格式 如：a.name
    private String type;//字段类型
    private String operator;//操作符 like = > < <> in...
    private String value;//值
    private String value2;//如果操作符是 between 则此值启用

    //init

    public SqlCondition() {

    }

    /**
     * 括号实例化方法
     * @param p_sqlfield
     */
    public SqlCondition(String p_sqlfield) {
        this.field = p_sqlfield;
    }
    /**
     * 快速实例化
     *
     * @param p_logic     and or
     * @param p_sqlfield  字段
     * @param p_fieldType 类型
     * @param p_operator  where 操作符
     * @param p_value     值
     */
    public SqlCondition(String p_logic, String p_sqlfield, String p_fieldType, String p_operator, String p_value) {
        this.logic = p_logic;
        this.field = p_sqlfield;
        this.type = p_fieldType;
        this.operator = p_operator;
        this.value = p_value;
    }

    /**
     * 快速实例化
     *
     * @param p_logic     and or
     * @param p_sqlfield  字段
     * @param p_fieldType 类型
     * @param p_operator  where 操作符
     * @param p_value     值
     * @param p_value2    值2
     */
    public SqlCondition(String p_logic, String p_sqlfield, String p_fieldType, String p_operator, String p_value, String p_value2) {
        this.logic = p_logic;
        this.field = p_sqlfield;
        this.type = p_fieldType;
        this.operator = p_operator;
        this.value = p_value;
        this.value2 = p_value2;
    }

    //get method
    public String getLogic() {
        return logic;
    }

    public String getField() {
        return field;
    }

    public String getType() {
        return type;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public String getValue2() {
        return value2;
    }


    //set method
    public void setLogic(String logic) {
        this.logic = logic;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
