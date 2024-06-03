package com.miller.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/09/28 09:51:54
 */
public class DateUtils {
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
}
