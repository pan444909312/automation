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
        scenarioID = "01KDQMMRKSV4SJQXT261BZ7FB7",
        scenarioName = "薪资列表-全部导出",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("薪资列表-全部导出")
public class SalaryListBatchExportTests {

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

    @DisplayName("薪资列表-全部导出")
    @Test
    void shouldBatchExportSalaryList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算上周一和上周日
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = getLastWeekMonday(today);
        LocalDate lastSunday = getLastWeekSunday(lastMonday);
        String beginTime = lastMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endTime = lastSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 薪资列表-全部导出
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/salary/manager/batchDownloadFile";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"beginTime\": \"%s\",\r\n" +
                "    \"endTime\": \"%s\"\r\n" +
                "}", beginTime, endTime);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
}

