package com.miller.common.util;

import java.security.MessageDigest;

/**
 * MD5 加密工具
 *
 * @author Miller Shan
 * @version 1.0.0
 * @since 2023/12/7 21:01:03
 */
public class MD5Util {

    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception var8) {
            //System.out.println(var8.toString());
            var8.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; ++i) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; ++i) {
            int val = md5Bytes[i] & 255;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();

        for (int i = 0; i < a.length; ++i) {
            a[i] = (char) (a[i] ^ 116);
        }

        String s = new String(a);
        return s;
    }


    public static void main(String[] args) {

        System.out.println(string2MD5("mengfanxu@hungrypandagroup.com"+"123456"));

        String s = "011309428363457587";
        s = convertMD5(s);
        System.out.println(s);
        s = convertMD5(s);
        System.out.println(s);

        String s2 = "011309428363457587";
        s2 = string2MD5(s2);
        System.out.println(s2);
        s2 = string2MD5(s2);
        System.out.println(s2);


    }

}
