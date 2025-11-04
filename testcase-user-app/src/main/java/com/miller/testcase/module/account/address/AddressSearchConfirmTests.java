package com.miller.testcase.module.account.address;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Scenario(
        scenarioID = "01JXCMXQJ2RADAHRFG9W9H5AZX",
        scenarioName = "确认地址",
        author = "huyang@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("确认地址")
public class AddressSearchConfirmTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/user/address/search/confirm";
    // 请求方式
    String method = "GET";
    // 请求头
    String headers = "module/account/address/request/headersIos.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = null;
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 paras 参数
    String params = "module/account/address/request/AddressSearchConfirmReq.json";
    // 断言
    String assert1 = "module/account/address/response/AddressSearchConfirmResp.json";

    @DisplayName("正向流程")
    @Test
    void shouldLoginSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        // 给请求头添加数据，例如这里添加token
        requestHeaders.put("Authorization", TestCaseHelpful.login("19157824000", "123456a"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert1);
        String address = TestCaseHelpful.extractValue(responseBody, "$.result.address").toString();

        boolean addressAssert = address.equals("China, Jiangxi, 九江") || address.equals("Chaisang District, Jiujiang, Jiangxi, China");

        TestCaseHelpful.assertThat(addressAssert).isEqualTo(true);

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
    }
}
