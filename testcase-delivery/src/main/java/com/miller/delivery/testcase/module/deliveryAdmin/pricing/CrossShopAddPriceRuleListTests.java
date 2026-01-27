package com.miller.delivery.testcase.module.deliveryAdmin.pricing;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管_跨店加价_指定城市+状态查询
 *
 * Apifox: docs/d-apifox/司管_跨店加价_指定城市+状态查询.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KEB9W0PX5AM3DCZFVH583CWT",
        scenarioName = "司管_跨店加价_指定城市+状态查询",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("司管_跨店加价_指定城市+状态查询")
public class CrossShopAddPriceRuleListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("跨店加价规则列表查询-默认&指定条件")
    @Test
    void shouldQueryCrossShopRuleList() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 杭州市默认查询
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/price/crossShopRuleList";
        String body1 = "{\"city\":null,\"status\":null,\"ruleName\":null,\"ruleId\":null,\"pageNo\":1,\"pageSize\":10}";
        var responseBody1 = TestCaseHelpful.sendRequest("POST", uri, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1).node("message").isEqualTo("成功");

        // 3) 杭州市指定条件查询
        String body2 = "{\"city\":\"杭州市\",\"status\":1,\"ruleName\":null,\"ruleId\":null,\"pageNo\":1,\"pageSize\":10}";
        var responseBody2 = TestCaseHelpful.sendRequest("POST", uri, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2).node("message").isEqualTo("成功");
    }
}

