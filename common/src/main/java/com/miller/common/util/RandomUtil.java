package com.miller.common.util;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/09/29 19:51:54
 */
public class RandomUtil {
    private static char[] codeSequence = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 获取随机码
     *
     * @param numCount  其中数字个数
     * @param charCount 其中字母个数
     */
    public static String getCode(int numCount, int charCount) {
        // 生成的密码
        var codeStr = new StringBuffer("");
        int codeCount = numCount + charCount;
        int len = codeSequence.length;
        for (var i = 0; i < codeCount; i++) {
            int intR = 0;
            if (charCount == 0) {
                intR = (int) Math.floor(Math.random() * 10);
            } else if (numCount == 0) {
                intR = (int) Math.floor(Math.random() * (len - 10)) + 10;
            } else {
                intR = (int) Math.floor(Math.random() * len);
            }
            char c = codeSequence[intR]; // 找到指定字符
            // 判断字符类型并计数：数字，字母
            if ('0' <= c && c <= '9') {
                numCount--;
            } else {
                charCount--;
            }
            codeStr.append(c);
        }
        return codeStr.toString();
    }

    /**
     * 获取数字随机码
     *
     * @param numCount 随机码字符个数
     */
    public static String getNumberCode(int numCount) {
        return getCode(numCount, 0);
    }

    /**
     * 获取字母随机码
     *
     * @param charCount 随机码字符个数
     */
    public static String getCharCode(int charCount) {
        return getCode(0, charCount);
    }

    public static void main(String[] args) {
        for (var i = 0; i < 10; i++) {
            System.out.println(RandomUtil.getCode(10, 6));
        }
    }
}
