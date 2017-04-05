package com.kingschan.fastquery.logic.handle.query;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.jsqlparser.DefaultSqlParser;
import com.kingschan.fastquery.util.JdbcTemplete;
import com.kingschan.fastquery.vo.DataTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页查询逻辑处理
 * @author kingschan
 *2013-80-19
 */
public class PaginationQueryLogicHandle implements LogicHandle {

    private Logger log = LoggerFactory.getLogger(PaginationQueryLogicHandle.class);
    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
        List<?> lis =null;
        DefaultSqlParser dsp = new DefaultSqlParser(sqb.getSql(),type);
        //构建where条件
        dsp.appendCondition(sqb.getWhere());        
        //设置排序
        if(null!=sqb.getOrder()&&!sqb.getOrder().isEmpty()){dsp.setOrderColumns(sqb.getOrder());}       
        
        if (null!=sqb.getPageIndex()&&null!=sqb.getPageSize()) {
            //普通查询 或者导出
            dsp.pagnation(sqb.getPageIndex(), sqb.getPageSize());
        }           
            //设置sql
        sqb.setSql(dsp.toString());
        log.debug("SQL:{}",dsp.toString());
        lis= JdbcTemplete.queryForListMap(con,dsp.toString());
        sqb.setQueryResult(lis);
        return sqb;
    }

	
	
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	private void removeNull(List<Map<String,Object>>lis){
		for(Map<String,Object> m : lis){
			Set<Entry<String, Object>> mapEntries = m.entrySet();
			Iterator it = mapEntries.iterator();
			while(it.hasNext()){
				Map.Entry<String,Object> entry = (Entry<String,Object>) it.next(); 
				if(entry.getValue()==null){
					it.remove();
				}
			}
		}
	}*/

}
