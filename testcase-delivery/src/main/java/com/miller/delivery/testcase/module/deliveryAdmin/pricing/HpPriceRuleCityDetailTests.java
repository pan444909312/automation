package com.miller.delivery.testcase.module.deliveryAdmin.pricing;

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

/**
 * 司管_HP基础定价_查询A城全城HP定价配置详情
 *
 * Apifox: docs/d-apifox/toCheck/司管_HP基础定价_查询A城全城HP定价配置详情.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KDQB1WGTQY4X3C6F61M670WS",
        scenarioName = "司管_HP基础定价_查询A城全城HP定价配置详情",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查询A城全城HP定价配置详情")
public class HpPriceRuleCityDetailTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("查询A城全城HP定价配置详情")
    @Test
    void shouldQueryCityHpPriceRuleDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算日期范围（本周）
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate sunday = monday.plusDays(6);
        String dateStart = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateEnd = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) D侧HP基础定价列表
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/base/priceRuleList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = String.format("{\"city\":\"杭州市\",\"dateStart\":\"%s\",\"dateEnd\":\"%s\"}", dateStart, dateEnd);
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1).node("message").isEqualTo("成功");

        // 4) 提取ruleId（按 apifox 选择 HpPriceRuleId0）
        String ruleId = TestCaseHelpful.extractValue(responseBody1, "$.data.list[0].ruleId").toString();

        // 5) 获取HP区域基础定价规则详细
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/base/basePriceRuleDetail";
        String body2 = String.format("{\"ruleId\":\"%s\"}", ruleId);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2).node("message").isEqualTo("成功");

        // 6) 获取HP区域基础定价规则群组信息
        String uri3 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/getDriverGroupAllList";
        String body3 = "{\"cityList\":[\"杭州市\"]}";
        var responseBody3 = TestCaseHelpful.sendRequest(method, uri3, null, headers, body3);
        TestCaseHelpful.assertThatJson(responseBody3).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody3).node("message").isEqualTo("成功");
    }
}

