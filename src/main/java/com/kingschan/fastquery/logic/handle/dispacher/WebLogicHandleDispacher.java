package com.kingschan.fastquery.logic.handle.dispacher;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildConditionLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildPagingLogicHandle;
import com.kingschan.fastquery.logic.handle.inital.BuildVariableLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteArrayQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteMapQueryLogicHandle;
import com.kingschan.fastquery.logic.handle.query.ExecuteTotalLogicHandle;
import com.kingschan.fastquery.output.DataOutPut;
import com.kingschan.fastquery.sql.connection.ConnectionFactory;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import com.kingschan.fastquery.sql.dto.SqlCommand;
import com.kingschan.fastquery.util.JdbcTemplete;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
*  <pre>    
* 类名称：WebLogicHandleDispacher
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
public class WebLogicHandleDispacher extends LogicHandleDispacher{

	private HttpServletRequest request;
	private HttpServletResponse response;
	public WebLogicHandleDispacher(HttpServletRequest request, HttpServletResponse response){
		this.request=request;
		this.response=response;
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
