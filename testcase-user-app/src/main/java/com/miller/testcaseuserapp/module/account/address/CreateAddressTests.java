package com.miller.testcaseuserapp.module.account.address;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/29 15:19:42
 */
@Scenario(
        scenarioID = "01JWDE3J7GDY6GC7JR1GPW5NFW",
        scenarioName = "用户_创建地址",
        author = "shandongdong@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("用户_创建地址")
public class CreateAddressTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST + "/api/app/user/v1/address/edit";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/account/address/request/add_address.json";
    // 断言
    String assert1 = "module/account/address/response/add_address_assert.json";

    // 存储 token
    String token = null;

    @BeforeAll
    void beforeAll() {
        token = TestCaseHelpful.login("15606690056", "12345678");
    }

    @DisplayName("正向流程")
    @Test
    void shouldLoginSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("Authorization", token);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式一： 全匹配， 排除部分字段动态字段匹配。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert1);
        TestCaseHelpful.assertThatJson(responseBody).isEqualTo(expectedStr);
    }
}
