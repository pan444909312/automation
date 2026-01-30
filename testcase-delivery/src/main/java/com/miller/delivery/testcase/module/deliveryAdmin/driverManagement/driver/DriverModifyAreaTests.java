package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-修改骑手接单商圈
 */
@Scenario(
        scenarioID = "01JNNE6CKKGMB6N5ZDAPCY5YXF",
        scenarioName = "【主干场景】司管后台-骑手列表-修改骑手接单商圈",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("修改骑手接单商圈")
public class DriverModifyAreaTests {

    @DisplayName("添加商圈")
    @Test
    void shouldModifyDriverArea() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 修改骑手接单商圈
        modifyDriverArea(token);
    }

    private void modifyDriverArea(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/modifyDriverArea";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"driverId\":1398714150,\"areaIds\":[51,163,436,470,476]}";

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

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

