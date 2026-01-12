package com.miller.delivery.testcase.module.deliveryAdmin.delayTime;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01JPSCHNCNPGPSDY7F53BK5YGH",
        scenarioName = "司管后台-订单管理-等餐报备-查看/编辑取餐报备规则下骑手",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("编辑取餐报备规则下骑手")
public class MealReportRuleDriverEditTests {

    @DisplayName("查看并编辑规则下群组")
    @Test
    void shouldViewAndEditRuleDriverGroup() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 查看规则下骑手群组
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverGroup/getDriverGroupAllList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = "{\"cityList\":[\"杭州市\"],\"groupName\":\"\",\"groupId\":\"\"}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 3) 编辑规则下群组
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/delay/time/add";
        String body2 = "{\"configId\":62,\"configName\":\"自动化测试规则\",\"configType\":1,\"status\":1,\"arriveShopTime\":1,\"reportNumber\":30,\"delayTime\":10,\"groupInfoList\":[{\"groupId\":886,\"groupName\":\"自动化群组\"}],\"shopInfoList\":null,\"idList\":[886],\"city\":\"杭州市\"}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
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

