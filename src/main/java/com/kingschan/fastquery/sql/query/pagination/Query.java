package com.kingschan.fastquery.sql.query.pagination;

import com.kingschan.fastquery.vo.DataTransfer;



/**
 * <pre>
 * @author kingschan
 * date:2012-11-27
 * description:
 * </pre>
 */
public interface Query {
	/**
	 * 分页查询
	 * @param con  
	 * @param sql	
	 * @param pageIndex 
	 * @param pagesize 
	 * @param orderByStr  
	 * @param closeConnection 
	 * @param whereStr   
	 * @return
	 * @throws Exception
	 */
	DataTransfer paginationQuery(DataTransfer dt)throws Exception;

}
