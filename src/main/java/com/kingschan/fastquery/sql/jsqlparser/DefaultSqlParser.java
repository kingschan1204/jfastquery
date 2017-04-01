package com.kingschan.fastquery.sql.jsqlparser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.Top;
/**
 * 
*  <pre>    
* 类名称：DefaultSqlParser 
* 类描述：   默认数据库解析类
* 创建人：陈国祥   
* 创建时间：2014-6-5 上午10:46:18   
* 修改人：Administrator   
* 修改时间：2014-6-5 上午10:46:18   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class DefaultSqlParser implements SqlAnalyse {
	private String sql;
	private DbType dbtype;
	private CCJSqlParserManager parserManager ;
	private Statement statement ;
	private PlainSelect ps;
	public static final String sqlserver_nolock_token=" \t\r\t ";
	public DefaultSqlParser(String p_sql,DbType p_dbtype) throws Exception{
		init( p_sql, p_dbtype);
	}
	/**
	 * 实例 化
	 * @param p_sql
	 * @param p_dbtype
	 */
	public void init(String p_sql,DbType p_dbtype){
	    this.sql=p_sql;
        this.dbtype=p_dbtype;
        if (p_dbtype==DbType.SQLSERVER) {
            this.sql=p_sql.replaceAll("(?i)with\\s*\\(\\s*nolock\\s*\\)",sqlserver_nolock_token );
        }
       try {
           parserManager= new CCJSqlParserManager();
           statement=parserManager.parse(new StringReader(sql));
           if (statement instanceof Select) {
               Select select = (Select) statement;
               if (select.getSelectBody() instanceof PlainSelect) {
                   ps = (PlainSelect) select.getSelectBody();
               }else{
                   throw new Exception(String.format("%s不是PlainSelect语句！", p_sql));
               }
           }else{
               throw new Exception(String.format("%s不是有效的查询语句！", p_sql));
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.jsqlparser.analyse.SqlAnalyse#getSelectColumns()
	 */

	public List<String> getSelectColumns() {
		List<SelectItem> lis =ps.getSelectItems();
		List<String> select= new ArrayList<String>();
		for (SelectItem si : lis) {
			select.add(si.toString());
		}
		return select;
	}


	public void addSelectColumns(String columnsName) {
		 List<SelectItem> selectlist= ps.getSelectItems();
         SelectExpressionItem se = new SelectExpressionItem();
         se.setExpression(new Column(columnsName));         
         selectlist.add(se);
         ps.setSelectItems(selectlist);
	}
	/*
	 * (non-Javadoc)
	 * @see net.sf.jsqlparser.analyse.SqlAnalyse#setSelectColumns(java.util.List)
	 */

	public void setSelectColumns(List<String> columns) {
		 if (null!=columns&&columns.size()>0) {
			 List<SelectItem> selectlist=new ArrayList<SelectItem>();
			 SelectExpressionItem se;
			 for (String str : columns) {
				 se= new SelectExpressionItem();
		         se.setExpression(new Column(str));
		         selectlist.add(se);
			}
			ps.setSelectItems(selectlist);
		}

	}
	/*
	 * (non-Javadoc)
	 * @see net.sf.jsqlparser.analyse.SqlAnalyse#getWhereCondition()
	 */

	public String getWhereCondition() {		
		return null==ps.getWhere()?null:ps.getWhere().toString();
	}
	/*
	 * (non-Javadoc)
	 * @see net.sf.jsqlparser.analyse.SqlAnalyse#appendCondition(java.lang.String)
	 */

	public void appendCondition(String condition) {
		if (null==condition||condition.replaceAll("\\s", "").isEmpty()) return;
		String filter=condition.replaceAll("/r/n", "");
		Expression ex =ps.getWhere();		    
		try {
			if (null == ex) {
				// 不存在where条件
			    Boolean flag=filter.trim().matches("^(?i)[and|or].*");
			    String temp=flag?
			            filter.trim().replaceFirst("(?i)and|or", ""):filter.trim();
				ex=new StringExpression(temp,true);
				ps.setWhere(ex);
			} else {
				// 存在where条件
				//验证 "\\s*(?i)where{1}\\s*"
			    Expression eps =null;
				if (filter.trim().matches("^(?i)and.*")) {
				    eps= new AndExpression(ex, new StringExpression(filter.replaceFirst("(?i)and", ""),true));
                }else if (filter.trim().matches("^(?i)or.*")) {
                    eps= new OrExpression(ex, new StringExpression(filter.replaceFirst("(?i)or", ""),true));
                }
				ps.setWhere(eps);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public void appendLikeCondition(LogicOperaction operaction, String columnName,String value) {
		Expression ex =ps.getWhere();
		LikeExpression like = new LikeExpression();
        like.setLeftExpression(new Column(columnName));
        like.setRightExpression(new StringValue(value));
        if (null==ex) {
			//如果没有where
        	ps.setWhere(like);
        	return;
		}
        switch (operaction) {
		case AND:
			AndExpression and = new AndExpression(ex, like);
	         ps.setWhere(and);
			break;
		case OR:
			OrExpression or = new OrExpression(ex, like);
			ps.setWhere(or);
			break;
		}
	}


	public void setWhereCondition(String where) {
		try {
			/*String temp_sql= ps.toString().replace(ps.getWhere().toString(), where);
			ps = (PlainSelect) this.getSelectBody(temp_sql);*/
		    Expression ex=ps.getWhere();
		    ex=new StringExpression(where.trim().matches("^(?i)and|or.*")?
		            where.trim().replaceFirst("(?i)and|(?i)or", ""):where.trim()
                ,true);
            ps.setWhere(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setTop(long rowIndex) throws Exception {
		if (this.dbtype==DbType.SQLSERVER) {
			Top top = new Top();
			//top.setRowCount(rowIndex);
			top.setExpression(new StringExpression("top 1",true));
			ps.setTop(top);
		}else{
			throw new Exception("Top 是SQLSERVER的特有关键字！");
		}
		
	}


	public String getOrderByString() {
		List<OrderByElement> lis =ps.getOrderByElements();
		return (null!=lis&&lis.size()>0)?lis.toString().replaceAll("\\[|\\]",""):null;
	}


	public List<String> getOrderByColumns() {
		List<OrderByElement> lis =ps.getOrderByElements();
		List<String> columns = new ArrayList<String>();
		if (null!=lis&&lis.size()>0) {
			for (OrderByElement oe : lis) {
				columns.add(oe.getExpression().toString());
			}
		}
		return columns;
	}


	public void appendOrderColumns(String column_name) {
		List<OrderByElement> lis =ps.getOrderByElements();
		if (null==lis) {
			lis= new ArrayList<OrderByElement>();
		}
		OrderByElement oe = new OrderByElement();
		oe.setExpression(new Column(column_name));
		lis.add(oe);
		ps.setOrderByElements(lis);
	}


	public void setOrderColumns(List<String> columns) {
		if(null==columns||columns.size()==0)return;
		List<OrderByElement> lis =new ArrayList<OrderByElement>();
		OrderByElement oe = null;
		for (String str : columns) {
			oe = new OrderByElement();
			oe.setExpression(new Column(str));
			lis.add(oe);
		}
		ps.setOrderByElements(lis);
	}


	public void pagnation(Integer pageIndex,Integer pageSize)throws Exception{
		Integer beginrow = (pageIndex - 1) * pageSize;
		Integer endrow = beginrow + pageSize;
		switch (this.dbtype) {
		case MYSQL:
			Limit limit = new Limit();
			limit.setOffset(beginrow);
			limit.setRowCount(pageSize);
			ps.setLimit(limit);
			break;
		case SQLSERVER:
			//sqlserver 在分页在情况下如果有子查询语句中出现了order by 时必须指定TOP关键字。并且row_number() over ( order by 那个排序的字段)
			List<SelectItem> select_column= ps.getSelectItems();
			if (select_column.size()==1&&select_column.get(0) instanceof AllColumns) {
				throw new Exception("SqlServer分页查询时：语句为  select * 的情况下无法分页！");
			}
	        SelectExpressionItem row_number = new SelectExpressionItem();
	        row_number.setAlias(new Alias("_rank_"));      
	        String over_order_by =null;//row_number()函数里面的排序字段
	        if (null==ps.getOrderByElements()) {
	        	//在没有排序的情况下默认取第一列排序
	        	 SelectExpressionItem sei =(SelectExpressionItem) select_column.get(0);  
	        	 over_order_by=sei.getExpression().toString();
			}else{
				//在有排序的情况下分页函数变为要排序的字段 并且设置TOP 不然会报错
				over_order_by=getOrderByString();
				setTop(endrow);
			}	       
	        row_number.setExpression(new Column(String.format(" ROW_NUMBER() OVER (ORDER BY %s)", over_order_by)));          
	        select_column.add(row_number);
	        ps.setSelectItems(select_column);
	        init( String.format("select * from (%s) as _tp_ where _tp_._rank_ between %d and %d", ps,beginrow,endrow), dbtype);
			break;
		case ORACLE:
			// sql templeate   1.sql语句   2.结束行  3.开始行
			final String ORACLE_pagination = "select * from (select jop.*,rownum rn from (%s) jop where rownum <= %d) where rn >= %d";
			 init(String.format(ORACLE_pagination, this,endrow,beginrow), dbtype);
			break;
		}
		
		
	}
	private static Map<String, String> special;
	static {
	    special= new HashMap<String, String>();
//	    special.put("#parentheses#", "()");//圆括号
//	    special.put("#point#", ".");//点
	}
	

	public String toString() {
	    String sql=ps.toString().replaceAll("\t\r\t", dbtype==DbType.SQLSERVER?"with (nolock)":"");
	    Iterator<String> keys = special.keySet().iterator();
	    while (keys.hasNext()) {
            String key =keys.next();
            sql=sql.replace(key, special.get(key));
        }
		return sql;
	}
    public void count() throws Exception {
       List<String> columns = new ArrayList<String>();
       String template="select count(1) from (%s) tmp_";
       if (null!=ps.getOrderByElements()) {
           //统计的情况下去掉排序无意义
           ps.setOrderByElements(null);
       }
       if (null!=ps.getGroupByColumnReferences()) {
        //存在group 就要 包多一层
           init(String.format(template, ps.toString()), dbtype);
       }else{
          columns.add("count(1)");
          this.setSelectColumns(columns);
          init(this.toString(), dbtype);
       }
        
    }
	
}
