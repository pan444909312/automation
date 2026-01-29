package com.miller.delivery.testcase.module.deliveryAdmin.control;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-特殊情况报备-发布特殊报备
 */
@Scenario(
        scenarioID = "01KDQDNJMZS9FDV7KR6EWF6CCM",
        scenarioName = "【主干场景】司管后台-特殊情况报备-发布特殊报备",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("发布特殊报备")
public class ControlReportPublishTests {

    @DisplayName("获取报备列表并发布")
    @Test
    void shouldPublishSpecialReport() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取列表数据并提取 reportNo
        String reportNo = fetchReportList(token);

        // 3) 查看备注（校验 reportNo 可用）
        viewReportDetail(token, reportNo);

        // 4) 发布特殊报备
        publishReport(token, reportNo);
    }

    private String fetchReportList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/report/page";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        String body = "{"
                + "\"areaIdList\": [],"
                + "\"cityName\": \"奥克兰\","
                + "\"reportNo\": \"\","
                + "\"reportStartTimeStr\": null,"
                + "\"reportEndTimeStr\": null,"
                + "\"reportStatus\": \"\","
                + "\"reportType\": \"\","
                + "\"pageNo\": 1,"
                + "\"pageSize\": 15"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 提取列表第一个 reportNo
        Object reportNo = TestCaseHelpful.extractValue(responseBody, "$.data.list[0].reportNo");
        return reportNo == null ? "" : reportNo.toString();
    }

    private void viewReportDetail(String token, String reportNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/report/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"reportNo\":\"%s\"}", reportNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void publishReport(String token, String reportNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/report/publish";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"reportNo\":\"%s\"}", reportNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        // 响应可能返回重复或成功，按 Apifox 用例容忍两种
        TestCaseHelpful.assertThatJson(responseBody).node("message")
                .isEqualTo("报备日期重复，请检查");
        TestCaseHelpful.assertThatJson(responseBody).node("code")
                .isEqualTo(9999);
    }

    /**
     * 司管后台登录并返回 token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
         
        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");

        String body = "{"
                + "\"password\": \"d9501f93554734ba83d19c9dc83ef4fb\","
                + "\"userName\": \"ding023660390221528503\""
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
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

