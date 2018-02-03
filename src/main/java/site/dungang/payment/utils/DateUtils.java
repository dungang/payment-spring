package site.dungang.payment.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.FastDateFormat;

public class DateUtils {
	public static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT_YMDHMS_ = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmm";
	public static final String YMDHMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD_FORMAT = "yyyy-MM-dd";
	public static final String YMD_POINT_FORMAT = "yyyy.MM.dd";
	private static final String YMDFORMAT = "yyyyMMdd";
	public static FastDateFormat dateFastFormat = FastDateFormat.getInstance(DATE_FORMAT);
	public static FastDateFormat ymdhmsFastFormat = FastDateFormat.getInstance(YMDHMS_FORMAT);
	public static FastDateFormat DATEFASTFORMAT_YMDHMS = FastDateFormat.getInstance(DATE_FORMAT_YMDHMS);
	public static FastDateFormat DATEFASTFORMAT_YMDHMS_ = FastDateFormat.getInstance(DATE_FORMAT_YMDHMS_);

	private static FastDateFormat ymdfastformat = FastDateFormat.getInstance(YMDFORMAT);
	private static FastDateFormat ymdFastFormat = FastDateFormat.getInstance(YMD_FORMAT);
	private static FastDateFormat YMD_FASTPOINTFORMAT = FastDateFormat.getInstance(YMD_POINT_FORMAT);

	public static Date getCurrentMonday() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		Date monday = currentDate.getTime();
		return monday;
	}

	public static Date getNextMonday() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek + 7;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		Date monday = currentDate.getTime();
		return monday;
	}

	public static Date getNextHalfOfYear() {
		return getAddMonth(6);
	}

	public static Date getAddMonth(int month) {

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	public static Date getAddMonthForDate(Date date, int month) {

		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}

	/**
	 * 返回昨天
	 * 
	 * @param today
	 * @return
	 */
	public static Date yesterday(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		return calendar.getTime();
	}

	/**
	 * 返回明天
	 * 
	 * @param today
	 * @return
	 */
	public static Date tomorrow(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		return calendar.getTime();
	}

	public static String getCurrentYMDPoint() {
		return YMD_FASTPOINTFORMAT.format(new Date());
	}

	public static String getYMDPoint(Date date) {
		return YMD_FASTPOINTFORMAT.format(date);
	}

	public static String getYMD(Date date) {
		return YMD_FASTPOINTFORMAT.format(date);
	}

	public static String getYMDHMSFormat(Date date) {
		return ymdhmsFastFormat.format(date);
	}

	public static String getCurrFullTime() {
		return dateFastFormat.format(new Date());
	}

	public static String getCurrYMDHMSTime() {
		return DATEFASTFORMAT_YMDHMS.format(new Date());
	}

	public static String getCurrYMDHMSTime_() {
		return DATEFASTFORMAT_YMDHMS_.format(new Date());
	}

	public static Date getYmdHmsDate(String date) {
		try {
			return ymdhmsFastFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getYmdDate(String date) {
		try {
			return ymdFastFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getCurrentYmd() {
		return ymdFastFormat.format(new Date());
	}

	public static String getCurrentYmdHMS() {
		return ymdhmsFastFormat.format(new Date());
	}

	public static String getYmdformat() {
		return ymdfastformat.format(new Date());
	}

	public static String getYmdformat(Date date) {
		return ymdfastformat.format(date);
	}

	/**
	 * 判断session是否失效
	 * 
	 * @param survivalTime
	 * @param createdAt
	 * @return
	 */
	public static boolean isLoseTime(Integer survivalTime, Date createdAt) {
		if (survivalTime == 0) {
			return false;
		}
		Date _date = new Date(createdAt.getTime() + survivalTime * 1000);
		return _date.before(new Date());
	}

	/**
	 * 获取日期格式为yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getDateStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间 00:00:00
	 * @param strDateEnd
	 *            结束时间 00:05:00
	 * @return
	 */
	public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		String nowdate = ymdFastFormat.format(date);
		strDateBegin = nowdate + " " + strDateBegin;
		strDateEnd = nowdate + " " + strDateEnd;
		long startTime = getYmdHmsDate(strDateBegin).getTime();
		long endTime = getYmdHmsDate(strDateEnd).getTime();
		if (date.getTime() >= startTime && date.getTime() <= endTime) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 两个日期的 天数差
	 * 
	 * @param big
	 * @param small
	 * @return
	 */
	public static int getDayGap(Date dateStart, Date dateEnd) {
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(dateStart);
		cStart.set(Calendar.HOUR_OF_DAY, 0);
		dateStart = cStart.getTime();

		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(dateEnd);
		cEnd.set(Calendar.HOUR_OF_DAY, 0);
		dateEnd = cEnd.getTime();

		long start = dateStart.getTime() / (1000 * 3600 * 24);
		long end = dateEnd.getTime() / (1000 * 3600 * 24);
		return ((int) (end - start));
	}

	public static void main(String[] args) {
		Date dateCheckIn = getYmdHmsDate("2017-12-12 23:59:59");// "yyyy-MM-dd HH:mm:ss";
		Date dateCheckOut = getYmdHmsDate("2017-12-13 1:01:01");
		System.out.println(getDayGap(dateCheckIn, dateCheckOut));

		long result = System.currentTimeMillis() + 30 * 60 * 1000;
		Date date = new Date(result);
		System.out.println(getDateStr(date));
	}
}
