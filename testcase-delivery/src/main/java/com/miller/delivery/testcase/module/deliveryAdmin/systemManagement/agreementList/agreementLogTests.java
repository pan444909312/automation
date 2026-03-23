package com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.agreementList;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 协议管理-列表
 *
 * @author 江彪
 * @version 2.0
 * @since 2026/03/20
 */
@Scenario(
        scenarioID = "01KM4KSMN7EF89V0RR8NRDVJ8B",
        scenarioName = "协议管理-协议日志",
        author = "jiangbiao@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("协议管理-协议日志")
public class agreementLogTests {

    @DisplayName("协议管理-协议日志")
    @Test
    void shouldGetAgreementListTestsDefaultSearch() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 协议管理-协议日志
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/system/agreement/agreementLog";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"agreementNo\":\"LAW425270036946488992\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");

        // 验证响应中包含"修改协议"
        String responseText = responseBody.toString();
        assert responseText.contains("修改协议") : "响应中未包含修改协议";
    }


    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

