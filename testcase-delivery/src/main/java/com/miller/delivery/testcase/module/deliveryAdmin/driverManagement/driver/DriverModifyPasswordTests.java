package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改骑手登录密码
 */
@Scenario(
        scenarioID = "01KDQ2DCDKTZHP61JZDPCJX2HK",
        scenarioName = "修改骑手登录密码",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("修改骑手登录密码")
public class DriverModifyPasswordTests {

    @DisplayName("修改骑手登录密码")
    @Test
    void shouldModifyDriverPassword() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 修改骑手登录密码
        modifyPassword(token);
    }

    private void modifyPassword(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/deliveryman/1398715578";
        String method = "PATCH";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"op\":\"pwd_modify\",\"newPassword\":\"AA2010aa\"}";

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

