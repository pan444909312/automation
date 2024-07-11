package com.miller.common.util;

/**
 * @description:
 * @author: liuzf
 * @date: 2021/01/07 3:08 下午
 */

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Sha256Util {

    public Sha256Util() {
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();

        for (int n = 0; b != null && n < b.length; ++n) {
            String stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs.append('0');
            }

            hs.append(stmp);
        }

        return hs.toString().toLowerCase();
    }

    public static String sha256Hmac(String message, String secret) {
        String hash = "";

        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] bytes = sha256HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception var6) {
            System.out.println("Error HmacSHA256 ===========" + var6.getMessage());
        }

        return hash;
    }
}
