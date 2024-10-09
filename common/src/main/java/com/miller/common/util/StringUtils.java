package com.miller.common.util;

import java.util.regex.Pattern;

/**
 * 字符串处理方法
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/09/28 09:26:20
 */
public class StringUtils {
    /**
     * 去除字符串中的空格、回车、换行符、制表符等
     */
    public static String replaceSpecialStr(String str) {
        return replaceSpecialStr(str, "\\s*|\t|\r|\n");
    }

    public static String replaceSpecialStr(String str, String regex) {
        var repl = "";
        if (str != null) {
            var p = Pattern.compile(regex);
            var m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
