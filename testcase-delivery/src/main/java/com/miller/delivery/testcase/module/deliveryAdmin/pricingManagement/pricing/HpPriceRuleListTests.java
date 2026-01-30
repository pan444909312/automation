package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.pricing;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KDQ237E5PAD5YEEB919YSTTQ",
        scenarioName = "司管_HP基础定价_HP定价列表_查询",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("HP定价列表查询")
public class HpPriceRuleListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("HP定价列表查询")
    @Test
    void shouldQueryHpPriceRuleList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算日期范围（本周）
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate sunday = monday.plusDays(6);
        String dateStart = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateEnd = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) D侧HP基础定价列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/base/priceRuleList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"dateStart\": \"%s\",\r\n" +
                "    \"dateEnd\": \"%s\"\r\n" +
                "}", dateStart, dateEnd);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
}

