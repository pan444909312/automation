package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-招新广告投放
 */
@Scenario(
        scenarioID = "01KDSS7HJQN2JSY695B2H2DRMY",
        scenarioName = "骑手列表-招新广告投放",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手广告投放")
public class DriverAdDeliveryTests {

    @DisplayName("招新广告投放")
    @Test
    void shouldGetRecruitmentChannelList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取招新广告投放列表
        getRecruitmentChannelList(token);
    }

    private void getRecruitmentChannelList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/recruitmentChannel/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"channelName\":\"\",\"city\":\"杭州市\",\"pageNo\":1,\"pageSize\":15}";

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

