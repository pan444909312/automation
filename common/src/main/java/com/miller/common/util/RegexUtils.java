package com.miller.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 正则工具
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/09/28 09:26:20
 */
public class RegexUtils {
    /**
     * 正则表达式匹配两个指定字符串中间的内容
     *
     * @param text            查找的文本
     * @param regexExpression 匹配表达式
     * @return 找到的列表
     */
    public static List<String> getSubStringList(String text, String regexExpression) {
        var list = new ArrayList<String>();
        var pattern = Pattern.compile(regexExpression);// 匹配的模式
        var m = pattern.matcher(text);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param text            查找的文本
     * @param regexExpression 匹配表达式
     * @return 找到的第一个匹配
     */
    public static String getFirstSubString(String text, String regexExpression) {
        var pattern = Pattern.compile(regexExpression);// 匹配的模式
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
