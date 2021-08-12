package com.futures.common.utils;

import com.gongyu.snowcloud.framework.util.DateUtils;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zhaoQiXing
 * @version 1.0
 * @date 2020/6/2 10:57
 */
public class DateUtil {

    /**
     * 返回当前时间戳 不含毫秒级
     * @return
     */
    public static long getNowDate(){
        long time = DateUtils.currentDate().getTime();
        String nowDate = String.valueOf(time);
        return Long.parseLong(nowDate.substring(0,nowDate.length()-3));
    }

    /**
     * 返回给定时间戳 不含毫秒级
     * @return
     */
    public static long getDate(Date date){
        long time = date.getTime();
        String nowDate = String.valueOf(time);
        return Long.parseLong(nowDate.substring(0,nowDate.length()-3));
    }

    /**
     * 根据指定天数计算秒
     * @return
     */
    public static long getLongTime(Integer days){
        Assert.notNull(days,"天数为空");
        return days * 60 * 60 * 24;
    }

    /**
     * 根据时间戳返回对应时间 时间戳长度 10
     * @param date
     * @return
     */
    public static Date getDate(Integer date){
        Assert.notNull(date,"时间不能为空");
        long d = Long.valueOf(date) * 1000;
        return new Date(d);
    }

    /**
     * 根据时间戳返回对应时间 时间戳长度 10
     * @param date
     * @return
     */
    public static Date getDate(Long date){
        Assert.notNull(date,"时间不能为空");
        long d = date * 1000;
        return new Date(d);
    }

    /**
     * 日期增加numDay日
     * @param date
     * @param numDay
     * @return
     */
    public static Date addDate(Date date,Integer numDay){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天
        calendar.add(calendar.DATE,numDay);
        return calendar.getTime();
    }

    /**
     * 当前时间是否在给定时间之前
     * @param exDate
     * @return
     */
    public static boolean before(String exDate){
        Date parse = DateUtils.parse(exDate, DateUtils.DEFAULT_DATE_TIME_FORMAT);
        return new Date().before(parse);
    }

    /**
     *判读时间差距，两个时间相差多少天，时，分，秒
     */
    public static Long getDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.DEFAULT_DATE_TIME_FORMAT);
        Long days = null;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));//现在系统当前时间
            Date pastTime = dateFormat.parse(date);//过去时间
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

}
