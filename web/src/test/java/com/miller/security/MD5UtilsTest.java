package com.miller.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MD5UtilsTest {

    @DisplayName("生成 MD5 密码")
    @Test
    void testMD5ForPassword() {
        String s = MD5Utils.md5("admin@aliyun.com" + "12345678");
        System.out.println(s);
    }
}