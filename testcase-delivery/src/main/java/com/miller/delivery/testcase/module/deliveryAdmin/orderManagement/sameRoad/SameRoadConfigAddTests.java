package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.sameRoad;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Scenario(
        scenarioID = "01JPSSAYZ857VV8KVDJ707DWR9",
        scenarioName = "司管后台-订单管理-顺路度设置-新增",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("顺路度设置新增")
public class SameRoadConfigAddTests {

    @DisplayName("新增顺路度设置")
    @Test
    void shouldAddSameRoadConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增顺路度设置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/same/road/config/addAndUpdate";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"configName\": \"自动化测试\",\r\n" +
                "    \"scopeType\": 0,\r\n" +
                "    \"feeType\": 0,\r\n" +
                "    \"diffGroup\": 0,\r\n" +
                "    \"discountOpenStatus\": [\r\n" +
                "        0\r\n" +
                "    ],\r\n" +
                "    \"activityType\": 0,\r\n" +
                "    \"distanceLimit\": 0,\r\n" +
                "    \"driverOrderDistanceConfig\": [],\r\n" +
                "    \"ruleList\": [\r\n" +
                "        {\r\n" +
                "            \"smoothnessCoefficient\": \"2\",\r\n" +
                "            \"discountFactor\": \"2\"\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"mergeDiscountFactor\": 2,\r\n" +
                "    \"orderSceneConfig\": [\r\n" +
                "        0\r\n" +
                "    ],\r\n" +
                "    \"businessType\": 0\r\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 4) 从数据库获取顺路度配置ID(可用于后续测试)
        PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_same_road_config where city='杭州市' and is_del=0 order by id desc limit 1");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

