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

/**
 * 司管_HP基础定价_修改指定区域HP定价_区分载具+时段+群组
 *
 * Apifox: docs/d-apifox/toCheck/司管_HP基础定价_修改指定区域HP定价_区分载具+时段+群组.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KDQCMMXEMAX1B084A6ZYQMB5",
        scenarioName = "司管_HP基础定价_修改指定区域HP定价_区分载具+时段+群组",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("修改指定区域HP定价_区分载具+时段+群组")
public class HpPriceRuleAreaUpdateComplexTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    private String getRuleIdFromList(String responseBody, int index) {
        try {
            return TestCaseHelpful.extractValue(responseBody, String.format("$.data.list[%d].ruleId", index)).toString();
        } catch (Exception e) {
            return TestCaseHelpful.extractValue(responseBody, "$.data.list[0].ruleId").toString();
        }
    }

    @DisplayName("修改指定区域HP定价（区分载具+时段+群组）")
    @Test
    void shouldUpdateHpPriceRuleAreaComplex() {
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

        // 4) 提取 ruleId（按 apifox 选择 HpPriceRuleId1）
        String ruleId = getRuleIdFromList(responseBody1, 1);

        // 5) 修改全城定价，区分载具+区分时段+区分群组（apifox原始body）
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/base/updateBasePriceRule";
        String body2 = String.format("{\"areaId\":\"0\",\"areaIdList\":[],\"city\":\"宣城市\",\"basePriceId\":\"%s\",\"diffTransportation\":1,\"periodType\":1,\"timeInfoList\":[{\"groupIdList\":[1857],\"groupSort\":1,\"timeRuleList\":[{\"startTime\":\"00:00\",\"endTime\":\"15:00\",\"adjustmentType\":0,\"price\":0.5},{\"startTime\":\"15:00\",\"endTime\":\"20:00\",\"adjustmentType\":0,\"price\":0.8}]}],\"planType\":1,\"distanceRuleList\":[{\"distance\":0.00001,\"distanceCoefficient\":0,\"priceCoefficient\":0,\"fixedCommission\":1,\"vehicleType\":0,\"type\":1},{\"distance\":0.00001,\"distanceCoefficient\":0.1,\"priceCoefficient\":0.1,\"fixedCommission\":1,\"vehicleType\":0,\"type\":0},{\"distance\":0.00001,\"distanceCoefficient\":0,\"priceCoefficient\":0,\"fixedCommission\":2,\"vehicleType\":1,\"type\":1},{\"distance\":0.00001,\"distanceCoefficient\":0.1,\"priceCoefficient\":0.1,\"fixedCommission\":2,\"vehicleType\":1,\"type\":0},{\"distance\":0.00001,\"distanceCoefficient\":0,\"priceCoefficient\":0,\"fixedCommission\":3,\"vehicleType\":2,\"type\":1},{\"distance\":0.00001,\"distanceCoefficient\":0.00001,\"priceCoefficient\":0.1,\"fixedCommission\":3,\"vehicleType\":2,\"type\":0},{\"distance\":0.00001,\"distanceCoefficient\":0,\"priceCoefficient\":0,\"fixedCommission\":5,\"vehicleType\":3,\"type\":1},{\"distance\":0.00001,\"distanceCoefficient\":0.1,\"priceCoefficient\":0.1,\"fixedCommission\":5,\"vehicleType\":3,\"type\":0},{\"distance\":0.00001,\"distanceCoefficient\":0,\"priceCoefficient\":0,\"fixedCommission\":4,\"vehicleType\":4,\"type\":1},{\"distance\":0.00001,\"distanceCoefficient\":0.1,\"priceCoefficient\":0.1,\"fixedCommission\":4,\"vehicleType\":4,\"type\":0}],\"diffGroup\":0,\"timeRuleDiffGroup\":1,\"groupRuleList\":[]}", ruleId);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2).node("message").isEqualTo("成功");
    }
}

