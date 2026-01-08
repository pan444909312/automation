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

/**
 * 票据审核-待审核tab列表
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDT0R718T88Y5RDCNTEK9X0W",
        scenarioName = "垫付管理-票据审核-待审核tab",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("票据审核-待审核tab列表")
public class InvoiceAuditPendingListTests {

    /**
     * 获取上周一和上周日
     */
    private String[] getLastWeekRange() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int daysToLastMonday = dayOfWeek == DayOfWeek.SUNDAY ? 6 : dayOfWeek.getValue() - 1 + 7;
        LocalDate lastMonday = today.minusDays(daysToLastMonday);
        LocalDate lastSunday = lastMonday.plusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new String[]{lastMonday.format(formatter), lastSunday.format(formatter)};
    }

    @DisplayName("待审核列表")
    @Test
    void shouldGetPendingAuditList() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 获取上周日期范围
        String[] lastWeekRange = getLastWeekRange();
        String lastWeekStart = lastWeekRange[0];
        String lastWeekEnd = lastWeekRange[1];

        // 3) 待审核列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/insteadOrder/list";
        String method = "POST";
        String body = String.format("{\n" +
                "    \"receiveDate\": [\n" +
                "        \"%s\",\n" +
                "        \"%s\"\n" +
                "    ],\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"auditStatus\": 0,\n" +
                "    \"receiveDateStart\": \"%s\",\n" +
                "    \"receiveDateEnd\": \"%s\"\n" +
                "}", lastWeekStart, lastWeekEnd, lastWeekStart, lastWeekEnd);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    @DisplayName("待审核列表-筛选城市")
    @Test
    void shouldGetPendingAuditListWithCity() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 获取上周日期范围
        String[] lastWeekRange = getLastWeekRange();
        String lastWeekStart = lastWeekRange[0];
        String lastWeekEnd = lastWeekRange[1];

        // 3) 待审核列表-筛选城市
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/insteadOrder/list";
        String method = "POST";
        String body = String.format("{\n" +
                "    \"receiveDate\": [\n" +
                "        \"%s\",\n" +
                "        \"%s\"\n" +
                "    ],\n" +
                "    \"city\": \"杭州市\",\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"auditStatus\": 0,\n" +
                "    \"receiveDateStart\": \"%s\",\n" +
                "    \"receiveDateEnd\": \"%s\"\n" +
                "}", lastWeekStart, lastWeekEnd, lastWeekStart, lastWeekEnd);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

