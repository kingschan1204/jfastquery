package com.kingschan.fastquery.sql.parse;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.vo.SqlCondition;
/**
 * 
*  <pre>    
* 类名称：FieldTypeAnalysis 
* 类描述：   字段类型 解析器
* 创建人：陈国祥   (kingschan)
* 创建时间：2014-8-4 下午3:25:11   
* 修改人：Administrator   
* 修改时间：2014-8-4 下午3:25:11   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface FieldTypeAnalysis {
	/**
	 * 解析
	 * @param condition 条件
	 * @param dbtype	数据库类型
	 * @return
	 * @throws Exception
	 */
	String Analysis(SqlCondition condition,DbType dbtype)throws Exception;

}
