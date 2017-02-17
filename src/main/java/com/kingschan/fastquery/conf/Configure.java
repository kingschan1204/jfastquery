package com.kingschan.fastquery.conf;

import javax.sql.DataSource;

/**
 * 
*    
* 类名称：Configure   
* 类描述：   
* 创建人：kings.chan
* 创建时间：2016-9-10 上午11:40:38   
* 修改人：   
* 修改时间：
* 项目：EasyQuery
* 修改备注：   
* @version    
*
 */
public interface Configure {
	/**
	 * 得到数据源
	 * @return
	 * @throws Exception
	 */
	DataSource getDataSource()throws Exception;
}
