package com.miller.userapp.util;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("测试请求发送工具")
class RequestUtilsTest {

    @Test
    @DisplayName("模拟多用户使用HTTP工具，互相隔离")
    void mockMultiUser() {
        // 模拟用户1
        var header1 = new HashMap<String, Object>();
        header1.put("Content-Type", "application/json");
        header1.put("authorization", "user1token");
        RequestUtils.setHeaders(header1);
        System.out.println("user1:" + RequestUtils.getHeaders());
        Map<String, Object> user1 = RequestUtils.getHeaders();

        // 模拟用户2
        var header2 = new HashMap<String, Object>();
        header2.put("Content-Type", "application/json");
        header2.put("authorization", "user2token");
        RequestUtils.setHeaders(header2);
        System.out.println("user2:" + RequestUtils.getHeaders());
        Map<String, Object> user2 = RequestUtils.getHeaders();

        // 模拟用户3
        var header3 = new HashMap<String, Object>();
        header3.put("Content-Type", "x-xxx-form-urlencoded");
        header3.put("authorization", "user3token");
        RequestUtils.setHeaders(header3);
        System.out.println("user3:" + RequestUtils.getHeaders());
        Map<String, Object> user3 = RequestUtils.getHeaders();

        // 断言两个用户端的 token  互相不受影响
        assertThat(user1.get("authorization")).isEqualTo("user1token");
        assertThat(user2.get("authorization")).isEqualTo("user2token");
        assertThat(user3.get("authorization")).isEqualTo("user3token");
        // 断言用户3的Content-Type 与用户1的不同
        assertThat(user3.get("Content-Type")).isNotEqualTo(user1.get("Content-Type"));

    }

}