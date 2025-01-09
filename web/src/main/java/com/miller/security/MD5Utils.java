package com.miller.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * MD5工具类
 *
 * @author Miller Shan
 * @since 2024/12/26 20:58:37
 */
public class MD5Utils {
    /**
     * MD5加密
     */
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String s = null;
        try {
            s = DigestUtils.md5DigestAsHex(key.getBytes(Charset.defaultCharset().name()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
