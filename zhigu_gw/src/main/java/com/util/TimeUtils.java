package com.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * 
 */
public class TimeUtils {

	public static String DEFAULT_PATTERN = "yyyy-MM-dd";

	public static String DEFAULT_PATTERN_MMINIT = "yyyy-MM-dd HH:mm:ss";

	public static String HH_PATTERN = "HH";

	public static String YYYYMMDD_PATTERN = "yyyyMMdd";

	/**
	 * 把当前时间转化为时间戳
	 * 
	 * @return
	 */
	public static String datetimeStr() {
		String datetimeStr = "";
		Date date = new Date();
		datetimeStr = date.getTime() + "";
		return datetimeStr;
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String timeStamp1Date(String seconds) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @return yyyy-MM-dd
	 */
	public static String timeStamp2Date(String seconds) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		String format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}

	/**
	 * 根据类型 把字符串转换成日期
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date convert2Date(String strDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * strDate 默认格式 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date convertStr2Date(String strDate) {

		return convert2Date(strDate, TimeUtils.DEFAULT_PATTERN);
	}

	public static Date convertMill2Day(Long millis) {
		Calendar cal = Calendar.getInstance();

		cal.setTimeInMillis(millis);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	public static Date convert2Day() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	public static String convertDate2Str(Date date, String pattern) {

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(date);
	}

	/**
	 * 把日期格式为Date类型转为String类型
	 * 
	 * @param date
	 * @return "yyyy-MM-dd"
	 */
	public static String convertDate2Str(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.DEFAULT_PATTERN);

