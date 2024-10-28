package com.miller.util;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author panjuxiang
 * @since 2024/9/29 16:10
 */
public class TimestampUtils {

    /**
     * 将时间戳按系统时区转化成yyyy/MM/dd 形式
     * @param timestamp
     * @return
     */
    public static String timestampToDateStr(long timestamp){

        // 将时间戳转换为LocalDateTime（需要指定时区）
        // 这里使用系统默认时区，但你也可以根据需要指定其他时区，如ZoneId.of("Asia/Shanghai")
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // 将LocalDateTime格式化为字符串
        String formattedDate = localDateTime.format(formatter);

        return formattedDate;

    }
}
