package com.miller.delivery.testcase.module.deliveryAdmin.invoice;

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

/**
 * 财务账期-列表
 *
 * Apifox: docs/d-apifox/toCheck/账期列表.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KDQBAXY9XN21FQKF5TF7PA18",
        scenarioName = "财务账期-列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("账期列表")
public class DriverInvoiceListTests {

    @DisplayName("获取账期列表-默认")
    @Test
    void shouldGetInvoiceListDefault() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/invoice/driverInvoice";
        String body = String.format("{\"city\":null,\"startDate\":\"2025-12-01\",\"endDate\":\"2025-12-30\",\"status\":null,\"type\":null,\"pageNo\":1,\"pageSize\":10}");
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");

        Object totalNumberObj = TestCaseHelpful.extractValue(responseBody, "$.data.totalNumber");
        assert totalNumberObj != null : "totalNumber 不应为空";
        long totalNumber = Long.parseLong(totalNumberObj.toString());
        assert totalNumber > 0 : "账期列表返回数量应大于 0";
    }

    @DisplayName("获取账期列表-城市、日期、已结算、骑手收入筛选")
    @Test
    void shouldGetInvoiceListWithFilters() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);


        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/invoice/driverInvoice";
        String body = String.format("{\"city\":\"杭州市\",\"startDate\":\"2025-12-01\",\"endDate\":\"2025-12-30\",\"status\":2,\"type\":0,\"pageNo\":1,\"pageSize\":10}");
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");

        Object totalNumberObj = TestCaseHelpful.extractValue(responseBody, "$.data.totalNumber");
        assert totalNumberObj != null : "totalNumber 不应为空";
        long totalNumber = Long.parseLong(totalNumberObj.toString());
        assert totalNumber > 0 : "账期列表返回数量应大于 0";
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    private static final class DateRange {
        final String start;
        final String end;

        private DateRange(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 计算上周周一~周日（对齐 apifox 脚本意图）
     */
    private DateRange getLastWeekRange() {
        LocalDate today = LocalDate.now();
        DayOfWeek dow = today.getDayOfWeek();
        int dayOfWeek = dow.getValue(); // 1(Mon) .. 7(Sun)
        int daysToLastMonday = (dayOfWeek == 7) ? 13 : (dayOfWeek - 1 + 7);
        LocalDate lastMonday = today.minusDays(daysToLastMonday);
        LocalDate lastSunday = lastMonday.plusDays(6);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new DateRange(lastMonday.format(fmt), lastSunday.format(fmt));
    }
}