		return sdf.format(date);
	}

	/**
	 * 把日期格式为Date类型转为String类型
	 * 
	 * @param date
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String convertDate3Str(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(TimeUtils.DEFAULT_PATTERN_MMINIT);

		return sdf.format(date);
	}

	/**
	 * 
	 * @Title: getYesterDay @Description: 获取昨天的日期 @param @return String @throws
	 */
	public static String getYesterDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date date = new Date(cal.getTimeInMillis());

		return convertDate2Str(date);
	}

	/**
	 * 获取昨天所在周的周一
	 * 
	 * @return String
	 */
	public static String getYesterDayWeekDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date date = new Date(cal.getTimeInMillis());

		return convertDate2Str(date);
	}

	/**
	 * 获取昨天所在月的第一天
	 * 
	 * @return String
	 */
	public static String getYesterDayMonthDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date date = new Date(cal.getTimeInMillis());

		return convertDate2Str(date);
	}

	/**
	 * 取得指定日期所在周的周一
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
		return calendar.getTime();
	}

	/**
	 * 取得指定日期所在周的周一
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfWeek(String date) {
		Date speDay = convertStr2Date(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(speDay);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
		return convertDate2Str(calendar.getTime());
	}

	/**
	 * 取得指定日期所在周的周日
	 *
	 * @param date
	 * @return Date
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Sunday
		return calendar.getTime();
	}

	/**
	 * 取得指定日期所在周的周日
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastDayOfWeek(String date) {
		Date speDay = convertStr2Date(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(speDay);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Sunday
		return convertDate2Str(calendar.getTime());
	}

	/**
	 * 返回指定日期所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期所在月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(String date) {
		Date speDay = convertStr2Date(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(speDay);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		return convertDate2Str(calendar.getTime());
	}

	/**
	 * 返回指定日期的月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastDayOfMonth(String date) {
		Date speDay = convertStr2Date(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(speDay);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return convertDate2Str(calendar.getTime());
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间 "yyyy-MM-dd"
	 * @param bdate
	 *            较大的时间 "yyyy-MM-dd"
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(convertStr2Date(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(convertStr2Date(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearFirst(Date date) {
		Calendar lendar = Calendar.getInstance();
		lendar.setTime(date);
		int year = lendar.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getYearFirst(String dateStr) {
		Date date = convertStr2Date(dateStr);
		Calendar lendar = Calendar.getInstance();
		lendar.setTime(date);
		int year = lendar.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		return convertDate2Str(calendar.getTime());
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearLast(Date date) {
		Calendar lendar = Calendar.getInstance();
		lendar.setTime(date);
		int year = lendar.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String getYearLast(String dateStr) {
		Date date = convertStr2Date(dateStr);
		Calendar lendar = Calendar.getInstance();
		lendar.setTime(date);
		int year = lendar.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		return convertDate2Str(calendar.getTime());
	}

	/**
	 * 返回一天的开始与结束时间
	 * 
	 * @param date
	 * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
	 *       1 返回yyyy-MM-dd 23:59:59日期
	 * @return
	 */
	public static Date getZeroDateByFlag(Date date, Integer flag) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (flag == 0) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		} else if (flag == 1) {
			// 凌晨23:59:59
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}
		return cal.getTime();
	}

	/**
	 * 当天的开始时间
	 * 
	 * @return
	 */
	public static long startOfTodDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = calendar.getTime();
		return date.getTime();
	}

	/**
	 * 当天的结束时间
	 * 
	 * @return
	 */
	public static long endOfTodDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date date = calendar.getTime();
		return date.getTime();
	}

	/**
	 * 昨天的开始时间
	 * 
	 * @return
	 */
	public static long startOfyesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = calendar.getTime();
		return date.getTime();
	}

	/**
	 * 昨天的结束时间
	 * 
	 * @return
	 */
	public static long endOfyesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.add(Calendar.DATE, -1);
		Date date = calendar.getTime();
		return date.getTime();
	}

	/**
	 * 功能：获取上周的开始时间
	 */
	public static long startOfLastWeek() {// 当周开始时间
		return startOfThisWeek() - 7 * 24 * 60 * 60 * 1000;
	}

	/**
	 * 功能：获取上周的结束时间
	 */
	public static long endOfLastWeek() {// 当周开始时间
		return endOfThisWeek() - 7 * 24 * 60 * 60 * 1000;
	}

	/**
	 * 功能：获取本周的开始时间 示例：2013-05-13 00:00:00
	 */
	public static long startOfThisWeek() {// 当周开始时间
		Calendar currentDate = Calendar.getInstance();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date date = currentDate.getTime();
		return date.getTime();
	}

	/**
	 * 功能：获取本周的结束时间 示例：2013-05-19 23:59:59
	 */
	public static long endOfThisWeek() {// 当周结束时间
		Calendar currentDate = Calendar.getInstance();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.MILLISECOND, 999);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date date = currentDate.getTime();
		return date.getTime();
	}

	/**
	 * 功能：获取本月的开始时间
	 */
	public static long startOfThisMonth() {// 当周开始时间
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		currentDate.set(Calendar.DAY_OF_MONTH, 1);
		Date date = currentDate.getTime();
		return date.getTime();
	}

	public static long endOfThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		Date date = cal.getTime();
		return date.getTime();
	}

	/**
	 * 功能：获取上月的开始时间
	 */
	public static long startOfLastMonth() {// 当周开始时间
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		currentDate.set(Calendar.DAY_OF_MONTH, 1);
		currentDate.add(Calendar.MONTH, -1);
		Date date = currentDate.getTime();
		return date.getTime();
	}

	/**
	 * 功能：获取上月的结束时间
	 */
	public static long endOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DATE, -1);
		Date date = cal.getTime();
		return date.getTime();
	}

	/**
	 * 功能：获取上月的结束日期
	 */
	public static Date endDateOfLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 根据long返回year
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static Object[] theYearOfTime(long milliseconds) {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		int thisYear = date.getYear() + 1900;
		cal.setTimeInMillis(milliseconds);
		date = cal.getTime();
		int regirsterYear = date.getYear() + 1900;
		if (regirsterYear < thisYear) {
			List<Integer> yearL = new ArrayList<Integer>();
			for (int i = regirsterYear; i <= thisYear; i++) {
				yearL.add(i);
			}
			return yearL.toArray();
		} else {
			return new Object[] { thisYear };
		}
	}

	/**
	 * 功能：获取本年的开始时间
	 */
	public static long startOfTheYear(int year) {// 当周开始时间
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.YEAR, year);
		currentDate.set(Calendar.MONTH, 0);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);
		currentDate.set(Calendar.DAY_OF_MONTH, 1);
		Date date = currentDate.getTime();
		return date.getTime();
	}

	/**
	 * 功能：获取本年的结束时间
	 */
	public static long endOfTheYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date date = cal.getTime();
		return date.getTime();
	}

	/**
	 * 根据日期获取年份
	 * 
	 * @param args
	 */
	public static int getYearByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 * @return true 是闰年 false 不是闰年
	 */
	public static Boolean isLeapYear(Integer year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据日期获取月份
	 * 
	 * @param args
	 */
	public static int getMonthByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 根据日期获取日
	 * 
	 * @param args
	 */
	public static int getDayByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 给指定 日期 date 加 n 个年数
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date yearAdd(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, n);// 自动加n个月
		return calendar.getTime();
	}
	
	/**
	 * 给指定 日期 date 加 n 个月数
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date monthAdd(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);// 自动加n个月
		return calendar.getTime();
	}

	/**
	 * 给指定 日期 date 加 n 天
	 * 
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date dateAdd(Date date, int n) {

		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(date);
		fromCal.add(Calendar.DATE, n);
		return fromCal.getTime();
	}

	/**
	 * 计算两个日期相差几个月
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static int getMonthOfTwo(Date minDate, Date maxDate) {
		try {
			int result = 0;
			int month = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

			Calendar bef = Calendar.getInstance();
			Calendar aft = Calendar.getInstance();
			bef.setTime(sdf.parse(convertDate2Str(minDate)));
			aft.setTime(sdf.parse(convertDate2Str(maxDate)));
			result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
			month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
			return result + month;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 计算两个日期相差几个月
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static int getMonthOfTwo(String minDate, String maxDate) {
		try {
			int result = 0;
			int month = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

			Calendar bef = Calendar.getInstance();
			Calendar aft = Calendar.getInstance();
			bef.setTime(sdf.parse(minDate));
			aft.setTime(sdf.parse(maxDate));
			result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
			month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
			return result + month;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 计算时间差
	 * @param begin
	 * @param end
	 * @return 返回格式,"hh:mm:ss" 
	 */
	public static String getTimeDifference(Date begin, Date end) {
		long between = (end.getTime() - begin.getTime()) / 1000;
		// 除以1000是为了转换成秒
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60;
		StringBuffer time = new StringBuffer();
		if (hour != 0) {
			time.append(hour + ":");
		}
		if (time.length() != 0) {
			time.append(String.format("%02d:", minute));
		} else if (minute != 0) {
			time.append(String.format("%d:", minute));
		}
		if (time.length() != 0) {
			time.append(String.format("%02d", second));
		} else {
			time.append(second);
		}
		return time.toString();
	}

	// main 函数
	public static void main(String[] args) throws ParseException {
//		System.out.println(getMonthOfTwo("2016-11-8", "2016-12-01"));
		System.out.println(getMonthOfTwo("2016-11-01", "2015-12-30"));
	
	
	}

}
