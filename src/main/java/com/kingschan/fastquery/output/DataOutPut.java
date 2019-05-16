package com.kingschan.fastquery.output;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kingschan.fastquery.sql.dto.DataTransfer;

/**
 * servlet 格式化输出
 * @author kingschan
 *date:2013-08-08
 */
public interface DataOutPut {
	/**
	 * 纯文本
	 */
	String Content_Type_Text="text/plain;charSet=UTF-8";
	/**
	 * HTML
	 */
	String Content_Type_Html="text/html;charSet=UTF-8";
	/**
	 * Excel
	 */
	String Content_Type_Excel="application/vnd.ms-excel";
	/**
	 * PDF
	 */
	String Content_Type_Pdf="application/pdf";
	/**
	 * csv
	 */
	String Content_Type_Csv="text/csv;charSet=UTF-8";
	/**
	 * 格式化输出枚举
	 * @author kingschan
	 *
	 */
	public enum Format{
		HTML,JSON,TEXT,PDF,EXCEL,CSV
	}

	/**
	 * 执行格式化
	 * @param request
	 * @param response
	 * @param dtf
	 * @param args
     * @throws Exception
     */
	void output(HttpServletRequest request, HttpServletResponse response, DataTransfer dtf, Map<String, Object> args)throws Exception;
}
