package com.kingschan.fastquery.logic.handle.dispacher;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildVariableLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildConditionLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteArrayQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteMapQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildPagingLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteTotalLogicHandle;
import com.kingschan.fastquery.sql.connection.ConnectionFactory;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import com.kingschan.fastquery.sql.dto.SqlCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  逻辑处理调度器
 *  kings.chan
 *  2017-04-07
 */
@SuppressWarnings("rawtypes")
public class LogicHandleDispacher {

	private Logger log = LoggerFactory.getLogger(LogicHandleDispacher.class);
	protected static Map<Class, LogicHandle> map ;
	static{
		map = new HashMap<Class, LogicHandle>();
		map.put(BuildVariableLogicHandle.class, new BuildVariableLogicHandle());
		map.put(BuildConditionLogicHandle.class, new BuildConditionLogicHandle());
		map.put(BuildPagingLogicHandle.class, new BuildPagingLogicHandle());
		map.put(ExecuteArrayQueryLogicHandle.class, new ExecuteArrayQueryLogicHandle());
		map.put(ExecuteMapQueryLogicHandle.class, new ExecuteMapQueryLogicHandle());
		map.put(ExecuteTotalLogicHandle.class, new ExecuteTotalLogicHandle());
	}


	/**
	 * 逻辑处理调度
	 * @param args  参数
	 * @param cmd   SQL
	 * @param handles 逻辑处理
     * @return
     */
	public DataTransfer handleDispacher(Map<String, Object> args,SqlCommand cmd,Class<LogicHandle>[] handles)  {
		Connection conn=null;	
		DataTransfer dt =new DataTransfer();
		dt.setSql(cmd.getSql());
			
		try {
			conn =ConnectionFactory.getConn(cmd.getJdbcConnection());
			for (Class c : handles) {
				dt =map.get(c).doLogic(args, dt, conn, cmd.getDBtype());
			}
		} catch (Exception e) {
			log.error("{}",e);
			e.printStackTrace();
		}finally{
			JdbcTemplete.closeDB(null, null, conn);
		}
		return dt;
	}
}
