package com.kingschan.fastquery.logic;

import java.sql.Connection;
import java.util.Map;

import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.dto.DataTransfer;


/**
 * 逻辑处理
 * @author fedora
 *date:2013-8-8
 */
public interface LogicHandle {
	/**
	 * 逻辑处理
	 * @param args  前台传入参数
	 * @param sqb   查询载体
	 * @return
	 * @throws Exception
	 */
	DataTransfer doLogic(Map<String, Object> args,DataTransfer sqb,Connection con,DbType type)throws Exception;
}
