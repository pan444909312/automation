package com.miller.delivery.testcase.module.deliveryAdmin.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-获取骑手数据统计列表
 */
@Scenario(
        scenarioID = "01KDSMET66GM295GNSJQJRC4BN",
        scenarioName = "获取骑手数据统计列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手数据统计")
public class DriverDataStatisticsTests {

    @DisplayName("骑手数据统计列表")
    @Test
    void shouldGetDriverDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手数据统计列表
        getDriverDataList(token);
    }

    private void getDriverDataList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverData/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        // 生成日期范围（最近一周）
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStart = startDate.format(formatter);
        String dateEnd = endDate.format(formatter);

        String body = String.format("{"
                + "\"driverId\":null,"
                + "\"driverName\":null,"
                + "\"telephone\":null,"
                + "\"cityList\":[],"
                + "\"orderAreaIdList\":[],"
                + "\"dates\":null,"
                + "\"dateStart\":\"%s\","
                + "\"dateEnd\":\"%s\","
                + "\"desc\":null,"
                + "\"sortName\":null,"
                + "\"eliminateReport\":1,"
                + "\"pageNo\":1,"
                + "\"pageSize\":10"
                + "}", dateStart, dateEnd);

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
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

