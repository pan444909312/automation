package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-获取骑手申诉列表
 */
@Scenario(
        scenarioID = "01KDQMHG8YNV9S1CR2E46JBBN6",
        scenarioName = "获取骑手申诉列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取骑手申诉列表")
public class DriverPunishAppealListTests {

    @DisplayName("获取骑手申诉列表")
    @Test
    void shouldGetDriverPunishAppealList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手申诉列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverPunishAppeal/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"auditStatusList\":[0],\"cityList\":[],\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

