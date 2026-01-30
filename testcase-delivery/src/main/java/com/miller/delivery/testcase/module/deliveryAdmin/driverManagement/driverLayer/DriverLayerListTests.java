package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driverLayer;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-分层列表
 */
@Scenario(
        scenarioID = "01K603WPAAH2SX5FYT0GWSMYAM",
        scenarioName = "司管-分层列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("分层列表")
public class DriverLayerListTests {

    @DisplayName("检查列表数据返回-所有城市")
    @Test
    void shouldGetLayerListAllCities() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取分层列表-所有城市
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityList\":[],\"workTypeList\":[],\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 检查列表返回的数据大于1条
        Integer totalNumber = Integer.valueOf(TestCaseHelpful.extractValue(responseBody, "$.data.totalNumber").toString());
        assert totalNumber > 1 : "列表数据应该大于1条";
    }

    @DisplayName("检查列表数据返回-杭州市下的")
    @Test
    void shouldGetLayerListByCity() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取分层列表-杭州市
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityList\":[\"杭州市\"],\"workTypeList\":[],\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 检查列表返回的数据大于1条
        Integer totalNumber = Integer.valueOf(TestCaseHelpful.extractValue(responseBody, "$.data.totalNumber").toString());
        assert totalNumber > 1 : "列表数据应该大于1条";
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Authorization", token);
        headers.put("Connection", "keep-alive");
        headers.put("Origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("Referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-site");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");

        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

