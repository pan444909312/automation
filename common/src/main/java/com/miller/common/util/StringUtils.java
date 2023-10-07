package com.miller.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理方法
 *
 * @author Miller Shan
 * @since 2023-09-28 09:26:20
 */
public class StringUtils {
    /**
     * 去除字符串中的空格、回车、换行符、制表符等
     */
    public static String replaceSpecialStr(String str) {
        return replaceSpecialStr(str, "\\s*|\t|\r|\n");
    }

    public static String replaceSpecialStr(String str, String regex) {
        String repl = "";
        if (str != null) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }

}
