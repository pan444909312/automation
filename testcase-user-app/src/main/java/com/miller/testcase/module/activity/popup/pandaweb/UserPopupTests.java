package com.miller.testcase.module.activity.popup.pandaweb;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JWSS1KB2H2X5WV4YW6GE43GC",
        scenarioName = "web-m站-新用户弹窗",
        author = "yancancan@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("web-m站-新用户弹窗")
public class UserPopupTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/app/user/new/user/popup";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/headers_mobile.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/activity/popup/pandaweb/request/success.json";
    // 断言
    String assert2 = "module/activity/popup/pandaweb/response/assert_some_fields.json";

    @DisplayName("web-m站-新用户弹窗")
    @Test
    void shouldReturnSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        // 给请求头添加数据，例如这里添加token
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
