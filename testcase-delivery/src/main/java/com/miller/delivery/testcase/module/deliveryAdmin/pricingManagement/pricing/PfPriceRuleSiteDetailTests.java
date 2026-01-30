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
        scenarioID = "01KDQCXPXQ0H36KFVKTNFMNHPW",
        scenarioName = "司管_PF基础定价_查询A城B站点PF定价配置详情",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查询A城B站点PF定价配置详情")
public class PfPriceRuleSiteDetailTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("查询A城B站点PF定价配置详情")
    @Test
    void shouldGetPfPriceRuleSiteDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算日期范围（本周）
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate sunday = monday.plusDays(6);
        String dateStart = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateEnd = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) D侧PF基础定价列表
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/pfBase/priceRuleList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"dateStart\": \"%s\",\r\n" +
                "    \"dateEnd\": \"%s\"\r\n" +
                "}", dateStart, dateEnd);
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 4) 提取ruleId（取第二个，即站点级别的）
        String ruleId = TestCaseHelpful.extractValue(responseBody1, "$.data.list[1].ruleId");

        // 5) 获取PF区域基础定价规则详细
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/pfBase/basePriceRuleDetail";
        String body2 = String.format("{\r\n" +
                "    \"ruleId\": \"%s\"\r\n" +
                "}", ruleId);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");

        // 6) 获取PF区域基础定价规则群组信息
        String uri3 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/getDriverGroupAllList";
        String body3 = "{\r\n" +
                "    \"cityList\": [\"杭州市\"]\r\n" +
                "}";
        var responseBody3 = TestCaseHelpful.sendRequest(method, uri3, null, headers, body3);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("message").isEqualTo("成功");
    }
}

