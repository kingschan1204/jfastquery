package com.kingschan.fastquery.logic.handle.inital;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.jsqlparser.DefaultSqlParser;
import com.kingschan.fastquery.sql.dto.DataTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页查询逻辑处理
 * @author kingschan
 *2013-80-19
 */
public class BuildPagingLogicHandle implements LogicHandle {

    private Logger log = LoggerFactory.getLogger(BuildPagingLogicHandle.class);
    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
        List<?> lis =null;
        DefaultSqlParser dsp = new DefaultSqlParser(sqb.getSql(),type);
        //构建where条件
        dsp.appendCondition(sqb.getWhere());        
        //设置排序
        if(null!=sqb.getOrder()&&!sqb.getOrder().isEmpty()){dsp.setOrderColumns(sqb.getOrder());}       
        //设置分页
        if (null!=sqb.getPageIndex()&&null!=sqb.getPageSize()) {
            //设置统计语句
            DefaultSqlParser totalParser = new DefaultSqlParser(sqb.getSql(),type);
            //构建where条件
            totalParser.appendCondition(sqb.getWhere());
            totalParser.count();//设置count
            sqb.setTotalSql(totalParser.toString());
            log.debug("Total SQL:{}",totalParser.toString());
            //设置显示条数语句
            dsp.pagnation(sqb.getPageIndex(), sqb.getPageSize());
        }           
            //设置sql
        sqb.setSql(dsp.toString());
        log.debug("Limit SQL:{}",dsp.toString());
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
