package com.kingschan.fastquery.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;

/**
 * 格式化工具类
 * 
 * @author kingschan date:2013-8-6
 */
public class EasyUiTagUtil {


	/**
	 * 格式化成多行记录的josn字符串
	 * @return
	 */
	public static JSONArray FormatRowsJsonData(List<Map<String, Object>> data){
		JSONArray jsons = new JSONArray();	
		JSONObject json =null;
		for (int i = 0; i < data.size(); i++) {
			json = JSON.parseObject(JSON.toJSONString(data.get(i)));
			jsons.add(json);
		}
		return jsons;
	}
	/**
	 * 格式化成分页的记录
	 * @return
	 * @throws Exception
	 */
	public static String FormatPageJsonData(long total,List<Map<String, Object>> data) throws Exception{		
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", FormatRowsJsonData(data));
		return json.toString();
	}
	
	
}
