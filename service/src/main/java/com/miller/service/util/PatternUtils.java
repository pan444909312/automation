package com.miller.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    public static String matcher(String str, String regex){

        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        String result = "";
        if (matcher.find()) {
            result= matcher.group(1);
        }
        return result;
    }

}
