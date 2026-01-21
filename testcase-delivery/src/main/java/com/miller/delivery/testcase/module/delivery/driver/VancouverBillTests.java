package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 温哥华法案
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K9Y1F9QKD2CHKSDY776NE3WX", // Placeholder, update with actual ID
        scenarioName = "骑手app-温哥华法案",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("温哥华法案")
public class VancouverBillTests {

    @DisplayName("温哥华法案")
    @Test
    void shouldGetVancouverBillData() {
        // 注意：JSON中没有登录步骤，只有OPTIONS和POST请求
        
        // 1) OPTIONS预检请求（可选，通常浏览器自动处理）
        // 跳过OPTIONS请求，直接发送POST请求
        
        // 2) 温哥华法案（H5验签）
        String uri = "https://api-cn-f2e-test.hungrypanda.cn/api/delivery/app/act/vancouver/data";
        String method = "POST";
        Map<String, Object> headers = createH5Headers();
        
        // 构建H5验签请求体（使用JSON中的authorization值）
        String body = buildH5SignBody("d1d3ebba74427c37c1b094f3513849ac");
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        // 注意：JSON中主接口没有test脚本，只验证基本响应结构
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isNotNull();
    }

    /**
     * 构建H5验签请求体
     */
    private String buildH5SignBody(String authorization) {
        long timestamp = System.currentTimeMillis();
        String nonce = "H0Bc6hwsgNP6m5lYA25CMntR2";
        String deviceId = "29dc04d7e512594";
        
        // 构建ph对象
        String ph = String.format(
                "{\"appTypeId\":\"2\",\"language\":\"CN\",\"version\":\"5.70.0\",\"authorization\":\"%s\"," +
                "\"countrycode\":\"CN\",\"latitude\":\"30.203564\",\"longitude\":\"120.216862\"," +
                "\"deviceVersion\":\"16.7.2\",\"platform\":\"WEB_IOS\",\"device\":\"1\",\"countryCode\":\"CN\"," +
                "\"appVersion\":\"5.70.0\",\"locale\":\"zh-CN\",\"deviceId\":\"BFEC8953-4F04-4AAC-9C06-7EB2C8CA1411\"," +
                "\"uniqueToken\":\"BFEC8953-4F04-4AAC-9C06-7EB2C8CA1411\"}",
                authorization);
        
        // 构建pd对象
        String pd = "{\"dateStart\":\"2025-07-21\",\"dateEnd\":\"2025-07-27\"}";
        
        // 构建完整请求体
        return String.format(
                "{\"pm\":\"POST\",\"ph\":%s,\"pd\":%s,\"nv\":\"2\",\"nt\":\"%d\",\"nn\":\"%s\",\"nd\":\"%s\"}",
                ph, pd, timestamp, nonce, deviceId);
    }

    /**
     * 创建H5请求头
     */
    private Map<String, Object> createH5Headers() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-language", "zh-CN,zh-Hans;q=0.9");
        headers.put("content-type", "application/json");

        return headers;
    }
}

