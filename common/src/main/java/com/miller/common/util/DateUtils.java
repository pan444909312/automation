package com.miller.common.util;

/**
 * 日期工具类
 *
 * @author Miller Shan
 * @since 2023-09-28 09:51:54
 */
public class DateUtils {
    /**
     * 生成13位时间戳
     *
     * @return 当前时间戳
     * @see System#currentTimeMillis()
     */
    public static Long generateTimestamp() {
        Long timestamp = System.currentTimeMillis();
        return timestamp;
    }
}
