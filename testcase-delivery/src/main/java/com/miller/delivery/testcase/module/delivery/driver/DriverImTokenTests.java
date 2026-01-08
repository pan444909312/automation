package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取骑手token
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JPP6DFV0Y8N6V9G2KS368GAK", // 从JSON文件中获取的scenarioID
        scenarioName = "骑手app-登录后默认请求的接口-获取骑手token",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取骑手token")
public class DriverImTokenTests {

    @DisplayName("获取骑手token")
    @Test
    void shouldGetDriverImToken() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 获取骑手token
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头（longitude和latitude为空字符串）
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "");
        headers.put("latitude", "");
        headers.put("version", "5.56.1");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

