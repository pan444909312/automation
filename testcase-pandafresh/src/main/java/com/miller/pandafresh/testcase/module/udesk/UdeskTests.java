package com.miller.pandafresh.testcase.module.udesk;

import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * udesk客服信息
 */
@Scenario(
        scenarioID = "01JWGA2A7BP11SZKBMBEGSGAW4",
        scenarioName = "udesk客服信息",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("udesk客服信息")
public class UdeskTests {
    String uri = TestcaseConfig.HpfHost + "/udesk/getConfigUrl";
    String method = "POST";
    String headers = "module/headersHP.json";
    String body = "module/udesk/request/udesk.json";
    String assert1 = "module/udesk/response/assert_udesk.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccessfully() {
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);// 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result").isNotNull();
    }
}
