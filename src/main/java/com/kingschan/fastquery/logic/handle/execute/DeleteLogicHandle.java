package com.kingschan.fastquery.logic.handle.execute;

import java.sql.Connection;
import java.util.Map;

import com.kingschan.fastquery.sql.jsqlparser.DbType;
import org.apache.log4j.Logger;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.dto.DataTransfer;

/**
 * 增删除改处理
 * @author kingschan
 *date:2013-09-29
 */
public class DeleteLogicHandle implements LogicHandle{
	private static Logger log = Logger.getLogger(DeleteLogicHandle.class);

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
      /*  String ids=args.containsKey(KeyWordArgs.Ids)?args.get(KeyWordArgs.Ids).toString():null;
        String chooseField=args.containsKey(KeyWordArgs.Choosefield)?args.get(KeyWordArgs.Choosefield).toString():null;

        DefaultSqlParser gsp = new DefaultSqlParser(sqb.getSql(),type);
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
            
        }
        sqb.setSql(gsp.toString());*/
        return sqb;
    }
	

	
}
