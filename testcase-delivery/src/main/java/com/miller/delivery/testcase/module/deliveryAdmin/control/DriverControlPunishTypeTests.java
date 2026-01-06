package com.miller.delivery.testcase.module.deliveryAdmin.control;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手处罚列表-骑手管控-惩罚类型
 */
@Scenario(
        scenarioID = "01KDQMHG8YNV9S1CR2E46JBBN6",
        scenarioName = "骑手处罚列表-骑手管控-惩罚类型",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("骑手管控-惩罚类型")
public class DriverControlPunishTypeTests {

    @DisplayName("骑手管控-惩罚类型")
    @Test
    void shouldGetPunishTypes() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手管控-惩罚类型
        getPunishTypes(token);
    }

    private void getPunishTypes(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"driverBusinessType\":0}";

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
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

