package com.fsmeeting.live.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * 
 * @author yicai.liu<moon>
 *
 */
public class DateTimeUtils {

	public static final String DATEANDTIME = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORTDATE = "yy-MM-dd";
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_NO_SPLIT = "yyyyMMdd";
	public static final String DATE_TIME_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_TIME_MILLISECOND_NO_OPERATE = "yyyyMMddHHmmssSSS";
	public static final String WEEK_OF_MONTH = "yyyy-MM-W";
	public static final String MONTH = "yyyy-MM";

	private static String BIGDATE[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	/**
	 * 获取当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String nowToString() {
		Calendar cal = Calendar.getInstance();
		return dateToString(cal, DATEANDTIME);

	}

	/**
	 * @Description 返回日期（yyyy-MM-dd）
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		DateFormat df = new SimpleDateFormat(DATE);
		return df.format(date);
	}

	public static String dateToString(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 
	 * @Description 得到前一天的数据
	 * @param date
	 * @return
	 */
	public static Date getYesterDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	/**
	 * 将时间戳转换格式，
	 * 
	 * @param long
	 *            type time
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String nowToStringForLong(Long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return dateToString(cal, DATEANDTIME);
	}

	/**
	 * 获取格式化后的当前时间
	 * 
	 * @param outputFormat
	 *            需要格式成的形式，如果为空，则默认格式化为"YYYY-MM-DD hh:mm:ss"
	 * @return date String
	 */
	public static String nowToString(String outputFormat) {
		Calendar cal = Calendar.getInstance();
		if (outputFormat == null)
			return dateToString(cal, DATEANDTIME);
		else
			return dateToString(cal, outputFormat);
	}

	public static String dateToString(Calendar cal, String outputFormat) {
		if (cal == null)
			return "";
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (month.length() < 2)
			month = "0" + month;
		String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		if (date.length() < 2)
			date = "0" + date;
		String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		if (hour.length() < 2)
			hour = "0" + hour;
		String minute = String.valueOf(cal.get(Calendar.MINUTE));
		if (minute.length() < 2)
			minute = "0" + minute;
		String second = String.valueOf(cal.get(Calendar.SECOND));
		if (second.length() < 2)
			second = "0" + second;
		String millisecond = String.valueOf(cal.get(Calendar.MILLISECOND));
		switch (millisecond.length()) {
		case 2:
			millisecond = "0" + millisecond;
			break;
		case 1:
			millisecond = "00" + millisecond;
			break;
		default:
			break;
		}

		String output = "";
		char mask;

		// outputFormat will be null if there are no resources.
		int n = outputFormat != null ? outputFormat.length() : 0;
		for (int i = 0; i < n; i++) {
			mask = outputFormat.charAt(i);
			switch (mask) {
			case 'M':
				output += month;
				i++;
				break;
			case 'd':
				output += date;
				i++;
				break;
			case 'y':
				if (outputFormat.charAt(i + 2) == 'y') {
					output += year;
					i += 3;
				} else {
					output += year.substring(2, 4);
					i++;
				}
				break;
			case 'H':
				output += hour;
				i++;
				break;
			case 'm':
				output += minute;
				i++;
				break;
			case 's':
				output += second;
				i++;
				break;
			case 'S':
				output += millisecond;
				i += 2;
				break;
			default:
				output += mask;
				break;
			}
		}
		return output;
	}

	/**
	 * 为日期加上时间,不然统计会少一天
	 */
	public static String dateToDateTime(String date, boolean beginOfTheDate) {
		if (beginOfTheDate) {
			return date + " 00:00:00";
		} else {
			return date + " 23:59:59";
		}
	}

	/**
	 * 查询报表时，只传送月份，所以把月份转为日期类型
	 */
	public static String monthToDate(String year, String month, boolean isBeginOfMonth) {
		String tempYear = "";
		if (year == null || year.trim().equals("")) {
			Date d = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			tempYear = Integer.toString(cal.get(Calendar.YEAR));
		} else {
			tempYear = year;
		}

		if (isBeginOfMonth)
			return tempYear + "-" + month + "-01";
		else
			return tempYear + "-" + month + "-" + getLastDayOfMonth(year, month);
	}

	/**
	 * 取给定月份的最后一天
	 */
	public static String getLastDayOfMonth(String year, String month) {
		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		// 月，因为Calendar里的月是从0开始，所以要-1
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月份加一，得到下个月的一号
		cal.add(Calendar.MONTH, 1);
		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);
		return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
	}

	/**
	 * 当前年份
	 */
	public static Integer getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 当前月份
	 */
	public static Integer getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 当前日期天
	 */
	public static Integer getCurrentDate() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 字符串转换为Date
	 * 
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Date strToDate(String str) {
		try {
			if (str.trim().length() == 10)
				str = dateToDateTime(str, true);
			DateFormat df = new SimpleDateFormat(DATEANDTIME);
			return df.parse(str.toString());
		} catch (ParseException e) {
			throw new RuntimeException("转换错误：", e);
		}
	}

	public static Date strToDate(String str, String foramt) {
		try {
			DateFormat df = new SimpleDateFormat(foramt);
			return df.parse(str.toString());
		} catch (ParseException e) {
			throw new RuntimeException("转换错误：", e);
		}
	}

