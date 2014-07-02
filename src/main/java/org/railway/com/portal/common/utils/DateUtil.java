
package org.railway.com.portal.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.type.LocaleType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;


public class DateUtil {
	public static final Long DATE_DAY_LONG = 1l;
	//一天时间得毫秒数
	public static final Long DATE_DAY_MILLISECOND = 24*60*60*1000*DATE_DAY_LONG;
	private static String defaultDatePattern = "yyyy-MM-dd hh:mm:ss";
	private static String defaultDatePattern1 = "yyyy-MM-dd";
	private static String defaultDatePattern2 = "yyyyMMdd";
	
	
	/**
	 * 
	 * @param startTime yyyy-MM-dd HH:mm
	 * @param endTime yyyy-MM-dd HH:mm
	 * @return
	 */
	public static String calcBetweenTwoTimes(String startTime,String endTime){
		String returnStr = "";
		try{
			
		
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   java.util.Date begin=dfs.parse(startTime);
		   java.util.Date end = dfs.parse(endTime);
		   long between=(end.getTime()-begin.getTime())/1000/60;//除以1000是为了转换成秒

		   long day1=between/(24*60);
		   long hour1=between%(24*60)/60;
		   long minute1=between%60;
		   if(day1==0){
			   if(hour1 ==0){
				   returnStr = minute1+"分";
			   }else{
				   returnStr =  hour1 + "小时" + minute1+"分";
			   }
		   }else{
			   returnStr = day1+"天" + hour1 + "小时" + minute1+"分";
		   }
		   
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnStr;
	}
	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parseDate(String strDate)  {
		Date date = null;
		try {
			date =  strDate == null ? null : parseDate(strDate,defaultDatePattern);
		} catch (ParseException e) {
			
		}
		return date;
	}
	
	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate)  {
		Date date = null;
		try {
			date =  strDate == null ? null : parseDate(strDate,defaultDatePattern1);
		} catch (ParseException e) {
			
		}
		return date;
	}
	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parseDate(String strDate, String pattern)
			throws ParseException {
		return strDate == null ? null : new SimpleDateFormat(
				pattern).parse(strDate);
	}
	
	/**
	 * 试用参数Format格式化Calendar成字符串
	 * 
	 * @param cal
	 * @param pattern
	 * @return
	 */
	public static String format(Calendar cal, String pattern) {
		return cal == null ? "" : new SimpleDateFormat(pattern).format(cal
				.getTime());
	}
	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 按照给定的Long型数据,获得指定格式的时间字符串
	 * @param time
	 * @param format
	 * @return
	 */
	public static String getDateTime(Long time,String format){
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(time);
	}
	
	
	public static Long getNowStamp(){
		return (new Date().getTime())/1000;
	}
	
	
	
	
	
	
	
