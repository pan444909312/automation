package com.miller.delivery.testcase.module.deliveryAdmin.insteadOrder;

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
        scenarioID = "01KDT1NAFYRD2BYKJFJA10VC18",
        scenarioName = "垫付管理-票据审核-导出",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("垫付-票据审核-导出")
public class ExportInsteadOrderTests {

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

    @DisplayName("垫付-票据审核-导出")
    @Test
    void shouldExportInsteadOrder() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 计算上周一和上周日
        LocalDate today = LocalDate.now();
        LocalDate lastMonday = getLastWeekMonday(today);
        LocalDate lastSunday = getLastWeekSunday(lastMonday);
        String lastWeekStart = lastMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String lastWeekEnd = lastSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 垫付-票据审核-导出
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/insteadOrder/exportInsteadOrder";
        String method = "POST";
        String body = String.format("{\"receiveDate\":[\"%s\",\"%s\"],\"pageNo\":1,\"pageSize\":10,\"auditStatus\":1,\"receiveDateStart\":\"%s\",\"receiveDateEnd\":\"%s\"}",
                lastWeekStart, lastWeekEnd, lastWeekStart, lastWeekEnd);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
}

