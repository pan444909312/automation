package com.miller.delivery.testcase.module.deliveryAdmin.blacklist;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-黑名单-新增拉黑关系失败
 */
@Scenario(
        scenarioID = "01KDSYPREP5172SNH4FGNV1BF3",
        scenarioName = "骑手列表-黑名单-新增拉黑关系失败",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新增拉黑关系")
public class BlacklistAddTests {

    @DisplayName("新增拉黑关系失败")
    @Test
    void shouldFailToAddBlacklist() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增拉黑关系失败
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/blacklist/batchAdd";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"dataType\":2,\"reason\":\"自动化测试异常\",\"driverIdList\":[\"111\"],\"userIdList\":[\"111\"]}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言返回错误
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(9999);
        String message = TestCaseHelpful.extractValue(responseBody, "$.message").toString();
        assert message.contains("骑手ID不存在：111") : "应该返回骑手ID不存在：111";
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

