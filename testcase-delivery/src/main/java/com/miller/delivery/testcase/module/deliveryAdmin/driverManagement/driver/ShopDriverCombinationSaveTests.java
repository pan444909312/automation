package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-新增指定骑手配置
 */
@Scenario(
        scenarioID = "01KDSNH7YDENTMM3GG8A1FGDQ0",
        scenarioName = "骑手列表-新增指定骑手配置",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("新增指定骑手")
public class ShopDriverCombinationSaveTests {

    @DisplayName("新增指定骑手配置")
    @Test
    void shouldSaveShopDriverCombination() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增指定骑手配置
        saveShopDriverCombination(token);

        // 3) 获取第一个配置ID（用于验证）
        getFirstCombinationId(token);
    }

    private void saveShopDriverCombination(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/saveShopDriverCombination";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        // 使用时间戳作为name，确保唯一性
        long timestamp = System.currentTimeMillis();
        String body = String.format("{\"id\":null,\"city\":\"奥克兰\",\"name\":\"%d\"}", timestamp);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void getFirstCombinationId(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/shopDriverCombination";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"name\":null,\"city\":null,\"shopId\":null,\"shopName\":null,\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        String body = "{\"password\":\"d9501f93554734ba83d19c9dc83ef4fb\",\"userName\":\"ding023660390221528503\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

