//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    public DateUtils() {
    }

    public static Date getDate() {
        return new Date();
    }

    public static Date getDate(int offset) {
        return getDate(getDate(), offset);
    }

    public static Date getDate(Date date, int offset) {
        Date rDate = new Date();
        rDate.setTime(date.getTime() + 86400000L *  Long.valueOf(offset).longValue());
        return rDate;
    }

    public static Date getDate(Date date, long offset) {
        Date rDate = new Date();
        rDate.setTime(date.getTime() + 86400000L * offset);
        return rDate;
    }

    public static Date getDate(String value, String type) {
        Date rtndate = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(type);
            rtndate = sdf.parse(value);
        } catch (ParseException var4) {
            var4.printStackTrace();
        }

        return rtndate;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar xmas = new GregorianCalendar(year, month - 1, day);
        return xmas.getTime();
    }

    public static Date getDate(String year, String month, String day) {
        return getDate((new Integer(year)).intValue(), (new Integer(month)).intValue() - 1, (new Integer(day)).intValue());
    }

    public static String dateFormat(Date date, int datestyle, int timestyle) {
        DateFormat df = DateFormat.getDateTimeInstance(datestyle, timestyle);
        String rtndate = df.format(date);
        return rtndate;
    }

    public static String dateFormat(Date date, String strFormat) {
        DateFormat df = new SimpleDateFormat(strFormat);
        return df.format(date);
    }

    public static String getYear(Date date) {
        return String.valueOf(_getYear(date));
    }

    public static String getMonth(Date date) {
        int month = _getMonth(date);
        return month < 10 ? "0" + month : String.valueOf(month);
    }

    public static String getDay(Date date) {
        int day = _getDay(date);
        return day < 10 ? "0" + day : String.valueOf(day);
    }

    public static String getYear() {
        return getYear(getDate());
    }

    public static String getMonth() {
        return getMonth(getDate());
    }

    public static String getDay() {
        return getDay(getDate());
    }

    public static String getHours(Date date) {
        int hours = _getHours(date);
        return hours < 10 ? "0" + hours : String.valueOf(hours);
    }

    public static String getMinutes(Date date) {
        int minutes = _getMinutes(date);
        return minutes < 10 ? "0" + minutes : String.valueOf(minutes);
    }

    public static String getSeconds(Date date) {
        int seconds = _getSeconds(date);
        return seconds < 10 ? "0" + seconds : String.valueOf(seconds);
    }

    public static int _getYear(Date date) {
        return getCalendar(date).get(1);
    }

    public static int _getMonth(Date date) {
        return getCalendar(date).get(2) + 1;
    }

    public static int _getDay(Date date) {
        return getCalendar(date).get(5);
    }

    public static int _getHours(Date date) {
        return getCalendar(date).get(11);
    }

    public static int _getMinutes(Date date) {
        return getCalendar(date).get(12);
    }

    public static int _getSeconds(Date date) {
        return getCalendar(date).get(13);
    }

    public static Calendar getCalendar(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c;
    }

    public static long getDateDiff(Date date1, Date date2, TimeZone tz) {
        Calendar cal1 = null;
        Calendar cal2 = null;
        if (tz == null) {
            cal1 = Calendar.getInstance();
            cal2 = Calendar.getInstance();
        } else {
            cal1 = Calendar.getInstance(tz);
            cal2 = Calendar.getInstance(tz);
        }

        cal1.setTime(date1);
        long ldate1 = date1.getTime() + (long)cal1.get(15) + (long)cal1.get(16);
        cal2.setTime(date2);
        long ldate2 = date2.getTime() + (long)cal2.get(15) + (long)cal2.get(16);
        long hr1 = ldate1 / 3600000L;
        long hr2 = ldate2 / 3600000L;
        long days1 = hr1 / 24L;
        long days2 = hr2 / 24L;
        return days2 - days1;
    }

    public static long getDateDiff(Date date1, Date date2) {
        if (date1 == null) {
            date1 = getDate();
        }

        if (date2 == null) {
            date2 = getDate();
        }

        long ldate1 = date1.getTime();
        long ldate2 = date2.getTime();
        long iDatenum = 0L;
        iDatenum = (ldate2 - ldate1) / 86400000L;
        return iDatenum;
    }

    public static String getWeek(String sdate, String fmt) {
        SimpleDateFormat df = new SimpleDateFormat(fmt);

        try {
            return getWeek(df.parse(sdate));
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String changeFormatStr(String value, String toFormatStr) {
        if (value != null && !"".equals(value)) {
            String patternStr = "[^0-9]?";
            Pattern pattern = Pattern.compile(patternStr, 2);
            Matcher matcher = pattern.matcher(value);
            String fromFormatStr = "yyyyMMddHHmm";
            value = matcher.replaceAll("");
            if (value.length() == 8) {
                fromFormatStr = "yyyyMMdd";
            }

            if (value.length() == 12) {
                fromFormatStr = "yyyyMMddHHmm";
            }

            if (value.length() == 14) {
                fromFormatStr = "yyyyMMddHHmmss";
            }

            SimpleDateFormat fromFormat = new SimpleDateFormat(fromFormatStr);
            SimpleDateFormat toFormat = new SimpleDateFormat(toFormatStr);
            Date formDate = null;

            try {
                formDate = fromFormat.parse(value);
            } catch (ParseException var10) {
                formDate = null;
            }

            return formDate == null ? value : toFormat.format(formDate);
        } else {
            return "";
        }
    }

    public static String getWeek(Date date) {
        Calendar cal1 = Calendar.getInstance();
        String chiweek = null;
        cal1.setTime(date);
        int bh = cal1.get(7);
        switch(bh) {
            case 1:
                chiweek = "星期日";
                break;
            case 2:
                chiweek = "星期一";
                break;
            case 3:
                chiweek = "星期二";
                break;
            case 4:
                chiweek = "星期三";
                break;
            case 5:
                chiweek = "星期四";
                break;
            case 6:
                chiweek = "星期五";
                break;
            case 7:
                chiweek = "星期六";
        }

        return chiweek;
    }

    public static int getWeekOfYear() {
        return getWeekOfYear(getDate());
    }

    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(3);
    }

    public static int getWeekOfYear(int offset) {
        return getWeekOfYear(getDate(), offset);
    }

    public static int getWeekOfYear(Date date, int offset) {
        return getWeekOfYear(getDate(date, offset));
    }

    public static int getWeekNumbersOfYear(int year) {
        GregorianCalendar cal = new GregorianCalendar(year, 12, 31);
        return cal.getMaximum(3);
    }

    public static int getWeekNumbersOfYear(String year) {
        return getWeekNumbersOfYear((new Integer(year)).intValue());
    }

    public static String getLastDayOfMonth(String strDate) {
        int year = (new Integer(strDate.substring(0, 4))).intValue();
        int month = (new Integer(strDate.substring(5, 7))).intValue();
        return dateFormat(getLastDate(year, month), "dd");
    }

    public static Date getLastDate(int year, int month) {
        int day = 0;
        String strmonth = null;
        boolean blrn = false;
        if (month >= 1 && month <= 12) {
            if ((year % 4 == 0 || year % 100 == 0) && year % 400 == 0) {
                blrn = true;
            } else {
                blrn = false;
            }

            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                day = 31;
            }

            if (month == 4 || month == 6 || month == 9 || month == 11) {
                day = 30;
            }

            if (month == 2) {
                if (blrn) {
                    day = 29;
                } else {
                    day = 28;
                }
            }

            if (month < 10) {
                strmonth = "0" + Integer.toString(month);
            } else {
                strmonth = Integer.toString(month);
            }

            return getDate(Integer.toString(year) + "-" + strmonth + "-" + Integer.toString(day), "yyyy-MM-dd");
        } else {
            return null;
        }
    }

    public static String replaceDateStr(String dateString) {
        return dateString;
    }

    public static long getBetweenSecond(Date beginDate, Date endDate) {
        long longBeginDate = beginDate.getTime();
        long longEndDate = endDate.getTime();
        return longEndDate / 1000L - longBeginDate / 1000L;
    }

    public static Date getDateSecond(Date date, long seconds) {
        return new Date(date.getTime() + seconds * 1000L);
    }

    public static java.sql.Date dateToSqlDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    public static Date sqlDateToDate(java.sql.Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    public static Timestamp dateToSqlTimestamp(Date date) {
        return date == null ? new Timestamp((new Date()).getTime()) : new Timestamp(date.getTime());
    }

    public static Date sqlTimestampToDate(Timestamp date) {
        return date == null ? null : new Date(date.getTime());
    }

    public static void main(String[] args) {
    }
}
