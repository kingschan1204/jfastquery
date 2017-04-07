package com.kingschan.fastquery.logic.handle.inital;
import java.sql.Connection;
import java.util.Map;

import com.kingschan.fastquery.logic.LogicHandle;
import com.kingschan.fastquery.sql.jsqlparser.DbType;
import com.kingschan.fastquery.util.StringTemplateParser;
import com.kingschan.fastquery.sql.dto.DataTransfer;
/**
 * 扫描并处理动态变量
 * @author kingschan
 *date:2014-4-30
 */
public class BuildVariableLogicHandle implements LogicHandle {
//	private static Logger log = Logger.getLogger(ScanArgsLogicHandle.class);

    public DataTransfer doLogic(Map<String, Object> args, DataTransfer sqb, Connection con,
            DbType type) throws Exception {
        //新版
        try {           
            StringTemplateParser stp = new StringTemplateParser(sqb.getSql());
             String result=stp.process(args);
             sqb.setSql(result);
             if (null!=sqb.getWhere()&&!sqb.getWhere().isEmpty()) {
                 stp = new StringTemplateParser(sqb.getWhere());
                 String result1=stp.process(args);
                 sqb.setWhere(result1);              
             }
         } catch (Exception e) {
             throw new Exception("sql语句表达式错误！");
         }
         return sqb;
    }

	
}
