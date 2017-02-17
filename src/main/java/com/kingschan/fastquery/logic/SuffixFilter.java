package com.kingschan.fastquery.logic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
*  <pre>    
* 类名称：SuffixFilter 
* 类描述：   后缀名逻辑处理
* 创建人：陈国祥   (kingschan)
* 创建时间：2015-4-2 下午4:07:30   
* 修改人：Administrator   
* 修改时间：2015-4-2 下午4:07:30   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface SuffixFilter {

    /**
     * 逻辑处理
     * @param req
     * @param rep
     * @throws Exception
     */
    void doFilter(HttpServletRequest req,HttpServletResponse rep,Map<String, Object> args)throws Exception;
}
