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
 * 
* <pre>
* 项目名称：freeaction   
* 类名称：GridOutPut   
* 类描述：   输出Json字符串
* 创建人：kingschan   
* 创建时间：2014-5-22 下午02:54:51   
* 修改人：kingschan   
* 修改时间：2014-5-22 下午02:54:51   
* 修改备注：   
* @version    
* </pre>
 */
public class GridOutPut implements DataOutPut, java.io.Serializable {

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger(GridOutPut.class);
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
