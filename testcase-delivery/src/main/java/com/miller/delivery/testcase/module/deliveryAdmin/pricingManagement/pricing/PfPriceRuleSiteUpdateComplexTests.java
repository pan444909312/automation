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
        scenarioID = "01KDQE4W6ASDRTS8TEKCEYA4D2",
        scenarioName = "司管_PF基础定价_修改指定站点PF定价_区分载具+时段+群组",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("修改指定站点PF定价_区分载具+时段+群组")
public class PfPriceRuleSiteUpdateComplexTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("修改指定站点PF定价_区分载具+时段+群组")
    @Test
    void shouldUpdatePfPriceRuleSiteComplex() {
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

        // 5) 修改指定站点定价，区分群组+不区分距离+不时段加价
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/pfBase/updateBasePriceRule";
        String body2 = String.format("{\r\n" +
                "    \"areaIdList\": [],\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"basePriceId\": \"%s\",\r\n" +
                "    \"diffTransportation\": 1,\r\n" +
                "    \"periodType\": 1,\r\n" +
                "    \"timeInfoList\": [\r\n" +
                "        {\r\n" +
                "            \"groupIdList\": [203],\r\n" +
                "            \"groupSort\": 1,\r\n" +
                "            \"timeRuleList\": [\r\n" +
                "                {\r\n" +
                "                    \"adjustmentType\": 0,\r\n" +
                "                    \"startTime\": \"00:00\",\r\n" +
                "                    \"endTime\": \"16:00\",\r\n" +
                "                    \"price\": 5\r\n" +
                "                },\r\n" +
                "                {\r\n" +
                "                    \"adjustmentType\": 1,\r\n" +
                "                    \"startTime\": \"16:00\",\r\n" +
                "                    \"endTime\": \"23:30\",\r\n" +
                "                    \"price\": 1\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"groupIdList\": [205],\r\n" +
                "            \"groupSort\": 4,\r\n" +
                "            \"timeRuleList\": [\r\n" +
                "                {\r\n" +
                "                    \"adjustmentType\": 0,\r\n" +
                "                    \"startTime\": \"00:00\",\r\n" +
                "                    \"endTime\": \"02:00\",\r\n" +
                "                    \"price\": 2\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"planType\": 1,\r\n" +
                "    \"distanceRuleList\": [\r\n" +
                "        {\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 1.1,\r\n" +
                "            \"vehicleType\": 0,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"vehicleType\": 0,\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0.1,\r\n" +
                "            \"priceCoefficient\": 0.1,\r\n" +
                "            \"fixedCommission\": 1.1,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 2,\r\n" +
                "            \"vehicleType\": 1,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"vehicleType\": 1,\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0.1,\r\n" +
                "            \"priceCoefficient\": 0.1,\r\n" +
                "            \"fixedCommission\": 2,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 3,\r\n" +
                "            \"vehicleType\": 2,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"vehicleType\": 2,\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0.1,\r\n" +
                "            \"priceCoefficient\": 0.1,\r\n" +
                "            \"fixedCommission\": 3,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 5,\r\n" +
                "            \"vehicleType\": 3,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"vehicleType\": 3,\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0.1,\r\n" +
                "            \"priceCoefficient\": 0.1,\r\n" +
                "            \"fixedCommission\": 5,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 4,\r\n" +
                "            \"vehicleType\": 4,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"vehicleType\": 4,\r\n" +
                "            \"distance\": 1,\r\n" +
                "            \"distanceCoefficient\": 0.1,\r\n" +
                "            \"priceCoefficient\": 0.1,\r\n" +
                "            \"fixedCommission\": 4,\r\n" +
                "            \"type\": 0\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"diffGroup\": 0,\r\n" +
                "    \"timeRuleDiffGroup\": 1,\r\n" +
                "    \"groupRuleList\": [],\r\n" +
                "    \"siteId\": \"3\"\r\n" +
                "}", ruleId);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
    }
}

