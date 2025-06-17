package com.miller.testcase.module.index;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * udesk客服信息
 */
@Scenario(
        scenarioID = "01JWWF717DPCCRW2Y1KVH2EK9R",
        scenarioName = "获取首页凑单信息",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("首页凑单")
public class GetDynamicDiscountTests {
    String uri = TestcaseConfig.HpfHost + "/index/getDynamicDiscount";
    String method = "POST";
    String headers = "module/headersHPF.json";
    String body = "module/index/request/getDynamicDiscount.json";
    String assertFull = "module/index/response/assert_getDynamicDiscount.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccessfully() {
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("authorization", TestCaseHelpful.login("17700004444", "123456"));
        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);
//        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);// 使用 JsonPath 方式
//        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result").isNotNull();

        // 方式一： 全匹配， 忽略部分动态字段值。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFull);
        TestCaseHelpful.assertThatJson(responseBody).isEqualTo(expectedStr);
    }


}
