package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.station;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-禁用骑手驿站
 */
@Scenario(
        scenarioID = "01KGH4WWW5KZZZE8901C6EAW4S",
        scenarioName = "骑手列表-禁用骑手驿站",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("禁用骑手驿站")
public class StationDisableTests {



    @DisplayName("禁用、启用驿站")
    @Test
    void shouldDisableStation() {


        String ConfigNo="504646250062430432";
        // 2) 开启驿站
        disableStation(ConfigNo,1);
        // 2) 禁用驿站
        disableStation(ConfigNo,0);
    }
    private void disableStation(String ConfigNo,int configStatus) {
        // 1) 司管登录获取 token
        String token = erpLogin();


        // 2) 禁用驿站
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/configStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"configNo\":\"" + ConfigNo + "\",\"configStatus\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言禁用成功
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
    private String getConfigNo(){
        String token = erpLogin();


        // 2) 获取骑手驿站列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"日照市\"\n" +
                "    ],\n" +
                "    \"configStatus\": 1,\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 15\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");

        String configno = TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].configNo").toString();
        return configno;
    }
}

