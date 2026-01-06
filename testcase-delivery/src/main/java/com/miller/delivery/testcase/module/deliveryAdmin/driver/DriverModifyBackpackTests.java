package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-修改最大背包设置
 */
@Scenario(
        scenarioID = "01JNNE6CKKGMB6N5ZDAPCY5YXG",
        scenarioName = "【主干场景】司管后台-骑手列表-修改最大背包设置",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("修改最大背包设置")
public class DriverModifyBackpackTests {

    @DisplayName("修改背包最大个数")
    @Test
    void shouldModifyDriverReceiveOrderNum() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 修改背包最大个数
        modifyBackpack(token);
    }

    private void modifyBackpack(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/modifyDriverReceiveOrderNum";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"driverBusinessType\":0,\"driverId\":1398714150,\"receiveOrderNum\":22}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
        headers.put("enableSign", "false");
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
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

