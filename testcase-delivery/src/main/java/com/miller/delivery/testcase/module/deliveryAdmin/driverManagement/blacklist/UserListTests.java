package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.blacklist;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-用户黑名单-用户列表
 */
@Scenario(
        scenarioID = "01KDSYAP281KHX6ARN7T2T38A5",
        scenarioName = "骑手列表-用户黑名单-用户列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("用户列表")
public class UserListTests {

    @DisplayName("用户列表")
    @Test
    void shouldGetUserList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取用户列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/blacklist/userList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"city\":\"杭州市\",\"pageNo\":1,\"pageSize\":10}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言获取成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
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

