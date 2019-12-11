package com.testcase.frame.common.util;

import com.testcase.frame.common.bean.TimeSet;
import com.testcase.frame.common.constant.Constants;
import com.testcase.frame.common.exception.base.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {


    private static final Logger logger = LogManager.getLogger();

    /*  1 则代表的是对年份操作， Calendar.YEAR
        2 是对月份操作， Calendar.MONTH
        3 是对星期操作，
        5 是对日期操作， Calendar.DATE
        11 是对小时操作， Calendar.HOUR_OF_DAY
        12 是对分钟操作，
        13 是对秒操作，
        14 是对毫秒操作。*/

  /*--------------------------------------  2019-05-10  -------------------------------------------------------*/


  public static String GetNowDate(String dateFormat){

      Date dt = new Date();

      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

      return sdf.format(dt);
  }

    /**
     * 字符串转日期
     * @param str
     * gs 格式
     * @return
     */
    public static Date StrToDate(String str,String gs) {

        SimpleDateFormat format = new SimpleDateFormat(gs);

        Date date = null;

        try {

            date = format.parse(str);
        } catch (ParseException e) {

            logger.error("StrToDate异常 ..." + str + gs  + e + e.getMessage());
            throw new BusinessException("参数不正确");
        }
        return date;
    }

    public static Object getPreTimeByCalendar(Date d, int scal ,int calendar,boolean isDate){

        Calendar calen = Calendar.getInstance();

        calen.setTime(d);

        calen.set(calendar, calen.get(calendar) - scal);

        return isDate ? calen.getTime() : Constants.DEFAULT_DATE_FORMAT.format(calen.getTime());
    }

    public static Object getPreTimeByCalendar(String date, int scal ,int calendar,boolean isDate){

        Calendar calen = Calendar.getInstance();

        calen.setTime(StrToDate(date,Constants.JAVA_TIME_FORMAT));

        calen.set(calendar, calen.get(calendar) - scal);

        return isDate ? calen.getTime() :Constants.DEFAULT_DATE_FORMAT.format(calen.getTime());
    }

    public static Object getNextTimeByCalendar(Date d, int scal ,int calendar,boolean isDate){

        Calendar calen = Calendar.getInstance();

        calen.setTime(d);

        calen.set(calendar, calen.get(calendar) + scal);

        return isDate ? calen.getTime() :Constants.DEFAULT_DATE_FORMAT.format(calen.getTime());
    }

    public static Object getNextTimeByCalendar(String date, int scal ,int calendar,boolean isDate){

        Calendar calen = Calendar.getInstance();

        calen.setTime(StrToDate(date,Constants.JAVA_TIME_FORMAT));

        calen.set(calendar, calen.get(calendar) + scal);

        return isDate ? calen.getTime() :Constants.DEFAULT_DATE_FORMAT.format(calen.getTime());
    }

    /* ---------------------------------------------------------------------------------------*/
    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE) + day);
        return now.getTime();
    }



    public static Date getMonthBefore(Date d, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
        now.add(Calendar.MONTH, -1);
        return now.getTime();
    }

    public static  Date  getHourBefore(Date d,int h) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - h);
        return calendar.getTime();
    }
    /**
     * 获取上一年时间范围
     */
    public static String getPreYear(Date d, int y){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.YEAR, y);
        return Constants.DEFAULT_DATE_FORMAT.format(now.getTime());
    }

    /**
     * 下个月
     * @return
     */
    public static Date getPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        return cal.getTime();
    }

    /**
     * 去年的下个月
     * @return
     */
    public static Date getLastPreMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.YEAR, -1);
        cal.add(cal.MONTH, 1);
        return cal.getTime();
    }
    public static Date getLastYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.YEAR, -1);
        return cal.getTime();
    }

    public static boolean dateBetween(Date start, Date end ,Date bind){

        long bindTime = bind.getTime();

        long startTime = start.getTime();

        long endTime = end.getTime();

        return bindTime >= startTime && bindTime <= endTime;
    }


    public static boolean dateBetween(String nowDate, String startDate, String endDate) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat(Constants.JAVA_TIME_FORMAT);

        Date now = format.parse(nowDate);
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);

        long nowTime = now.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();

        return nowTime >= startTime && nowTime <= endTime;
    }

    /**
     * 获取时间内的所有日期
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            //tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }
    public static List<String> getMonths(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            //tempEnd.add(Calendar.MONTH, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd) || tempStart.compareTo(tempEnd)==0) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.MONTH, +1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    public static int differentDaysByMillisecond(String startTime,String endTime) {

        Date date1 = DateUtils.StrToDate(startTime,Constants.JAVA_TIME_FORMAT);
        Date date2 = DateUtils.StrToDate(endTime,Constants.JAVA_TIME_FORMAT);

        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));

        return days;
    }

    public static String getSpecifiedDayBefore(String specifiedDay){
        Calendar c = Calendar.getInstance();

        Date date = null;

        try {

            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE,day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore+" 00:00:00";
    }




    public static List<String>  getAllDatesByDateRange(String startTime, String  endTime){

        List<String> lDate = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");

        Date dBegin = null;
        Date dEnd = null;

        try {
            dBegin = sdf.parse(startTime);
            dEnd = sdf.parse(endTime);

            lDate.add(sd.format(dBegin));
            Calendar calBegin = Calendar.getInstance();
            calBegin.setTime(dBegin);

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(dEnd);

            while (dEnd.after(calBegin.getTime()))
            {
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                lDate.add(sd.format(calBegin.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return lDate;
    }

    public static String getCurrentTime(String dateFormat){

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return sdf.format(dt);
    }

    public static String  characterToDateStr(String character,String format){

        SimpleDateFormat dft = new SimpleDateFormat(format);

        Calendar   dar = Calendar.getInstance();

        try {

            dar.setTime(dft.parse(character));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dft.format(dar.getTime());
    }

    /*--------*/

    public static List<TimeSet> getTimeSetForDate(String startTime, String endTime, String gs, int calendar){

        SimpleDateFormat format = new SimpleDateFormat(gs);

        Date d1 =  DateUtils.StrToDate(startTime ,gs);// 定义起始日期

        Date d2 =  DateUtils.StrToDate(endTime ,gs);// 定义结束日期

        Calendar dd = Calendar.getInstance();// 定义日期实例

        dd.setTime(d1);// 设置日期起始时间

        Calendar cale = Calendar.getInstance();

        Calendar c = Calendar.getInstance();

        c.setTime(d2);

        return setTimeSet(dd,cale,c,d1,d2,calendar,format);
    }

    public static List<TimeSet> setTimeSet(Calendar dd,Calendar cale,Calendar c,Date d1, Date d2,int calendar,SimpleDateFormat sdf){

        List<TimeSet> timeSets = new ArrayList<>();

        TimeSet  time = null;

        while (dd.getTime().before(d2)) { // 判断是否到结束日期

            time = new TimeSet();

            cale.setTime(dd.getTime());

            if(dd.getTime().equals(d1)){

                cale.set(calendar, dd.getActualMaximum(calendar));

                time.setStartTime(sdf.format(d1));

                time.setEndTime(sdf.format(cale.getTime()));

            }else {

                time.setStartTime(sdf.format(cale.getTime()));

                cale.set(calendar, dd.getActualMaximum(calendar));

                time.setEndTime(sdf.format(cale.getTime()));
            }
            timeSets.add(time);
            dd.add(calendar, cale.get(calendar) + 1); // 进行当前日期月份加1
        }

        return timeSets;
    }

    /**
     *  是否是日期字段
     * @param field
     * @return
     */
    public static boolean isDateFied(Field field) {
        return (Date.class == field.getType());
    }
}
