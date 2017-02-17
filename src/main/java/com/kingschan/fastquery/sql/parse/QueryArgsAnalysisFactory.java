package com.kingschan.fastquery.sql.parse;

import java.util.HashMap;
import java.util.Map;

import com.kingschan.fastquery.sql.parse.fieldtype.DateField;
import com.kingschan.fastquery.sql.parse.fieldtype.DateTimeField;
import com.kingschan.fastquery.sql.parse.fieldtype.NumberField;
import com.kingschan.fastquery.sql.parse.fieldtype.TextField;

/**
 *查询字段解析
 * @author kingschan
 *date:2013-4-24
 */
public class QueryArgsAnalysisFactory {
	/**
	 * 支持的字段类型
	 * @author fedora
	 *
	 */
	public enum FiledType{
		TEXT,NUMBER,DATE,STRING,SELECT,DATETIME,BOOLEAN
	}
	/**
	 * 逻辑操作符
	 * @author fedora
	 *
	 */
	public enum Relationship{
		AND,OR,NOT,ANY
	}
	
	private static Map<FiledType, FieldTypeAnalysis> Analysis = new HashMap<FiledType, FieldTypeAnalysis>();
	static{
		Analysis.put(FiledType.TEXT, new TextField());
		Analysis.put(FiledType.NUMBER, new NumberField());
		Analysis.put(FiledType.DATE, new DateField());
		
		Analysis.put(FiledType.STRING, new TextField());
		Analysis.put(FiledType.SELECT, new TextField());
		Analysis.put(FiledType.BOOLEAN, new NumberField());
		Analysis.put(FiledType.DATETIME, new DateTimeField());
	}
	/**
	 * 返回字段类型解析器
	 * @param filedtype
	 * @return
	 */
	public static FieldTypeAnalysis getAnalysis(FiledType filedtype){
		return Analysis.get(filedtype);
	}
	/**
	 * 传入字符解析出相应的逻辑操作符
	 * @param str
	 * @return
	 */
	public Relationship parseRelationShip(String str)throws Exception{
		if (str.equals("&")) {
			return Relationship.AND;
		}else if (str.equals("|")) {
			return Relationship.OR;
		}else if (str.equals("!")) {
			return Relationship.NOT;
		}else{
			throw new Exception("逻辑操作符转换失败，不支持:"+str);
		}
	}
}
