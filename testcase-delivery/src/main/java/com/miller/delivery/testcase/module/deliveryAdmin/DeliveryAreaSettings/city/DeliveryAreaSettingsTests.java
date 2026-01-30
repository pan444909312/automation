package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.city;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 获取配送区域设置
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT48WZ11FM3XPYNV6TEVDAG",
        scenarioName = "获取配送区域设置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取配送区域设置")
public class DeliveryAreaSettingsTests {

    @DisplayName("获取城市配送区域列表")
    @Test
    void shouldGetCityDeliveryAreaList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取城市配送区域列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/city/detail/list?pageNo=1&pageSize=10";
        String method = "GET";
        Map<String, Object> headers = createHeaders(token);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, null);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("获取杭州配送区域")
    @Test
    void shouldGetHangzhouDeliveryArea() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取杭州配送区域
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/city/detail/list?pageNo=1&pageSize=10&cityId=1";
        String method = "GET";
        Map<String, Object> headers = createHeaders(token);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, null);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 4) 验证返回包含杭州市
        String responseText = responseBody.toString();
        assert responseText.contains("杭州市") : "返回结果应包含杭州市";
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");

        return headers;
    }
}

