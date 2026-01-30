package com.miller.delivery.testcase.module.deliveryAdmin.salaryManagement;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手列表-查看骑手薪资
 */
@Scenario(
        scenarioID = "01KDQ4BHCJ6E4ZGDFBE84NR654",
        scenarioName = "骑手列表-查看骑手薪资",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("查看骑手薪资")
public class DriverSalaryInfoTests {

    @DisplayName("查看骑手薪资列表与统计")
    @Test
    void shouldQueryDriverSalary() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 查看骑手薪资
        fetchSalaryList(token);

        // 3) 骑薪资统计（原因类型）
        fetchSalaryStatistics(token);
    }

    private void fetchSalaryList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/salary/manager/getDriverSalaryListInfo";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        String body = "{"
                + "\"driverId\": \"1398715578\","
                + "\"beginDate\": \"2025-12-23\","
                + "\"endDate\": \"2025-12-29\","
                + "\"city\": null,"
                + "\"receiptTimeStart\": null,"
                + "\"receiptTimeEnd\": null,"
                + "\"deliveryTimeStart\": null,"
                + "\"deliveryTimeEnd\": null"
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void fetchSalaryStatistics(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/special-order/adjustmentSalaryReasonType";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
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

