package com.miller.testcase.module.home.user;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JWSS1KB1PRR97ZR8GMEP2ZWM",
        scenarioName = "用户_登陆后获取用户信息",
        author = "yancancan@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("用户_登陆后获取用户信息")
public class UserInfoTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST + "/api/user/userInfo";
    // 请求方式
    String method = "GET";
    // 请求头
    String headers = "module/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = null;
    // 断言
    String assert2 = "module/home/user/info/response/assert_some_fields.json";

    @DisplayName("用户_登陆后获取用户信息")
    @Test
    void shouldReturnSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        // 给请求头添加数据，例如这里添加token
        requestHeaders.put("Authorization", TestCaseHelpful.login("17700000077", "123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
}
