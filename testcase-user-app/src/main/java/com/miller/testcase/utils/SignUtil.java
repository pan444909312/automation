package com.miller.testcase.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/**
 * @Author: panjuxiang
 * @Since: 2025/7/14
 */
public class SignUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);

    private SignUtil() {
    }

    public static String getSign(String signKey, JSONObject reqJson) {
        try {
            Map<String, String> params = getParam(reqJson);
            String sortedStr = getSortedParamStr(params);
            String paraStr = signKey + sortedStr;
            return createSign(paraStr);
        } catch (UnsupportedEncodingException var5) {
            UnsupportedEncodingException e = var5;
            LOGGER.warn("getSign UnsupportedEncodingException ", e);
            return "";
        }
    }

    private static Map<String, String> getParam(JSONObject reqJson) {
        Map<String, String> params = new HashMap();
        if (reqJson == null) {
            return params;
        } else {
            reqJson.entrySet().stream().filter((entry) -> {
                return !Objects.equals(entry.getKey(), "file");
            }).filter((entry) -> {
                return entry.getValue() != null && StringUtils.isNotEmpty(String.valueOf(entry.getValue()));
            }).forEach((entry) -> {
                String var10000 = (String)params.put(entry.getKey(), String.valueOf(entry.getValue()));
            });
            return params;
        }
    }

    private static String getSortedParamStr(Map<String, String> params) throws UnsupportedEncodingException {
        Set<String> sortedParams = new TreeSet(params.keySet());
        StringBuilder strB = new StringBuilder();
        Iterator var3 = sortedParams.iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            if (!"sign".equalsIgnoreCase(key)) {
                String value = (String)params.get(key);
                if (StringUtils.isNotEmpty(value)) {
                    strB.append(key).append(value);
                }
            }
        }

        return strB.toString();
    }

    private static String createSign(String str) {
        if (str != null && !str.isEmpty()) {
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            try {
                MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
                mdTemp.update(str.getBytes(StandardCharsets.UTF_8));
                byte[] md = mdTemp.digest();
                int j = md.length;
                char[] buf = new char[j * 2];
                int k = 0;

                for(int i = 0; i < j; ++i) {
                    byte byte0 = md[i];
                    buf[k++] = hexDigits[byte0 >>> 4 & 15];
                    buf[k++] = hexDigits[byte0 & 15];
                }

                return new String(buf);
            } catch (Exception var9) {
                Exception e = var9;
                LOGGER.warn("create sign was failed", e);
                return null;
            }
        } else {
            return null;
        }
    }
}
