package com.miller.pandafresh.testcase.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class WebSignUtils {
    private static final String ONE_RAND_STRING = "1234567890qwjnb";
    private static final String TWO_RAND_STRING = "3jlaiow323kjdsj";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 获取随机整数
     */
    private static int getRndInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * 获取随机字符串
     */
    private static String getRandomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = getRndInteger(0, CHARACTERS.length() - 1);
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }

    /**
     * 提取URL路径
     */
    private static String extractPathname(String url) {
        int protocolIndex = url.indexOf("://");
        int startIndex = Math.max(0, url.indexOf("/", protocolIndex != -1 ? protocolIndex + 3 : protocolIndex));
        int endIndex = Math.min(url.length(), Math.min(
                url.lastIndexOf("?") != -1 ? url.lastIndexOf("?") : url.length(),
                url.lastIndexOf("#") != -1 ? url.lastIndexOf("#") : url.length()
        ));
        return url.substring(startIndex, endIndex);
    }

    /**
     * 生成签名
     */
    public static SignResult encode(String nt, String nu, String nm, String nh, String nb) {
        // 生成25位随机字符串
        String nn = getRandomString(25);
        
        // 提取URL路径
        String no = extractPathname(nu);
        String nmu = nm.toUpperCase();

        // 第一轮加密
        String oneEncodedString = DigestUtils.md5Hex(ONE_RAND_STRING + nmu + nh + no);
        
        // 第二轮加密
        String twoEncodedString = DigestUtils.md5Hex(
                TWO_RAND_STRING + oneEncodedString + nt + nn + nb
        );

        // 提取签名
        String nd = twoEncodedString.substring(2, 17);

        // 打印调试信息
        System.out.println("Debug information:");
        System.out.println("URL path: " + no);
        System.out.println("Method: " + nmu);
        System.out.println("Headers: " + nh);
        System.out.println("Body: " + nb);
        System.out.println("First round input: " + ONE_RAND_STRING + nmu + nh + no);
        System.out.println("First round result: " + oneEncodedString);
        System.out.println("Second round input: " + TWO_RAND_STRING + oneEncodedString + nt + nn + nb);
        System.out.println("Second round result: " + twoEncodedString);

        return new SignResult("2", nt, nn, nd);
    }

    /**
     * 签名结果类
     */
    public static class SignResult {
        private final String nv;
        private final String nt;
        private final String nn;
        private final String nd;

        public SignResult(String nv, String nt, String nn, String nd) {
            this.nv = nv;
            this.nt = nt;
            this.nn = nn;
            this.nd = nd;
        }

        public String getNv() {
            return nv;
        }

        public String getNt() {
            return nt;
        }

        public String getNn() {
            return nn;
        }

        public String getNd() {
            return nd;
        }
    }
} 