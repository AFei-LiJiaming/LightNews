package com.AFei.base.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class TimeUtils
{
    /**
     * 时间戳换时间
     * @param timeMillis
     * @return
     */
    public static String stampToDate(long timeMillis)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }


    /**
     * 获取时间
     * @return
     */
    public static String getTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }


    public static String getDistanceTime(long diff)
    {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0)
        {
            return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
        }
        if (hour != 0)
        {
            return hour + "小时" + min + "分钟" + sec + "秒";
        }
        if (min != 0)
        {
            return min + "分钟" + sec + "秒";
        }
        if (sec != 0)
        {
            return sec + "秒";
        }
        return "0秒";
    }


    /**
     * 获取当前日期后的一星期的日期
     * @return
     */
    public static List<String> getSevendate()
    {
        String mYear; // 当前年
        String mMonth; // 月
        String mDay;
        List<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));


        for (int i = 0; i < 7; i++)
        {
            // 获取当前年份
            mYear = String.valueOf(c.get(Calendar.YEAR));
            // 获取当前月份
            mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
            // 获取当前日份的日期号码
            mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);
            if (Integer.parseInt(mDay) > maxDayFromDayOFMONTH(Integer.parseInt(mYear), (i + 1)))
            {
                mDay = String.valueOf(maxDayFromDayOFMONTH(Integer.parseInt(mYear), (i + 1)));
            }
            String date = mYear + "-" + mMonth + "-" + mDay;
            dates.add(date);
        }
        return dates;
    }


    public static int maxDayFromDayOFMONTH(int year, int month)
    {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        //注意,Calendar对象默认一月为0
        time.set(Calendar.MONTH, month - 1);
        //本月份的天数
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day;
    }
}