	/**
	 * 获取当前时间的前/后的一段时间
	 * @param day 前几天时间 (day等于0则取当前时间),day为正数则时间向前推，day为负数时间向后推
	 * @param strDate 输入的日期，格式yyyy-yy-dd
	 * @return 返回格式如：2012-10-10
	 */
	public static String getDateByDay(String strDate,int day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date data = new Date();
		String startTime = "";
		try {
			data = strDate == null ? null : sdf.parse(strDate);
			long time = data.getTime();
			//如果传入的参数为0 则取当前时间
			if(day<1 && day >-1){
				
			}else{
				long timeOld10 = DATE_DAY_MILLISECOND*day;
				time = time - timeOld10;
			}
		    startTime = sdf.format(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return startTime;
	}
	
	/**
	 * 获取yyyy-MM-dd HH:mm:ss格式的时间
	 * @param timestamp
	 * @return
	 */
	public static String getStringTimestamp(Long timestamp){
		Timestamp ts = new Timestamp(timestamp);  
        String tsStr = "";  
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
            //方法一  
            tsStr = sdf.format(ts);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return tsStr;
	}
	
	/**
	 * 
	 * @param date 格式yyyyMMdd
	 * @return 格式yyyy-MM-dd
	 */
	public static String formateDate(String date){
		String returntime = "";
		try {
			returntime = format(parseDate(date,defaultDatePattern2),defaultDatePattern1);
		} catch (ParseException e) {
			
		}
		return returntime;
	}
	
	/**
	 * 给定开始日期和天数，生成日期队列
	 * @param startDate 格式yyyy-mm-dd
	 * @param dayCount 天数
	 * @return
	 */
	public static List<String> getDateListWithDayCount(String startDate,int dayCount){
		 List<String> days = new ArrayList<String>();
		 for(int i = 0;i<dayCount;i++){
			 days.add(getDateByDay(startDate,-i));
		 }
		// System.out.println("" + days);
		 return days;
	}
	
	/**
	 * 计算两个日期之间相隔的天数
	 * @param startDate格式为yyyy-MM-dd
	 * @param endDate格式为yyyy-MM-dd
	 * @return
	 */
	public static int getDaysBetween(String startDate,String endDate){
		DateTime start = new DateTime(startDate);
		DateTime end = new DateTime(endDate);
		return Days.daysBetween(start, end).getDays();
	}
	/**
	 * 将yyyyMMdd格式时间转为yyyy-MM-dd格式
	 * @param day 格式为yyyyMMdd
	 * @return 格式为yyyy-MM-dd
	 */
	public static String getFormateDay(String day){
		Date date = null;
		try {
			date = parseDate(day,defaultDatePattern2);
		} catch (ParseException e) {
			
			//e.printStackTrace();
		}
		return format(date,defaultDatePattern1);
	}
	
	
	/**
	 * 计算两个时间的差值
	 * @param dateStart 格式yyyy-MM-dd HH:mm:ss
	 * @param dateStop 格式yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static int getBetweenMinite(String dateStart,String dateStop){
		//String dateStart = "2012-01-14 09:30:58";
	    //String dateStop = "2012-01-14 10:30:48";
        int result = 0;
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    Date d100 = null;
	    Date d200 = null;

	    try {
	    	d100 = format.parse(dateStart);
	    	d200 = format.parse(dateStop);

	        DateTime dt100 = new DateTime(d100);
	        DateTime dt200 = new DateTime(d200);

	        result = Minutes.minutesBetween(dt100, dt200).getMinutes();
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
           return result;
	  }
	
	/**
	 * 将yyyy-mm-dd格式转换成yyyyMMdd格式
	 * @param day  yyyy-mm-dd
	 * @return yyyyMMdd
	 */
	public static String getFormateDayShort(String day){
		Date date = null;
		try {
			date = parseDate(day,defaultDatePattern1);
		} catch (ParseException e) {
			
			//e.printStackTrace();
		}
		return format(date,defaultDatePattern2);
	}
	
	/**
	 * 时间格式转换
	 * @param day  传入的日期
	 * @param formateType formate格式字符串
	 * @return yyyyMMdd
	 */
	public static String getFormateDayTime(String day,String formateType){
		Date date = null;
		try {
			date = parseDate(day,formateType);
		} catch (ParseException e) {
			
			//e.printStackTrace();
		}
		return format(date,defaultDatePattern2);
	}
	
	public static void main(String[] args) {
	
		System.err.println(getFormateDayShort("2014-04-12"));
		/**
		 * 1.构造一个时间
		 */
		//方法1：取系统时间
		DateTime dt1 = new DateTime();
		
		//方法2：通过java.util.Date对象生成
		DateTime dt2 = new DateTime(new Date());
		
		//方法3：通过Long (milliseconds，
		//即距离 1970 年 1 月 1 日子时格林威治标准时间（也称为 epoch）以来的毫秒数)
		DateTime dt3 = new DateTime(System.currentTimeMillis());
		
		//方法4：通过Calendar实例构建
		DateTime dt4 = new DateTime(Calendar.getInstance());
		
		//方法5：指定年月日点分秒生成(参数依次是：年,月,日,时,分,秒,毫秒) 
		DateTime dt5 = new DateTime(2014, 6, 03, 13, 14, 0, 0);
		
		//方法6：ISO8601形式生成  
		DateTime dt6 = new DateTime("2012-05-20");  
		DateTime dt7 = new DateTime("2012-05-20T13:14:00");
		
		//只需要年月日的时候  
		LocalDate localDate = new LocalDate(2009, 9, 6);// September 6, 2009  
		  
		//只需要时分秒毫秒的时候  
		LocalTime localTime = new LocalTime(13, 30, 26, 0);// 1:30:26PM 
		
	  
		/**
		 * 2.获取年月日点分秒
		 */
		DateTime dt = new DateTime();  
		//年  
		int year = dt.getYear();  
		//月  
		int month = dt.getMonthOfYear();  
		//日  
		int day = dt.getDayOfMonth();  
		//星期  
		int week = dt.getDayOfWeek();  
		//点  
		int hour = dt.getHourOfDay();  
		//分  
		int min = dt.getMinuteOfHour();  
		//秒  
		int sec = dt.getSecondOfMinute();  
		//毫秒  
		int msec = dt.getMillisOfSecond();  
		
		/**
		 * 3.与JDK日期对象的转换
		 */
		DateTime dtime = new DateTime();  
		//DateTime对象转换成java.util.Date对象
		Date d1 = new Date(dtime.getMillis());
		Date d2 = dtime.toDate();
		//DateTime对象转换成java.util.Calendar对象 
		Calendar c1 = Calendar.getInstance();  
		c1.setTimeInMillis(dt.getMillis());  
		Calendar c2 = dtime.toCalendar(Locale.getDefault());
		
		/**
		 * 4.日期前后推算
		 */
		DateTime dateTime = new DateTime();  
		  
		//昨天  
		DateTime yesterday = dateTime.minusDays(1);         
		//明天  
		DateTime tomorrow = dateTime.plusDays(1);       
		//1个月前  
		DateTime before1month = dateTime.minusMonths(1);        
		//3个月后  
		DateTime after3month = dateTime.plusMonths(3);          
		//2年前  
		DateTime before2year = dateTime.minusYears(2);          
		//5年后  
		DateTime after5year = dateTime.plusYears(5);
		
		/**
		 * 5.特殊日期的计算
		 */
		
		//月末日期    
		DateTime lastday = dt.dayOfMonth().withMaximumValue();  
		  
		//90天后那周的周一  
		DateTime firstday = dt.plusDays(90).dayOfWeek().withMinimumValue(); 
	
		/**
		 * 6.时区
		 */
		//默认设置为日本时间  
		DateTimeZone.setDefault(DateTimeZone.forID("Asia/Tokyo"));  
		DateTime dt8 = new DateTime();  
		  
		//伦敦时间  
		DateTime dt9 = new DateTime(DateTimeZone.forID("Europe/London")); 
	   
		/**
		 * 7.区间计算
		 */
		DateTime begin = new DateTime("2012-02-01");  
		DateTime end = new DateTime("2012-05-01");  
		  
		//计算区间毫秒数  
		Duration d = new Duration(begin, end);  
		long time = d.getMillis();  
		  
		//计算区间天数  
		Period p = new Period(begin, end, PeriodType.days());  
		int days = p.getDays();  
		  
		//计算特定日期是否在该区间内  
		Interval i = new Interval(begin, end);  
		boolean contained = i.contains(new DateTime("2012-03-01")); 
	
	   /**
	    * 8.日期的比较
	    */
		DateTime d10 = new DateTime("2012-02-01");  
		DateTime d11 = new DateTime("2012-05-01");  
		  
		//和系统时间比  
		boolean b1 = d10.isAfterNow();  
		boolean b2 = d10.isBeforeNow();  
		boolean b3 = d10.isEqualNow();  
		  
		//和其他日期比  
		boolean f1 = d10.isAfter(d11);  
		boolean f2 = d10.isBefore(d11);  
		boolean f3 = d10.isEqual(d11); 
		
		/**
		 * 9.格式化输出
		 * Joda的日期格式化提供了简单的API接口：toString()，如果你需要的话，
		 * 也可以传一个ISO-8601或者一个JDK控制的字符串格式，来告诉API如何输出日期格式。
		 * 方法有如下：
		 * toString(DateTimeFormatter formatter)
		 * toString(String pattern)
		 * toString(String pattern,Local local)
		 */
		String s1 = dateTime.toString("yyyy/MM/dd hh:mm:ss.SSSa");  
		String s2 = dateTime.toString("yyyy-MM-dd HH:mm:ss");  
		String s3 = dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa");  
		String s4 = dateTime.toString("yyyy/MM/dd HH:mm ZZZZ");  
		String s5 = dateTime.toString("yyyy/MM/dd HH:mm Z");  
		String s6 = dateTime.toString("yyyy-MM-dd",Locale.CHINESE);
		
//		DateTime dd = new DateTime("2014-06-07 10:20:30");
		LocalDate da = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseLocalDate("2014-06-07 17:55:00");
		
		//System.err.println("" + da.toString("yyyy-MM-dd"));
		
		//System.err.println(format(parseDate("2014-06-07 10:20:30"),"yyyy-MM-dd"));
		//System.err.println(da.toString("yyyy-MM-dd"));
		
	}
		
		
	
	
	
}



