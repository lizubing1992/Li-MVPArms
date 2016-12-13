package com.jess.arms.utils;


import android.content.Context;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


	// yyyy-MM-dd hh:mm:ss 12小时制
	// yyyy-MM-dd HH:mm:ss 24小时制

	public static final String TYPE_01 = "yyyy-MM-dd HH:mm:ss";
	public static final String TYPE_02 = "yyyy-MM-dd";
	public static final String TYPE_03 = "HH:mm:ss";
	public static final String TYPE_04 = "yyyy年MM月dd日";
	public static final String TYPE_05 = "yyyy-MM-dd HH:mm";
	public static final String TYPE_06 = "yyyy.MM.dd";

	/**
	 * 根据指定的格式格式话 毫秒数
	 *
	 * @param time
	 * @param pattern
	 * @return 格式化后的String
	 */
	public static String formatDate(long time, String pattern) {
		if (time == 0) return "- -";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}

	public static String formatDate(String longStr, String pattern) {
		if (StringUtils.isEmpty(longStr)) return "- -";
		try {
			return formatDate(Long.parseLong(longStr), pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "- -";
	}

	public static String formatDate2Hnt(String longStr, String pattern) {
		try {
			return formatDate(Long.parseLong(longStr), pattern);
		} catch (Exception e) {
		}
		return "- -";
	}

	public static long formatStr(String timeStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(timeStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 转换long型日期格式
	 *
	 * @param context
	 * @param date
	 * @return
	 */
	public static String formateDateNow(Context context, long date) {
		int format_flags = android.text.format.DateUtils.FORMAT_NO_NOON_MIDNIGHT
				| android.text.format.DateUtils.FORMAT_ABBREV_ALL | android.text.format.DateUtils.FORMAT_CAP_AMPM
				| android.text.format.DateUtils.FORMAT_SHOW_DATE | android.text.format.DateUtils.FORMAT_SHOW_DATE
				| android.text.format.DateUtils.FORMAT_SHOW_TIME;
		return android.text.format.DateUtils.formatDateTime(context, date, format_flags);
	}

	/**
	 * 获取当前的时间
	 *
	 * @param context
	 * @return
	 */
	public static String getTime(Context context) {
		return formateDateNow(context, System.currentTimeMillis());
	}

	/**
	 * 以友好的方式显示时间
	 *
	 * @param sdate
	 * @return
	 */
	public static String friendlyTime(String sdate) {
		String timeStr;
		try {
			if (StringUtils.isEmpty(sdate))
				return "";

			Date date = toDate(sdate);
			if (date == null) {
				return "";
			}

			Calendar cal = Calendar.getInstance();
			// 判断是否是同一天
			String curDate = dateFormater.get().format(cal.getTime());
			String paramDate = dateFormater.get().format(date);
			if (curDate.equals(paramDate)) {
				int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
				if (hour == 0)
					timeStr = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
				else
					timeStr = hour + "小时前";
				return timeStr;
			}
			long lt = date.getTime() / 86400000;
			long ct = cal.getTimeInMillis() / 86400000;
			int days = (int) (ct - lt);
			if (days == 0 || days == 1) {
				int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
				if (hour == 0)
					timeStr = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
				else
					timeStr = hour + "小时前";
			} else {
				timeStr = formatDate(date.getTime(), TYPE_02);
			}
		} catch (Exception e) {
			e.printStackTrace();
			timeStr = "";
		}
		return timeStr;
	}
	/**
	 * 比较两个时间的前后顺序
	 * @param firstDate 第一时间
	 * @param secondDate 第二时间
	 * @return true 代表第一个时间晚于第二个时间
	 */
	public static boolean isEarly(String firstDate,String secondDate) {
		Date date = toDate(firstDate);
		Date sdate = toDate(secondDate);
		if (sdate != null&& date != null) {
//			LogUtils.e("date.getTime()="+date.getTime()+"****sdate.getTime()="+sdate.getTime());
			if(date.getTime() - sdate.getTime() >0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * 将字符串转位日期类型
	 *
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static String changeYmd(String strYmd) {
		if (strYmd == null) {
			return " ";
		} else {
			strYmd = strYmd.replace("-", "");
			return strYmd.substring(0, 4) + "年" + strYmd.substring(4, 6) + "月";
		}

	}

	/**
	 * @param timestr
	 * @return
	 */
	public static String getFormatDate(String timestr) {
		if (timestr != null) {
			int index = timestr.indexOf(" ");
			if (index > 0) {
				return timestr.substring(0, index);
			} else {
				return timestr;
			}
		} else {
			return "";
		}
	}

	/**
	 * 获取年-月并去掉“-”的数字字符串
	 *
	 * @param date
	 * @return
	 */
	public static long changeStringDateToLong(String date) {
		date = date.substring(0, 7);
		String tmp = date.replace("-", "");
		//LogUtils.i(tmp);
		return Long.parseLong(tmp);
	}

	/**
	 * 日期格式字符串转换成时间戳
	 *
	 * @param dateStr 字符串日期
	 * @param format  如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long date2TimeStamp(String dateStr, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr).getTime() / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 当前时间是否已经过了该时间
	 *
	 * @param time 指定的时间  单位毫秒
	 * @return
	 */
	public static boolean isExpired(long time) {
		if (time == 0) return false;
		long currentTime = System.currentTimeMillis();
		return currentTime > time;
	}
}
