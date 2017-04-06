package com.kingschan.fastquery.logic.handle.dispacher;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildVariableLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildConditionLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteArrayQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteMapQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildPagingLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteTotalLogicHandle;
import com.kingschan.fastquery.output.DataOutPut;
import com.kingschan.fastquery.sql.connection.ConnectionFactory;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import com.kingschan.fastquery.sql.dto.SqlCommand;

/**
 * 
*  <pre>    
* 类名称：LogicHandleDispacher 
* 类描述：  逻辑处理调度器
* 创建人：陈国祥   (kingschan)
* 创建时间：2014-8-1 下午3:51:38   
* 修改人：Administrator   
* 修改时间：2014-8-1 下午3:51:38   
* 修改备注：   
* @version V1.0
* </pre>
 */
@SuppressWarnings("rawtypes")
public class LogicHandleDispacher {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private static Map<Class, LogicHandle> map ;
	public LogicHandleDispacher(HttpServletRequest request,HttpServletResponse response){
		this.request=request;
		this.response=response;
	}
	static{
		map = new HashMap<Class, LogicHandle>();
		map.put(BuildVariableLogicHandle.class, new BuildVariableLogicHandle());
		map.put(BuildConditionLogicHandle.class, new BuildConditionLogicHandle());
		map.put(ExecuteArrayQueryLogicHandle.class, new ExecuteArrayQueryLogicHandle());
		map.put(ExecuteMapQueryLogicHandle.class, new ExecuteMapQueryLogicHandle());
		map.put(BuildPagingLogicHandle.class, new BuildPagingLogicHandle());
		map.put(ExecuteTotalLogicHandle.class, new ExecuteTotalLogicHandle());
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 逻辑处理调度
	 * @param cmd sql命令
	 * @param output  输出格式
	 * @param handles	逻辑处理执行顺序
	 * @throws Exception 
	 */
	public void handleDispacher(Map<String, Object> req_args,SqlCommand cmd,DataOutPut output,Class<LogicHandle>[] handles)  {
		Connection conn=null;	
		DataTransfer dt =new DataTransfer();
		dt.setSql(cmd.getSql());
			
		try {
			conn =ConnectionFactory.getConn(cmd.getJdbcConnection());
			for (Class c : handles) {
				dt =map.get(c).doLogic(req_args, dt, conn, cmd.getDBtype());
			}
			output.output(request, response, dt, req_args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcTemplete.closeDB(null, null, conn);
		}
		
	}
}
