package com.miller.delivery.testcase.module.deliveryAdmin.deliveryTime;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 删除-老的配送时长
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT5NVZHKQEYM22C42WBPSTQ",
        scenarioName = "老的配送方案-删除",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("删除-老的配送时长")
public class OldDeliveryTimeConfigDeleteTests {

    // 注意：需要在实际使用时替换为真实的 delivery_time_config_id
    private static final Long DELIVERY_TIME_CONFIG_ID = 1L; // 请从质量平台或数据库中获取实际的 delivery_time_config_id

    @DisplayName("删除老的配送时长配置")
    @Test
    void shouldDeleteOldDeliveryTimeConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 删除老的配送时长配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/time/config/statusUpdate";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"configId\":%d,\"status\":2}", DELIVERY_TIME_CONFIG_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("Content-Type", "application/json");
        return headers;
    }
}

