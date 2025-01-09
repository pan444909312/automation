package com.miller.common.util;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author panjuxiang
 * @since 2024/9/29 16:10
 */
public class TimestampUtils {

    /**
     * 将时间戳按系统时区转化成yyyy/MM/dd 形式
     *
     * @param timestamp
     * @return
     */
    public static String timestampToDateStr(long timestamp) {

        // 将时间戳转换为LocalDateTime（需要指定时区）
        // 这里使用系统默认时区，但你也可以根据需要指定其他时区，如ZoneId.of("Asia/Shanghai")
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // 将LocalDateTime格式化为字符串
        String formattedDate = localDateTime.format(formatter);

        return formattedDate;
    }

    /**
     * 根据传入的时间戳，获取该时间戳昨天的0:00的时间戳
     *
     * @param timestamp
     * @return
     */
    public static long timestampToYesterdayMidnight(long timestamp) {

        // 将时间戳转换为 LocalDateTime（使用系统默认时区）
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        // 获取前一天的日期，并设置为00:00:00
        LocalDateTime previousDayMidnight = dateTime.minusDays(1).truncatedTo(java.time.temporal.ChronoUnit.DAYS);

        // 将 LocalDateTime 转换回时间戳（毫秒）
        long previousDayMidnightTimestamp = previousDayMidnight.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 输出结果
        System.out.println("Given timestamp: " + timestamp);
        System.out.println("Previous day's midnight timestamp: " + previousDayMidnightTimestamp);
        return previousDayMidnightTimestamp;
    }

    /**
     * 根据当前日期，加上指定月数的日期，并返回那一天0点的时间戳
     * @param monthsToAdd 指定月数，正数则算未来时间，负数则算过去时间
     * @return
     */
    public static long getMidnightTimestampPlusMonths(int monthsToAdd) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 计算当前加monthsToAdd个月的日期
        LocalDate futureDate = currentDate.plusMonths(monthsToAdd);

        ZonedDateTime zonedDateTime = futureDate.atStartOfDay(ZoneId.of("Asia/Shanghai"));

        return zonedDateTime.toInstant().toEpochMilli();
    }

    public static long getTimestampToNowBetweenDays(long timestamp) {
        // 将给定的时间戳转换为Instant对象
        Instant givenInstant = Instant.ofEpochMilli(timestamp);

        // 获取当前的Instant对象
        Instant now = Instant.now();

        // 为了得到整数的天数差异（不考虑时间部分），你可以这样做：
        return Math.abs(ChronoUnit.DAYS.between(
                givenInstant.atZone(ZoneId.systemDefault()).toLocalDate(), // 将givenInstant转换为LocalDate（使用系统默认时区）
                now.atZone(ZoneId.systemDefault()).toLocalDate() // 将当前时间转换为LocalDate（使用系统默认时区）
        ));
    }

//    @Test
    private void test02(){
        // 假设给定的时间戳（以毫秒为单位）
        long givenTimestamp = 1733500800000L; // 这是一个示例时间戳，你应该替换为你自己的时间戳

        // 将给定的时间戳转换为Instant对象
        Instant givenInstant = Instant.ofEpochMilli(givenTimestamp);

        // 获取当前的Instant对象
        Instant now = Instant.now();

        // 为了得到整数的天数差异（不考虑时间部分），你可以这样做：
        long fullDaysDifference = Math.abs(ChronoUnit.DAYS.between(
                givenInstant.atZone(ZoneId.systemDefault()).toLocalDate(), // 将givenInstant转换为LocalDate（使用系统默认时区）
                now.atZone(ZoneId.systemDefault()).toLocalDate() // 将当前时间转换为LocalDate（使用系统默认时区）
        ));

        // 打印结果
        System.out.println("使用ChronoUnit.DAYS.between()得到的完整天数差异: " + fullDaysDifference);
    }

//    @Test
    private void dataTest() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 计算3个月后的日期
        LocalDate futureDate = currentDate.plusMonths(3);
        LocalDate lastDate = currentDate.plusMonths(-3);

        // 将LocalDate转换为ZonedDateTime（时区可以根据需要调整）
        ZonedDateTime zonedDateTime = futureDate.atStartOfDay(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime zonedDateTime2 = lastDate.atStartOfDay(ZoneId.of("Asia/Shanghai"));

        // 获取时间戳（毫秒数）
        long timestampFutureDate = zonedDateTime.toInstant().toEpochMilli();
        long timestampLastDate = zonedDateTime2.toInstant().toEpochMilli();

        // 打印结果
        System.out.println("当前日期: " + currentDate);
        System.out.println("3个月后的日期: " + futureDate);
        System.out.println("3个月后的日期的时间戳: " + timestampFutureDate);
        System.out.println("3个月前的日期: " + lastDate);
        System.out.println("3个月后的日期的时间戳: " + timestampLastDate);

        System.out.println(getMidnightTimestampPlusMonths(0));
    }

}
