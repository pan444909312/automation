package com.miller.testcaseuserapp.module.home.login;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2025/5/26 20:19:42
 */
@Scenario(
        scenarioID = "01JW68KNTBJSEZ0GPXQ9AF6XFN",
        scenarioName = "用户-登录",
        author = "shandongdong@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 5, manualTestTime = 1)
@DisplayName("用户-登录")
public class UserLoginTests {

    // 接口请求的 path
    private String uri = TestcaseConfig.HOST + "/api/user/combine/login";
    // 请求方式
    private String method = "POST";
    // 请求头
    private String headers = "module/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    private String body = "module/home/login/request/success.json";
    // 断言
    private String assert1 = "module/home/login/response/assert_full_field.json";
    private String assert2 = "module/home/login/response/assert_some_fields.json";

    @DisplayName("正常流程_登录")
    @Test
    public void shouldLoginSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式一： 全匹配， 排除部分字段动态字段匹配。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert1);
        TestCaseHelpful.assertThatJson(responseBody).isEqualTo(expectedStr);

        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

        // 方式三：精确断言.比如只想校验某一个字段
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.resultCode").isEqualTo(1000); // 写法一: 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000); // 写法二: 使用 node 方式
    }
}
