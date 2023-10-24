package com.miller.service.framework.util;

import java.util.concurrent.TimeUnit;

/**
 * 生成手机号
 *
 * @author Miller Shan
 * @version 1.0.0
 */
public class PhoneNumberUtils {
    /**
     * 号段
     */
    private static final String prefixOfPhone = "187";

    /**
     * 生成手机号
     *
     * @return 11位的手机号
     */
    public static synchronized String generatePhoneNumber() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long l = System.currentTimeMillis();
        String phoneNumber = String.valueOf(l).substring(5, 13);
        return prefixOfPhone + phoneNumber;
    }
}
