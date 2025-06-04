package com.miller.testcase.module.goods;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 商详页-还想买
 */
@Scenario(
        scenarioID = "01JWG5FM4FD7DPYQR6JXRGVR8H",
        scenarioName = "商品详情-还想买",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("商品详情-还想买")
public class RelateStillTests {
    String uri = TestcaseConfig.HOST + "/goods/relate/still";
    String method = "POST";
    String headers = "module/headersPF.json";
    String body = "module/goods/request/relateStill.json";
    String assert1 = "module/goods/response/assert_relate_still.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccessfully() {
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1);// 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data").isNotNull();
    }
}
