package com.data.bigdata.common.time;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  时间转换类，当前时间转换成yyyy-mm-dd形式
 */
public class TimeTransformUtils {

    private static Date nowTime;
    public static String Date2yyyy_MM_dd(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowTime = new Date(System.currentTimeMillis());
        String time = dateFormat.format(nowTime);
        return time;
    }


    public static String Long2yyyyMMdd(long timeLong){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        nowTime = new Date(timeLong*1000);
        String time = dateFormat.format(nowTime);
        return time;
    }

    public static String Date2yyyyMMdd(String timeLong){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        nowTime = new Date(Long.valueOf(timeLong)*1000);
        String time = dateFormat.format(nowTime);
        return time;
    }

}
