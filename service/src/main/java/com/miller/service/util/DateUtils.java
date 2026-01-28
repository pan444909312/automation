package com.miller.service.util;

import org.apache.commons.lang3.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    static String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentDate(String format) {
        if (ObjectUtils.isEmpty(format)) {
            format = DATA_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }


    public static Long toMillisecondTimestamp(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr);
        //  转换为毫秒级时间戳
        return localDate.atStartOfDay(ZoneId.of("Asia/Shanghai"))
                .toInstant()
                .toEpochMilli();
    }

    /**
     * 校验日期格式是否与预期一致，默认："yyyy-MM-dd"
     */
    public static void checkDateFormat(String date){
        DateUtils.checkDateFormat(date,"yyyy-MM-dd");
    }

    /**
     * 校验日期格式是否与预期一致
     */
    public static void checkDateFormat(String date , String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // 严格模式（不允许自动转换，如 2023-02-30 会报错）
        try {
            if (ObjectUtils.isEmpty(date)) {
                throw new RuntimeException("请求参数异常，date 不能为空");
            }
            sdf.parse(date); // 尝试解析
        } catch (ParseException e) {
            throw new RuntimeException("请求参数异常，date 格式异常，必须：".concat(format));
        }
    }
}
