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
        scenarioID = "01KDQBS749SQYBF5S0PBTV5QCE",
        scenarioName = "司管_PF基础定价_修改全城PF定价_区分载具+时段+群组",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("修改全城PF定价_区分载具+时段+群组")
public class PfPriceRuleCityUpdateComplexTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("修改全城PF定价_区分载具+时段+群组")
    @Test
    void shouldUpdatePfPriceRuleCityComplex() {
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

        // 4) 提取ruleId
        String ruleId = TestCaseHelpful.extractValue(responseBody1, "$.data.list[0].ruleId");

        // 5) 修改全城定价，区分群组+区分距离+不时段加价
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/pfBase/updateBasePriceRule";
        String body2 = String.format("{\r\n" +
                "    \"areaIdList\": [],\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"basePriceId\": \"%s\",\r\n" +
                "    \"diffTransportation\": 1,\r\n" +
                "    \"periodType\": 1,\r\n" +
                "    \"timeInfoList\": [\r\n" +
                "        {\r\n" +
                "            \"groupIdList\": [1394],\r\n" +
                "            \"groupSort\": 1,\r\n" +
                "            \"timeRuleList\": [\r\n" +
                "                {\r\n" +
                "                    \"startTime\": \"09:00\",\r\n" +
                "                    \"endTime\": \"20:00\",\r\n" +
                "                    \"adjustmentType\": 0,\r\n" +
                "                    \"price\": 1\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"planType\": 1,\r\n" +
                "    \"distanceRuleList\": [\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 1,\r\n" +
                "            \"vehicleType\": 0,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0.00001,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 1,\r\n" +
                "            \"vehicleType\": 0,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 2,\r\n" +
                "            \"vehicleType\": 1,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0.00001,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 2,\r\n" +
                "            \"vehicleType\": 1,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 3,\r\n" +
                "            \"vehicleType\": 2,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0.00001,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 3,\r\n" +
                "            \"vehicleType\": 2,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 5,\r\n" +
                "            \"vehicleType\": 3,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0.00001,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 5,\r\n" +
                "            \"vehicleType\": 3,\r\n" +
                "            \"type\": 0\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 4,\r\n" +
                "            \"vehicleType\": 4,\r\n" +
                "            \"type\": 1\r\n" +
                "        },\r\n" +
                "        {\r\n" +
                "            \"distance\": 0.00001,\r\n" +
                "            \"distanceCoefficient\": 0.00001,\r\n" +
                "            \"priceCoefficient\": 0,\r\n" +
                "            \"fixedCommission\": 4,\r\n" +
                "            \"vehicleType\": 4,\r\n" +
                "            \"type\": 0\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"diffGroup\": 0,\r\n" +
                "    \"timeRuleDiffGroup\": 1,\r\n" +
                "    \"groupRuleList\": [],\r\n" +
                "    \"siteId\": \"0\"\r\n" +
                "}", ruleId);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
    }
}

