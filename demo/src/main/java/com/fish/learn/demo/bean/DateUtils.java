package com.fish.learn.demo.bean;

import org.apache.commons.lang3.time.DateFormatUtils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final long ONE_DAY=24*60*60*1000L;
    public static final long ONE_HOUR=1*60*60*1000L;
    public static final long ONE_MIN=1*60*1000L;
    public static final long ONE_SEC=1*1000L;
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        if (date == null) {
            return null;
        }
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 以默认格式'yyyy-MM-dd HH:mm:ss'进行日期解析
     */
    public static Date parse(String dateString) {
        return parse(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parse(String dateString, String format) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();
        try {
            DATE_FORMAT.applyPattern(format);
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    public static String getTimeBetweenTwoDays(long time){
        int day=(int)(Double.valueOf(time)/ ONE_DAY);
        int hour=(int)(Double.valueOf(time)%ONE_DAY/ONE_HOUR);
        int min=(int)(Double.valueOf(time)%ONE_HOUR/ONE_MIN);
        int sec=(int)(Double.valueOf(time)%ONE_MIN/ONE_SEC);
        StringBuffer sb=new StringBuffer();
        if(day!=0){
            sb.append(day+"天 ");
        }
        if(hour!=0){
            sb.append(hour+"小时 ");
        }
        if(min!=0){
            sb.append(min+"分 ");
        }
        if(sec!=0){
            sb.append(sec+"秒 ");
        }else{
            sb.append("0秒");
        }
        return sb.toString();
    }

    public static String getFirstDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return first;
    }

    public static Calendar isMonthHasDay(Calendar calendar, int day) {
        int maxDay = calendar.getActualMaximum(Calendar.DATE);
        if (maxDay < day) {
            calendar.add(Calendar.MONTH, 1);
            return isMonthHasDay(calendar, day);
        } else {
            return calendar;
        }
    }


    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return formatDate(new Date(Long.valueOf(seconds + "000")), format);
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param ms 精确到毫秒的字符串
     * @param format
     * @return
     */
    public static String msTimeStamp2Date(String ms, String format) {
        if (ms == null || ms.isEmpty() || ms.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return formatDate(new Date(Long.valueOf(ms)), format);
    }

    /**
     * 日期格式字符串转换成时间戳(秒)
     *
     * @param dateString 字符串日期
     * @param format     如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String dateString, String format) {
        return String.valueOf(parse(dateString, format).getTime() / 1000);
    }


    /**
     * 获取本周的开始时间
     * @return
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取本周的结束时间
     * @return
     */
    public static Date getEndDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * 获取本月的开始时间
     * @return
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     * @return
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取某个日期的结束时间
     * @param d
     * @return
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取今年是哪一年
     * @return
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * 获取本月是哪一月
     * @return
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     * 获取某个日期的开始时间
     * @param d
     * @return
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d){
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date getAddYear(Date old, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(old);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    public static Date getAddMonth(Date old, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(old);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static Date getAddDate(Date old, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(old);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date getAddHours(Date old, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(old);
        c.add(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    public static Date getAddMinutes(Date old, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(old);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    public static Date getAddSeconds(Date old, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(old);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
//      System.out.println(formatDate(parseDate("2010/3/6")));
//      System.out.println(getDate("yyyy年MM月dd日 E"));
//      long time = System.currentTimeMillis()-parseDate("2012-11-19").getTime();
//      System.out.println(time/(24*60*60*1000));

        Date date = getEndDayOfMonth();
        System.out.println("date："+formatDateTime(date));

        Date date2 = getBeginDayOfMonth();
        System.out.println("date2："+formatDateTime(date2));


        Date date3 = getEndDayOfWeek();
        System.out.println("date3："+formatDateTime(date3));

        Date date4 = getBeginDayOfWeek();
        System.out.println("date4："+formatDateTime(date4));

        Date date5 = getDayStartTime(new Date());
        System.out.println("date5："+formatDateTime(date5));

        Date date6 = getDayEndTime(new Date());
        System.out.println("date6："+formatDateTime(date6));


        /*String str = "2018-01-31T02:38:40.000Z";
        Instant instant = Instant.parse(str);
        Date date = Date.from(instant);
        System.out.println(org.apache.commons.lang.time.DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));


        System.out.println(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));*/
    }
}
