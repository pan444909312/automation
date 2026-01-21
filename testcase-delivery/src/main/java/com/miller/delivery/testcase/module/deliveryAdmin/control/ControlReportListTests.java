package com.miller.delivery.testcase.module.deliveryAdmin.control;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-特殊情况报备-获取列表数据
 */
@Scenario(
        scenarioID = "01JNNE6CKJ28TNDMEMC1DAJX3S",
        scenarioName = "【主干场景】司管后台-特殊情况报备-获取列表数据",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("获取列表数据")
public class ControlReportListTests {

    @DisplayName("获取列表数据")
    @Test
    void shouldGetReportList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取列表数据
        getReportList(token);
    }

    private void getReportList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/report/page";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{"
                + "\"areaIdList\":[],"
                + "\"cityName\":\"杭州市\","
                + "\"reportNo\":\"\","
                + "\"reportStartTimeStr\":null,"
                + "\"reportEndTimeStr\":null,"
                + "\"reportStatus\":\"\","
                + "\"reportType\":\"\","
                + "\"pageNo\":1,"
                + "\"pageSize\":15"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 可以提取reportNo用于后续操作: TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].reportNo")
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

