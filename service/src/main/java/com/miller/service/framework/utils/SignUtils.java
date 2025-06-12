package com.miller.service.framework.utils;

import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 签名工具类
 * 实现与前端sign.js相同的验签功能
 */
public class SignUtils {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();
    private static final Pattern API_URL_PATTERN = Pattern.compile("://(api|app)(-|\\..+)\\.hungrypanda\\.");

    /**
     * 获取随机整数
     */
    private static int getRndInteger(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * 获取随机字符串
     */
    private static String getRandomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = getRndInteger(0, CHARS.length() - 1);
            result.append(CHARS.charAt(num));
        }
        return result.toString();
    }

    /**
     * 提取URL中的pathname
     */
    private static String extractPathname(String url) {
        int protocolIndex = url.indexOf("://");
        int startIndex = Math.max(0, url.indexOf("/", 
            protocolIndex != -1 ? protocolIndex + 3 : protocolIndex));
        
        int endIndex = url.length();
        int queryIndex = url.lastIndexOf("?");
        int hashIndex = url.lastIndexOf("#");
        
        if (queryIndex != -1) endIndex = Math.min(endIndex, queryIndex);
        if (hashIndex != -1) endIndex = Math.min(endIndex, hashIndex);
        
        return url.substring(startIndex, endIndex);
    }

    /**
     * 判断是否是API请求
     */
    private static boolean getIsApiProxy(String url) {
        if (url.contains("://")) {
            // 使用Pattern.matcher()来匹配正则表达式，与JavaScript版本完全一致
            if (!API_URL_PATTERN.matcher(url).find()) {
                return false;
            }
        }
        if (url.contains("/statics/global_config")) {
            return false;
        }
        return true;
    }

    /**
     * 生成签名
     */
    private static Map<String, String> encode(String nt, String nu, String nm, String nh, String nb) {
        String nn = getRandomString(25);
        String no = extractPathname(nu);
        String nmu = nm.toUpperCase();

        // 第一轮加密
        String oneRandString = "1234567890qwjnb";
        String oneEncodedString = DigestUtils.md5Hex(oneRandString + nmu + nh + no);

        // 第二轮加密
        String twoRandString = "3jlaiow323kjdsj";
        String twoEncodedString = DigestUtils.md5Hex(
            twoRandString + oneEncodedString + nt + nn + nb
        );

        String nd = twoEncodedString.substring(2, 17);

        Map<String, String> result = new HashMap<>();
        result.put("nv", "2");
        result.put("nt", nt);
        result.put("nn", nn);
        result.put("nd", nd);
        return result;
    }

    /**
     * 请求增加签名
     */
    public static Map<String, Object> nse(Map<String, Object> options) {
        String url = (String) options.get("url");
        
        // 非接口请求直接返回
        if (!getIsApiProxy(url)) {
            return options;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> headers = (Map<String, Object>) options.getOrDefault("headers", new HashMap<>());
        String method = ((String) options.getOrDefault("method", "GET")).toUpperCase();
        Object data = options.getOrDefault("data", new HashMap<>());

        // 生成签名
        Map<String, String> signResult = encode(
            String.valueOf(System.currentTimeMillis()),
            url,
            method,
            JSONUtils.toJSONString(headers),
            JSONUtils.toJSONString(data)
        );

        // 构建请求数据
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("pm", method);
        requestData.put("ph", headers);
        requestData.put("pd", data);
        requestData.put("nv", signResult.get("nv"));
        requestData.put("nt", signResult.get("nt"));
        requestData.put("nn", signResult.get("nn"));
        requestData.put("nd", signResult.get("nd"));

        Map<String, Object> result = new HashMap<>();
        result.put("url", url);
        result.put("method", "POST");
        result.put("headers", new HashMap<>());
        result.put("data", requestData);

        return result;
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        // 构建测试请求参数
        Map<String, Object> headers = new HashMap<>();
        headers.put("apptypeid", "1");
        headers.put("authorization", "13ad986003c5ba9f5f28c5193c44ecdc");
        headers.put("brand", "");
        headers.put("countryCode", "US");
        headers.put("language", "en");
        headers.put("latitude", "30.203788170291023");
        headers.put("longitude", "120.2169341262152");
        headers.put("platform", "PC_WEB_USER");
        headers.put("uniqueToken", "bd240567-80fc-48b2-b6e3-92a295211fb5");
        headers.put("version", "8.8.0");

        Map<String, Object> options = new HashMap<>();
        options.put("url", "https://api-cn-f2e-test.hungrypanda.cn/api/user/location");
        options.put("method", "GET");
        options.put("headers", headers);
        options.put("data", new HashMap<>());

        // 生成签名
        Map<String, Object> signedRequest = nse(options);
        
        // 使用HttpUtils发送请求
        try {
            String response = HttpUtils.sendPostRequestReturnBody(
                (String) signedRequest.get("url"),
                null,
                (Map<String, Object>) signedRequest.get("headers"),
                signedRequest.get("data"),
                null
            );
            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 