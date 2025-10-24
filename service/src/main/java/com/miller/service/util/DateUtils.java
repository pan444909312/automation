package com.miller.service.util;

import org.apache.commons.lang3.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期工具类
 */
public class DateUtils {

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
