package com.miller.delivery.testcase.module.deliveryAdmin.DeliveryAreaSettings.deliveryTime;

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


    @DisplayName("删除老的配送时长配置")
    @Test
    void shouldDeleteOldDeliveryTimeConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        OldDeliveryTimeConfigAddTests oldDeliveryTimeConfigAddTests = new OldDeliveryTimeConfigAddTests();
        oldDeliveryTimeConfigAddTests.shouldAddOldDeliveryTimeConfig();
        Long oldconfigId = oldDeliveryTimeConfigAddTests.oldconfigId;
        // 2) 删除老的配送时长配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/time/config/statusUpdate";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"configId\":%d,\"status\":2}", oldconfigId);
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