	/**
	 * 两个日期之间相隔天数的共通
	 * 
	 * @param dateFrom
	 *            String
	 * @param dateEnd
	 *            String
	 * @return 天数 Long
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Long getDays(String dateFrom, String dateEnd) {
		Date dtFrom = null;
		Date dtEnd = null;
		dtFrom = strToDate(dateFrom);// yyyy-MM-dd
		dtEnd = strToDate(dateEnd);

		long begin = dtFrom.getTime();
		long end = dtEnd.getTime();
		long inter = end - begin;
		if (inter < 0)
			inter = inter * (-1);
		long dateMillSec = 24 * 60 * 60 * 1000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;

		if (remainder != 0)
			dateCnt++;
		return dateCnt;
	}

	/**
	 * 两个日期年份相差
	 * 
	 * @param dateFrom
	 *            String
	 * @return 年数 Integer
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static Integer getYears(String dateFrom) {
		Date dtFrom = null;
		Calendar calFrom = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		dtFrom = strToDate(dateFrom);// yyyy-MM-dd
		calFrom.setTime(dtFrom);
		return (calEnd.get(Calendar.YEAR) - calFrom.get(Calendar.YEAR));
	}

	// 将日期转换成大写
	public static String changeDateToBigDate(String inStr) {
		if (inStr.length() == 1)
			inStr = "0" + inStr;
		Integer number = Integer.parseInt(inStr);
		String RMBStr = "";
		int len, n;
		len = inStr.length();
		boolean isMonthOrDate = true;
		if (len > 15)
			return "数值过大!";// TODO EXCEPTION
		if (number > 31 || number < 10)
			isMonthOrDate = false;
		if (isMonthOrDate) {
			if (len > 2)
				return "月或日的格式不合法";
			String shiWei = BIGDATE[inStr.charAt(0) - '0'] + "拾";

			String geWei = BIGDATE[inStr.charAt(1) - '0'];
			if (geWei.equalsIgnoreCase("零"))
				geWei = "";
			RMBStr += (shiWei + geWei);

		} else {
			for (int i = len - 1; i >= 0; i--) {
				if (inStr.charAt(len - i - 1) == ' ')
					continue;
				n = inStr.charAt(len - i - 1) - '0';
				if (n < 0 || n > 9)
					return "输入含非数字字符!";
				RMBStr += BIGDATE[n];
			}
		}
		return RMBStr;
	}

	/**
	 * 比较日期大小--大于
	 * 
	 * @param date1
	 *            String 起始日期
	 * @param date2
	 *            String 结束日期
	 * @return boolean
	 * @throws ParseException
	 */
	public static boolean isLargeThan(String date1, String date2) {
		Date temp1 = strToDate(date1);
		Date temp2 = strToDate(date2);
		if (temp1.compareTo(temp2) > 0)
			return true;
		return false;
	}

	/**
	 * 比较日期大小--大于等于
	 * 
	 * @param date1
	 *            String 起始日期
	 * @param date2
	 *            String 结束日期
	 * @return boolean *
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static boolean isLargeEqual(String date1, String date2) {
		Date temp1 = strToDate(date1);
		Date temp2 = strToDate(date2);
		return isLargeEqual(temp1, temp2);
	}

	/**
	 * 比较日期大小--大于等于
	 * 
	 * @param date1
	 *            String 起始日期
	 * @param date2
	 *            String 结束日期
	 * @return boolean *
	 * @throws ParseException
	 * @throws ParseException
	 */
	public static boolean isLargeEqual(Date date1, Date date2) {
		if (date1.compareTo(date2) == 0 || date1.compareTo(date2) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获得两个时间时间差
	 * 
	 * @param startdate
	 *            Date 起始日期
	 * @param enddate
	 *            Date 结束日期
	 * @return int
	 */
	public static int getSeconds(Date startdate, Date enddate) {
		long time = enddate.getTime() - startdate.getTime();
		int totalS = new Long(time / 1000).intValue();
		return totalS;
	}

	/**
	 * 比较两个日期字符串的大小
	 * 
	 * @param start
	 * @param end
	 * @author yicai.liu<moon>
	 * @return
	 */
	public static int compare(String start, String end) {
		Date startDate = strToDate(start);
		Date endDate = strToDate(end);

		return startDate.compareTo(endDate);
	}

	/**
	 * 获取两个日期之间所有日期(包括起始结束)
	 * 
	 * @param start
	 * @param end
	 * @author yicai.liu<moon>
	 * @return
	 */
	public static List<String> datesBetween(String start, String end) {

		List<String> dates = new ArrayList<>();

		Calendar temp = Calendar.getInstance();
		temp.setTime(strToDate(start, DATE));
		temp.add(Calendar.DAY_OF_MONTH, -1);

		while (strToDate(end, DATE).after(temp.getTime())) {
			temp.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(dateToString(temp.getTime(), DATE));
		}

		return dates;
	}

	/**
	 * @Description 格式化输入字符串
	 * @param date
	 *            输入日期字符串
	 * @return 格式化后的日期字符串
	 */
	public static String format(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(strToDate(date));
	}
}
