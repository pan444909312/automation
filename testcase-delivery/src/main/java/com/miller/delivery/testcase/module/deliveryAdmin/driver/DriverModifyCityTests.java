package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改骑手城市
 */
@Scenario(
        scenarioID = "01KDQ07D6WCB6KGBJJJT2CCF36",
        scenarioName = "修改骑手城市",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("修改骑手城市")
public class DriverModifyCityTests {

    @DisplayName("修改骑手城市（杭州市<->奥克兰）")
    @Test
    void shouldModifyDriverCity() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 修改杭州市为奥克兰
        modifyCityToAuckland(token);

        // 等待1秒
        Thread.sleep(1000);

        // 3) 修改奥克兰为杭州市（恢复）
        modifyCityToHangzhou(token);
    }

    private void modifyCityToAuckland(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/modifyCityAreaAndSite";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"driverId\":1398715578,\"city\":\"奥克兰\",\"areaIds\":[244],\"siteIdList\":[]}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void modifyCityToHangzhou(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/modifyCityAreaAndSite";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"driverId\":1398715578,\"city\":\"杭州市\",\"areaIds\":[51],\"siteIdList\":[]}";

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

