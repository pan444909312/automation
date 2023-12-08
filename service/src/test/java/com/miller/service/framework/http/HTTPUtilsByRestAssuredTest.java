package com.miller.service.framework.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("测试HTTP请求工具类")
class HTTPUtilsByRestAssuredTest {
    @Test
    @DisplayName("请求头Content-Type为 x-www.form-urlencoded")
    void testBodyIsFormUrlencoded() {
        HTTPUtilsByRestAssured httpUtilsByRestAssured = new HTTPUtilsByRestAssured();
        var uri = "http://www.baidu.com";
        var params = new HashMap<String, Object>();
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        var body = new HashMap<String, Object>();
        body.put("name", "Miller");
        var cookies = new HashMap<String, Object>();

        Map<String, Object> stringObjectMap = httpUtilsByRestAssured
                .sendPostRequest(uri, params, headers, body, cookies);
        Map<String, Object> status = (Map<String, Object>) stringObjectMap.get("status");
        assertThat(status.get("statusCode")).isEqualTo(200);
        assertThat(status.get("statusCode")).isNotEqualTo(500);
    }


    @Test
    @DisplayName("请求头Content-Type为空，测试请求体可能是 x-www.form-urlencoded")
    void testBodyMaybeIsFormUrlencoded() {
        HTTPUtilsByRestAssured httpUtilsByRestAssured = new HTTPUtilsByRestAssured();
        var uri = "http://www.baidu.com";
        var params = new HashMap<String, Object>();
        var headers = new HashMap<String, Object>();
        // 请求头未指定类型，根据请求体类型进行判断
        var body = new HashMap<String, Object>();
        body.put("name", "Miller");
        var cookies = new HashMap<String, Object>();

        Map<String, Object> stringObjectMap = httpUtilsByRestAssured
                .sendPostRequest(uri, params, headers, body, cookies);
        Map<String, Object> status = (Map<String, Object>) stringObjectMap.get("status");
        assertThat(status.get("statusCode")).isEqualTo(200);
        assertThat(status.get("statusCode")).isNotEqualTo(500);
    }

    @Test
    @DisplayName("请求头Content-Type为空，测试框架的默认处理机制")
    void testBodyIsJson() {
        HTTPUtilsByRestAssured httpUtilsByRestAssured = new HTTPUtilsByRestAssured();
        var uri = "http://www.baidu.com";
        var params = new HashMap<String, Object>();
        var headers = new HashMap<String, Object>();
        var body = "{\"test\":1024}";
        var cookies = new HashMap<String, Object>();
        Map<String, Object> stringObjectMap = httpUtilsByRestAssured
                .sendPostRequest(uri, params, headers, body, cookies);
        Map<String, Object> status = (Map<String, Object>) stringObjectMap.get("status");
        assertThat(status.get("statusCode")).isEqualTo(200);
        assertThat(status.get("statusCode")).isNotEqualTo(500);
    }
}