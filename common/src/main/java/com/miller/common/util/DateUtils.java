package com.miller.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/09/28 09:51:54
 */
public class DateUtils {

    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 生成13位时间戳
     *
     * @return 当前时间戳
     * @see System#currentTimeMillis()
     */
    public static Long generateTimestamp() {
        return System.currentTimeMillis();
    }

    public static String getCurrentDateTime() {
        // 获取当前日期和时间
        Date now = new Date();

        // 定义日期时间格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 格式化当前日期和时间
        return formatter.format(now);
    }

    /**
     * 获取某个时区下某个日期当天的开始时间戳
     *
     * @param dateStr
     * @param timeZoneId
     * @return
     */
    public static long get0h0m0sMillsByDateAndZone(String dateStr, String timeZoneId) throws Exception {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(timeZoneId)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        Date parse = sdf.parse(dateStr);
        return parse.getTime();
    }

    /**
     * 日期转换为时间戳(毫秒)
     *
     * @param dateStr
     * @param timeZone
     * @return
     */
    public static Long getTimestamp(String dateStr, String timeZone) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(timeZone)) {
            return 0L;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date parse = null;
        try {
            parse = sdf.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("date parse exception:{}" + e);
        }

        if (parse == null) {
            return 0L;
        }
        return parse.getTime();
    }

    /**
     *
     * 将字符串按格式转化为Date类型
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date strToDate(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)){
            return null;
        }
        if (StringUtils.isBlank(pattern)){
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        // 定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            // 进行转换
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
