package com.kingschan.fastquery.util;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 
*  <pre>    
* 类名称：JdbcTemplete 
* 类描述：   JDBC操作工具类
* 创建人：陈国祥   (kingschan)
* 创建时间：2011-7-31 上午10:25:39   
* 修改人：Administrator   
* 修改时间：2014-7-31 上午10:25:39   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class JdbcTemplete {

	/**
	 * 关闭数据库连接
	 * @param rs
	 * @param sm
	 * @param con
     */
	public static void closeDB(ResultSet rs, Statement sm, Connection con) {
		try {
			if (null != rs) {
				rs.close();
			}
			if (null != sm) {
				sm.close();
			}
			if (null != con) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回一个map的集合
	 * 
	 * @param con 数据库连接
	 * @param sql	sql语句
	 * @return	list[map(string,object)]结果集
	 * @throws Exception
	 */
	public static List<Map<String, Object>> queryForListMap(Connection con,String sql, Object... Sqlargs) throws Exception {
		List<Map<String, Object>> lis = new ArrayList<Map<String, Object>>();
		PreparedStatement pre = con.prepareStatement(sql);
		if (null != Sqlargs) {
			for (int i = 1; i <= Sqlargs.length; i++) {
				pre.setObject(i, Sqlargs[i - 1]);
			}
		}
		ResultSet rs = pre.executeQuery();
		ResultSetMetaData rsmd;
		while (rs.next()) {
			rsmd = rs.getMetaData();
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String type=rsmd.getColumnTypeName(i);
				//修正优先选用别名
				String columnName=StringUtil.null2Empty(rsmd.getColumnLabel(i)).isEmpty()?rsmd.getColumnName(i):rsmd.getColumnLabel(i);
				//修正jdbc读取bit字段时的bug
				if (type.equalsIgnoreCase("bit")) {
				    map.put(columnName, rs.getBoolean(i));					
				}else if (type.matches("(?i)datetime")) {
					Timestamp times =rs.getTimestamp(i);
					if(null!=times){
						Date date = new Date(times.getTime());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						map.put(columnName, formatter.format(date));
					}					
				}
				else{
					map.put(columnName, rs.getObject(i));
				}
				
			}
			lis.add(map);

		}
		JdbcTemplete.closeDB(rs, pre, null);
		return lis;
	}

	/**
	 * 查询单一的值
	 * @param con 数据库连接
	 * @param sql	sql语句
	 * @param Sqlargs sql参数
	 * @return object类型单值
	 * @throws Exception
	 */
	public static Object UniqueQuery(Connection con, String sql, Object... Sqlargs) throws Exception {
		Object value = null;
		PreparedStatement pre = con.prepareStatement(sql);
		if (null != Sqlargs) {
			for (int i = 1; i <= Sqlargs.length; i++) {
				pre.setObject(i, Sqlargs[i - 1]);
			}
		}
		ResultSet rs = pre.executeQuery();
		while (rs.next()) {
			value = rs.getObject(1);
		}
		JdbcTemplete.closeDB(rs, pre, null);
		return value;
	}

	/**
	 *<b> 根据传入的参数查询数据库返回一个List的object数组形式</b>
	 * @param con 数据库连接
	 * @param sql	sql语句
	 * @param Sqlargs sql参数
	 * @return list(object[])类型的集合
	 * @throws Exception
	 */
	public static List<Object[]> queryForListArray(Connection con, String sql,Object... Sqlargs) throws Exception {
		List<Object[]> lis = new ArrayList<Object[]>();
		PreparedStatement pre = con.prepareStatement(sql);
		if (null != Sqlargs) {
			for (int i = 1; i <= Sqlargs.length; i++) {
				pre.setObject(i, Sqlargs[i - 1]);
			}
		}
		ResultSet res = pre.executeQuery();
		ResultSetMetaData rsmd = null;
		List<Object> rowslis;
		while (res.next()) {
			rsmd = res.getMetaData();
			rowslis = new ArrayList<Object>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				rowslis.add(res.getObject(i));
			}
			lis.add(rowslis.toArray());
		}
		JdbcTemplete.closeDB(res, pre, null);
		return lis;
	}

	/**
	 * 执行SQL语名返回受影响的行数
	 * @param con 数据库连接
	 * @param sql	sql语句
	 * @param objects sql参数
	 * @return 受影响行数
	 * @throws Exception
	 */
	public static int executeSQL(Connection con, String sql, Object... objects)
			throws Exception {
		int affected = 0;
		PreparedStatement pre = con.prepareStatement(sql);
		for (int i = 1; i <= objects.length; i++) {
			pre.setObject(i, objects[i - 1]);
		}
		affected = pre.executeUpdate();
		JdbcTemplete.closeDB(null, pre, null);
		return affected;
	}
	/**
	 * 执行SQL语名返回受影响的行数  替换sql语句中 #{xxx} 或者 ${xxx}
	 * @param con 数据库连接
	 * @param sql sql语句
	 * @param map sql参数
	 * @return 受影响行数
	 * @throws Exception
	 */
	public static int executeSQL(Connection con, String sql, Map<String, String> map)
			throws Exception {
		int affected = 0;
		String temp =sql;
		if (null!=map) {
			Iterator<String> itera = map.keySet().iterator();
			while (itera.hasNext()) {
				String key = itera.next();
				temp = sql.replaceAll(String.format("[\\$|\\#]\\{%s\\}", key), map.get(key));
				
			}
		}
		PreparedStatement pre = con.prepareStatement(temp);		
		affected = pre.executeUpdate();
		JdbcTemplete.closeDB(null, pre, null);
		return affected;
	}
	/**
	 * 插入一条数据，并返回主键
	 * @param con 连接
	 * @param sql	语句
	 * @param objects	参数
	 * @return	自增键
	 * @throws Exception
	 */
	public static Object save(Connection con, String sql,Object... objects)throws Exception{
	    Object key=null;
		PreparedStatement pre = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		for (int i = 1; i <= objects.length; i++) {
			pre.setObject(i, objects[i - 1]);
		}
		int affected=pre.executeUpdate();
		if (affected>0) {
		    ResultSet rs =pre.getGeneratedKeys();
	         if ( rs.next() ) {  
	                 key = rs.getObject(1);  
	             }  
        }
		return key;
	}
	/**
	 * 在一个数据库连接上执行一批静态SQL语句  如果有异常会自动回滚事务
	 * 
	 * @param conn
	 *            数据库连接
	 * @param sqlList
	 *            静态SQL语句字符串集合
	 * @throws SQLException 
	 */
	public static int[] executeBatchSQL(Connection conn, List<String> sqlList) throws SQLException {
		int[] affected = null;
		
		try {
			//点禁止自动提交，设置回退   			   
			conn.setAutoCommit(false);
			// 创建执行SQL的对象
			Statement stmt = conn.createStatement();
			for (String sql : sqlList) {
				stmt.addBatch(sql);
			}
			// 执行SQL，并获取返回结果
			affected=stmt.executeBatch();
			//事务提交   			   
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.out.println("执行批量SQL语句出错，请检查！操作失败！事务回滚。"+e);
			throw new SQLException(e);
		}
		return affected;
	}

	/**
	 * 批量执行一批动态的SQL语句
	 * 
	 * <pre>
	 * 一批命令提交到数据库来执行，如果全部命令执行成功，则返回一个更新计数数组。返回数组的int元素的排序对应在批处理的命令，这是根据在它们被添加到该批次的顺序排序。方法executeBatch返回的数组中的元素可能是下列之一：
	 * 
	 * 一个大于或等于零 - 表示该命令被成功处理，并发出命令的执行受到影响的数据库中的行数是一个更新计数
	 * 值SUCCESS_NO_INFO - 表示该命令被成功处理，但影响的行数是未知
	 * 如果批量更新中的命令之一无法正确执行，这个方法抛出一个BatchUpdateException ， JDBC驱动程序可能会或可能不会继续处理批处理中的剩余命令。
	 * 然而，司机的行为必须与特定的DBMS一致，要么始终继续处理命令，要么永远不继续处理命令。如果驱动程序继续处理在发生故障后，由该方法返回的数组将包含BatchUpdateException.getUpdateCounts一样多的元素，
	 * 是在批处理中的命令，以及元素中的至少一个会出现下面的： 
	 * 
	 * EXECUTE_FAILED的值 - 表示该命令不能成功执行，如果驱动程序继续处理命令，命令失败后只发生
	 * 可能的实现和返回值在Java 2 SDK ，标准版， 1.3版本已被修改，以适应BatchUpdateException强健且快速，已经被摔下后继续工程案例批量更新中的命令选项。
	 * 
	 * 返回：
	 * 批次中的每个命令包含一个元素的数组的更新计数。根据中的命令添加到批中的顺序排列的元素的数组中。
	 * 抛出：
	 * SQLException - 如果发生数据库访问错误，这种方法被称为一个封闭的声明或驱动程序不支持批量语句。则抛出BatchUpdateException （ SQLException的子类） ，如果发送到数据库的命令之一无法正确执行或者尝试返回结果集。
	 * 因为：
	 * 1.3
	 * 请参见：
	 * DatabaseMetaData.supportsBatchUpdates
	 * </pre>
	 * 
	 * @param conn 数据库连接
	 * @param sql	sql语句
	 * @param args sql参数
	 * @throws Exception
	 */
	public static int[] executeBatchSQL(Connection conn, String sql,
			List<Object[]> args) throws Exception {
		int[] affected = null;
		try {
		    conn.setAutoCommit(false);
	        PreparedStatement prest = conn.prepareStatement(sql,
	                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        Object[] obj = null;
	        for (int i = 0; i < args.size(); i++) {
	            obj = args.get(i);
	            for (int j = 0; j < obj.length; j++) {
	                prest.setObject(j + 1, obj[j]);
	            }
	            prest.addBatch();
	        }
	        affected = prest.executeBatch();
	        conn.commit();
	        prest.clearBatch();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            throw new Exception(e);
        }		
		return affected;
	}
	
	/**
	 * 调用存储过程：无返回值 
	 * @param conn 数据库连接
	 * @param sql	sql语句
	 * @param args sql参数
	 * @throws SQLException
	 */
	public static void executeProc(Connection conn,String sql,Object...args) throws SQLException{
		CallableStatement call  = conn.prepareCall(sql);  
		if (null!=args&&args.length>0) {
			for (int i = 1; i <= args.length; i++) {
				//index 从1开始
				call.setObject(i, args[i-1]);
			}			
		}
		call.execute();
	}
	/**
	 * 调用存储过程：只有1个返回值 
	 * @param conn 数据库连接
	 * @param sql	sql语句
	 * @param args sql参数
	 * @param outArgsIndex 输出参数的下标
	 * @param outArgsType	输出参数的类型参照 java.sql.Types
	 * @throws SQLException
	 */	
	public static Object executeProcOutSimple(Connection conn,String sql,Object[] args,int outArgsIndex,int outArgsType) throws SQLException{
		CallableStatement call  = conn.prepareCall(sql);  
		if (null!=args&&args.length>0) {
			for (int i = 1; i <= args.length; i++) {
				//index 从1开始
				call.setObject(i, args[i-1]);
			}			
		}
		call.registerOutParameter(outArgsIndex, outArgsType);
		call.execute();
		return call.getObject(outArgsIndex);
	}
	
}