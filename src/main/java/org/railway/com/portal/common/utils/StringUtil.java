package org.railway.com.portal.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * ClassName:StringUtil <br/> 
 * Date:     2013-9-5 下午9:01:54 <br/> 
 * @author   jinwei 
 * @version   
 * @since    JDK 1.6 
 * @see
 */
public class StringUtil {
	
	/**
	 * 字符串转整形
	 * @param obj
	 * @return
	 */
	public static Integer strToInteger(String str){
		Integer result = 0;
		try{
			if("".equals(str) || null == str){
				return 0;
			}
			else{
				result = Integer.parseInt(str);
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		// done
		return result;
	}
	
	public static Long strToLong(String str){
		Long result = 0L;
		try{
			if("".equals(str) || null == str){
				return result;
			}
			else{
				result = Long.parseLong(str);
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		// done
		return result;
	}
	
	public static String objToStr(Object obj){
		String result = "";
		try{
			if(null == obj){
				return result;
			}
			else{
				result = String.valueOf(obj);
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		// done
		return result;
	}
	
	
	public static boolean strIsNull(String obj){
		boolean result = false;
		try{
			if(null == obj || "".equals(obj)){
				return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		// done
		return result;
	}
	
	public static boolean isboolIp(String ipAddress)  
	{  
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." 
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." 
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." 
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
	    Pattern pattern = Pattern.compile(regex);   
	    Matcher matcher = pattern.matcher(ipAddress);   
	    return matcher.matches();   
	} 
	
	public static String summaryToJSON(String summary){
		summary = summary.replace("\n", "");
		char[] summaryChar = summary.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ;i<summaryChar.length;i++){
			if(summaryChar[i]==','){
				int j = i+1;
				//判断是否是空格
				for(;(int)summaryChar[j]==32;j++){
				}
				if(summaryChar[j]!=']'){
					sb.append(summaryChar[i]);
				}
				sb.append(summaryChar[j]);
				i=j;
			}else{
				sb.append(summaryChar[i]);
			}
		}
		return sb.toString(); 
	}
	
	
	
	/** 
	 * isNum:判断一个字符串是否为数字. <br/> 
	 * @author xuhualong 
	 * @param str
	 * @return 
	 * @since JDK 1.6 
	 */ 
	public static boolean isNum(String str){
		try{
			Double.parseDouble(str);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	  
	
	//获取当前主机IP
	public static String getLocalHost() throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost(); 
		return addr.getHostAddress();
	}
	
	public static String handleTime(String time){
		if(time !=null && !"".equals(time)){
			return time.substring(2);
		}
		return null;
	}
	public static void main(String[] args){
		//System.out.println(System.currentTimeMillis());
		//System.out.println(new Date("2014-04-22 10:10:10"));
		//System.out.println("" + handleTime("0:9:0:0"));
		//System.out.println("0:9:0:0".substring(0,1));
		//System.err.println(278910/1000);
	}
}
