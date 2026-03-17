package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.riderOnboardingConfig;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手入驻配置-开启关闭配置
 *
 * @author 江彪
 * @version 2.0
 * @since 2026/02/28
 */
@Scenario(
        scenarioID = "01KJHR1705D3KMNRS3PTSBXHET",
        scenarioName = "骑手入驻配置-开启关闭配置",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("骑手入驻配置-开启关闭配置")
public class riderOnboardingConfigOpenAndCloseTests {

    @DisplayName("骑手入驻配置-开启关闭配置")
    @Test
    void riderOnboardingConfigOpenAndClose() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 开启杭州市推送配置
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/defaultConfig/openConfig";
        String body = "{\"open\":1,\"city\":\"杭州市\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 4) 关闭杭州市推送配置
        String closeBody = "{\"open\":0,\"city\":\"杭州市\"}";
        var closeResponseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, closeBody);

        // 5) 断言
        TestCaseHelpful.assertThatJson(closeResponseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

    }


    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

