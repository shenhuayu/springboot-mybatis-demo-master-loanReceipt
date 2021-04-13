package com.winter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 @author shenhy
 @create 2021-04-09 10:37 
 ***/
public class DateUtils {

    public static int daysBetween(Date stdate, Date endate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(stdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date stdate = sdf.parse("20210101");
        Date endate = sdf.parse("20211101");
        System.out.println(daysBetween(stdate,endate));
        Date today = new Date();
        String todayStr = sdf.format(today);
        today = sdf.parse(todayStr);
    }
}
