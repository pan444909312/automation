package com.miller.delivery.testcase.module.deliveryAdmin.salaryManagement;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KDQMD3KTA0B0HHEV4JVYA88Z",
        scenarioName = "薪资列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("薪资列表")
public class SalaryListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    private LocalDate getLastWeekMonday(LocalDate today) {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int daysToLastMonday = dayOfWeek == DayOfWeek.SUNDAY ? 6 : dayOfWeek.getValue() - 1 + 7;
        return today.minusDays(daysToLastMonday);
    }

    private LocalDate getLastWeekSunday(LocalDate lastMonday) {
        return lastMonday.plusDays(6);
    }

    @DisplayName("薪资列表")
    @Test
    void shouldGetSalaryList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算上周一和上周日
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = getLastWeekMonday(today);
        LocalDate lastSunday = getLastWeekSunday(lastMonday);
        String beginTime = lastMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endTime = lastSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 薪资列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/salary/manager/getSalaryListInfo";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"beginTime\": \"%s\",\r\n" +
                "    \"endTime\": \"%s\",\r\n" +
                "    \"driverId\": null,\r\n" +
                "    \"driverLoginName\": null,\r\n" +
                "    \"driverName\": null,\r\n" +
                "    \"driverBusinessTypeList\": [],\r\n" +
                "    \"areaIdList\": [],\r\n" +
                "    \"sortAttr\": null,\r\n" +
                "    \"sortRuleType\": null,\r\n" +
                "    \"includeDeleted\": true,\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 15\r\n" +
                "}", beginTime, endTime);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("薪资列表-搜索骑手ID")
    @Test
    void shouldGetSalaryListByDriverId() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算上周一和上周日
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = getLastWeekMonday(today);
        LocalDate lastSunday = getLastWeekSunday(lastMonday);
        String beginTime = lastMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endTime = lastSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 薪资列表-搜索骑手ID
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/salary/manager/getSalaryListInfo";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"beginTime\": \"%s\",\r\n" +
                "    \"endTime\": \"%s\",\r\n" +
                "    \"driverId\": 249223,\r\n" +
                "    \"driverLoginName\": null,\r\n" +
                "    \"driverName\": null,\r\n" +
                "    \"driverBusinessTypeList\": [],\r\n" +
                "    \"areaIdList\": [],\r\n" +
                "    \"sortAttr\": null,\r\n" +
                "    \"sortRuleType\": null,\r\n" +
                "    \"includeDeleted\": true,\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 15\r\n" +
                "}", beginTime, endTime);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
}

