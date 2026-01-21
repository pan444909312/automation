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
 * log-老的配送时长
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT5SHPBG33K5PSNFJGKJCCE",
        scenarioName = "老的配送方案-log",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("log-老的配送时长")
public class OldDeliveryTimeConfigLogTests {

    @DisplayName("获取老的配送时长配置日志")
    @Test
    void shouldGetOldDeliveryTimeConfigLog() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取老的配送时长配置日志
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/time/config/getLog";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"configId\":null,\"configName\":null,\"operation\":null,\"operator\":null,\"time\":null,\"timeEnd\":null,\"timeStart\":null,\"pageNo\":1,\"pageSize\":10,\"city\":\"杭州市\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("Content-Type", "application/json");
        return headers;
    }
}

