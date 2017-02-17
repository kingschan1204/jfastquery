package com.kingschan.fastquery.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
*  <pre>    
* 类名称：RegexUtil 
* 类描述：   正则表达式工具类
* 创建人：陈国祥   (kingschan)
* 创建时间：2013-6-25
* 修改人：Administrator   
* 修改时间：2013-6-25
* 修改备注：   
* @version V1.0
* </pre>
 */
public class RegexUtil {
	//匹配数字
	public static final String regex_number="^[-+]?([0]{1}(\\.[0-9]+)?|[1-9]{1}\\d*(\\.[0-9]+)?)";//"^[-+]?[0-9]+(\\.[0-9]+)?$";
	//匹配日期(年-月-日)
	public static final String regex_date="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	//匹配日期（年-月-日 时：分：秒）
	public static final String regex_dateTime="^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";
	//邮箱地址
	public static final String regex_email="^[A-Z0-9._%+-]+@(?:[A-Z0-9-]+\\.)+[A-Z]{2,6}$";
	//URL
	public static final String regex_url="[http|https]+://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	//ip地址
	public static final String regex_ipaddress="^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$";
	//包含中文
	public static final String regex_includeCN="[\u4e00-\u9fa5]+";
	//EL表达式
	public static final String regex_expression="[\\$|\\#]\\{[\\w]+\\}";
	
	/**
	 * 是否匹配传入的正则表达式
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean matching(String regex,String str){
		return str.matches(regex);
	}
	
	
	/**
	 * 提取字符串或一段文本中的手机号码 
	 * @param text
	 * @return
	 */
	public static String getPhoneNumbers(String text){
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)"); 
		Matcher matcher = pattern.matcher(text); 
		    StringBuffer bf = new StringBuffer(64); 
		    while (matcher.find()) { 
		      bf.append(matcher.group()).append(","); 
		    } 
		    int len = bf.length(); 
		    if (len > 0) { 
		      bf.deleteCharAt(len - 1); 
		    } 
		return bf.toString();
	}
	/**
	 * 提取文本中匹配正则的字符串
	 * @param text
	 * @param regx 正则
	 * @return 多个结果已,分隔
	 */
	public static String findStrByRegx(String text,String regx){
		Pattern pattern = Pattern.compile(regx); 
		Matcher matcher = pattern.matcher(text); 
		    StringBuffer bf = new StringBuffer(64); 
		    while (matcher.find()) { 
		      bf.append(matcher.group()).append(","); 
		    } 
		    int len = bf.length(); 
		    if (len > 0) { 
		      bf.deleteCharAt(len - 1); 
		    } 
		return bf.toString();
	}
	/**
	 * 去掉所有HTML标记
	 * @param htmlStr HTML字符串
	 * @return
	 */
	public static String replaceAllHtmlTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 
	/**  
     * 匹配身份证号码
     * @param IDStr 身份证号  
     * @return 有效：返回"" 无效：返回String信息  
     * @throws ParseException  
     */  
    public static boolean matcherIdCard(String IDStr) throws Exception { 
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",   
                "3", "2" };   
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",   
                "9", "10", "5", "8", "4", "2" };   
        String Ai = "";   
        // ================ 号码的长度 15位或18位 ================   
        if (IDStr.length() != 15 && IDStr.length() != 18) {   
           // throw new Exception("身份证号码长度应该为15位或18位");
            return false;   
        }   
        // =======================(end)========================   
  
        // ================ 数字 除最后以为都为数字 ================   
        if (IDStr.length() == 18) {   
            Ai = IDStr.substring(0, 17);   
        } else if (IDStr.length() == 15) {   
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);   
        }   
        if (!Ai.matches("\\d+")) {   
            //throw new Exception("身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字");
            return false;   
        }   
        // =======================(end)========================   
  
        // ================ 出生年月是否有效 ================   
        String strYear = Ai.substring(6, 10);// 年份   
        String strMonth = Ai.substring(10, 12);// 月份   
        String strDay = Ai.substring(12, 14);// 月份   
        if (RegexUtil.matching(regex_date, strYear + "-" + strMonth + "-" + strDay)) {   
           // throw new Exception("身份证生日无效");
            return false;    
        }   
        GregorianCalendar gc = new GregorianCalendar();   
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");   
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
                || (gc.getTime().getTime() - s.parse(   
                        strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {   
           // throw new Exception("身份证生日不在有效范围");
            return false;  
        }   
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {  
            //throw new Exception("身份证月份无效");
            return false;   
        }   
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) { 
           // throw new Exception("身份证日期无效");
            return false;   
        }   
        // =====================(end)=====================   
  
        // ================ 地区码时候有效 ================   
        Hashtable<String, String> h = GetAreaCode();   
        if (h.get(Ai.substring(0, 2)) == null) {  
            //throw new Exception("身份证地区编码错误");
            return false;   
        }   
        // ==============================================   
  
        // ================ 判断最后一位的值 ================   
        int TotalmulAiWi = 0;   
        for (int i = 0; i < 17; i++) {   
            TotalmulAiWi = TotalmulAiWi   
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))   
                    * Integer.parseInt(Wi[i]);   
        }   
        int modValue = TotalmulAiWi % 11;   
        String strVerifyCode = ValCodeArr[modValue];   
        Ai = Ai + strVerifyCode;   
  
        if (IDStr.length() == 18) {   
             if (Ai.equals(IDStr) == false) {   
                 //throw new Exception("身份证无效，不是合法的身份证号码"); 
            	 return false;
             }   
         } else {   
             return true;   
         }   
         // =====================(end)=====================   
         return true;   
     }
    
  
     
     /**  
      * 功能：设置地区编码  
      * @return Hashtable 对象  
      */  
     private static Hashtable<String, String> GetAreaCode() {   
         Hashtable<String, String> hashtable = new Hashtable<String, String>();   
         hashtable.put("11", "北京");   
         hashtable.put("12", "天津");   
         hashtable.put("13", "河北");   
         hashtable.put("14", "山西");   
         hashtable.put("15", "内蒙古");   
         hashtable.put("21", "辽宁");   
         hashtable.put("22", "吉林");   
         hashtable.put("23", "黑龙江");   
         hashtable.put("31", "上海");   
         hashtable.put("32", "江苏");   
         hashtable.put("33", "浙江");   
         hashtable.put("34", "安徽");   
         hashtable.put("35", "福建");   
         hashtable.put("36", "江西");   
         hashtable.put("37", "山东");   
         hashtable.put("41", "河南");   
         hashtable.put("42", "湖北");   
         hashtable.put("43", "湖南");   
         hashtable.put("44", "广东");   
         hashtable.put("45", "广西");   
         hashtable.put("46", "海南");   
         hashtable.put("50", "重庆");   
         hashtable.put("51", "四川");   
         hashtable.put("52", "贵州");   
         hashtable.put("53", "云南");   
         hashtable.put("54", "西藏");   
         hashtable.put("61", "陕西");   
         hashtable.put("62", "甘肃");   
         hashtable.put("63", "青海");   
         hashtable.put("64", "宁夏");   
         hashtable.put("65", "新疆");   
         hashtable.put("71", "台湾");   
         hashtable.put("81", "香港");   
         hashtable.put("82", "澳门");   
         hashtable.put("91", "国外");   
         return hashtable;   
     }  
}
