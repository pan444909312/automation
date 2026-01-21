package com.miller.delivery.testcase.module.deliveryAdmin.capacityDemand;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Scenario(
        scenarioID = "01JPPNSFTGGR26GFJV80SX0X3Y",
        scenarioName = "司管后台-排班计划-获取时段运力详情",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("获取时段运力详情")
public class CapacityDemandTimeDetailTests {

    @DisplayName("获取时段运力详情")
    @Test
    void shouldGetCapacityDemandTimeDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当天日期
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 从数据库获取当天其中一条运力
        Map<String, Object> demandRecord = PandaTestDBHelpful.executeSelectOneSql(
                "select * from panda_test.hp_delivery_capacity_demand where demand_date=? order by id desc limit 1",
                todayDate);
        Long demandId = ((Number) demandRecord.get("id")).longValue();

        // 4) 获取时段运力详情
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityDemand/timeDetail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n    \"recId\": %d\r\n}", demandId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 5) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
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
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

