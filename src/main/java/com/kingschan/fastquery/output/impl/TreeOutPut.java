package com.kingschan.fastquery.output.impl;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingschan.fastquery.output.DataOutPut;
import com.kingschan.fastquery.util.EasyUiTagUtil;
import com.kingschan.fastquery.vo.DataTransfer;
/**
 * 
*  <pre>    
* 类名称：TreeOutPut 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2014-9-22 下午8:14:50   
* 修改人：Administrator   
* 修改时间：2014-9-22 下午8:14:50   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class TreeOutPut implements DataOutPut, java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public void output(HttpServletRequest request, HttpServletResponse response, DataTransfer sqb,
            Map<String, Object> args) throws Exception {
        String jsonstr = null;
        jsonstr = EasyUiTagUtil.FormatRowsJsonData((List<Map<String, Object>>) sqb.getQueryResult()).toString();        
        response.setContentType(DataOutPut.Content_Type_Text);
        PrintWriter pw = response.getWriter();
        pw.print(jsonstr);
        
    }

}
