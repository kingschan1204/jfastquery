package com.kingschan.fastquery.output.impl;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingschan.fastquery.output.DataOutPut;
import com.kingschan.fastquery.util.EasyUiTagUtil;
import com.kingschan.fastquery.sql.dto.DataTransfer;

/**
 * 标准输出实现
 * kingschan
 * 2017-04-06
 */
public class StandardOutPut implements DataOutPut, java.io.Serializable {

	private static final long serialVersionUID = 1L;
    @SuppressWarnings("unchecked")
    public void output(HttpServletRequest request, HttpServletResponse response, DataTransfer sqb,
            Map<String, Object> args) throws Exception {
        String jsonstr = null;
        jsonstr = EasyUiTagUtil.FormatPageJsonData(sqb.getTotal(),(List<Map<String, Object>>) sqb.getQueryResult());        
        response.setContentType(DataOutPut.Content_Type_Text);
        PrintWriter pw = response.getWriter();
        pw.print(jsonstr);
        
    }


}
