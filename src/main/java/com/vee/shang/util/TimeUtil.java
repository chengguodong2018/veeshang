package com.vee.shang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cgd
 * @date 2017/12/16.
 */
public class TimeUtil {
    public static String getTimeStr(Date time) {
        long l = new Date().getTime() - time.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);

        String timeStr;
        if (day > 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            timeStr = sdf.format(time);
        } else if (day == 1) {
            timeStr = "昨天";
        } else if (hour > 0) {
            timeStr = hour + "小时前";
        } else if (min > 0) {
            timeStr = min + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        return timeStr;
    }

    public static Long getDayDiff(Date time) {
        long l = new Date().getTime() - time.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        return day;
    }
}
