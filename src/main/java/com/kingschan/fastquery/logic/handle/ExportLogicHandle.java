package com.kingschan.fastquery.logic.handle;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingschan.fastquery.conf.FastQueryConfigure;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.sql.jsqlparser.DefaultSqlParser;
import com.kingschan.fastquery.util.StringUtil;
import org.apache.log4j.Logger;

import com.kingschan.fastquery.conf.model.KeyWordArgs;
import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.vo.DataTransfer;
/**
 * <pre>
 * 导出EXCEL时的逻辑处理
 * @author fedora
 *date:2013-8-8
 *</pre>
 */
public class ExportLogicHandle implements LogicHandle{
	//private FreeActionContainer container=FreeActionContainerImpl.getInstance();
	private static Logger log = Logger.getLogger(ExportLogicHandle.class);

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con, DbType type) throws Exception {
       /* FastQueryConfigure configure =FastQueryConfigure.getInstance();
        String disableColumns=args.containsKey(KeyWordArgs.Disablecloums)?args.get(KeyWordArgs.Disablecloums).toString():null;
        String ids=args.containsKey(KeyWordArgs.Ids)?args.get(KeyWordArgs.Ids).toString():null;
        String chooseField=args.containsKey(KeyWordArgs.Choosefield)?args.get(KeyWordArgs.Choosefield).toString():null;
        DefaultSqlParser gsp = new DefaultSqlParser(sqb.getSql(),type);
        if (null!=disableColumns) {
            String[] dc =disableColumns.split(",");
            List<String> cols=gsp.getSelectColumns();
            for (String s : dc) {
                for (int i = 0; i < cols.size(); i++) {
                    String col =cols.get(i);
                    if (col.equals(s)) {
                        cols.remove(i);
                    }
                }
            }
            gsp.setSelectColumns(cols);
            sqb.setSql(gsp.toString());
        }
        //设置选择列和选中的集合
        if (!StringUtil.null2Empty(ids).isEmpty()&&!StringUtil.null2Empty(chooseField).isEmpty()) {
            log.info(" model:choose export!");
            Set<String> set = new HashSet<String>();
            String[] tempids =ids.split(",");
            StringBuffer sb = new StringBuffer();
            for (String s : tempids) {
                set.add(s);
                sb.append("'").append(StringUtil.replaceSpaceLine(s)).append("',");
            } 
            sqb.setChooseField(chooseField);
            sqb.setChooseIds(set);
            gsp.appendCondition(" and "+chooseField+" in ("+sb.toString().substring(0,sb.toString().length()-1)+")");
            
        }else{
            log.info(" model:query export!");
            
        }
        sqb.setSql(gsp.toString());*/
        return sqb;
    }

	
	

	
}
