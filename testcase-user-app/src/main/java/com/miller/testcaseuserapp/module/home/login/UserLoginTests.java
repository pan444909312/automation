package com.miller.testcaseuserapp.module.home.login;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_EXTRA_FIELDS;

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
        developmentTime = 20, maintenanceTime = 10, manualTestTime = 1)
@DisplayName("用户-登录")
public class UserLoginTests {
    /**
     * 定义登录接口请求的 path
     */
    private static final String uri = TestcaseConfig.HOST + "/api/user/combine/login";

    @DisplayName("正常流程_登录")
    @Test
    public void shouldLoginSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var headers = TestCaseUtils.getHeaders("module/headers.json");

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseUtils.getJsonRequestBody("module/home/login/request/login_success_request_body.json");

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, requestBody, null);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        var expectedStr = TestCaseUtils.getFileContent("module/home/login/response/login_success_assert.json");
        // 方式一：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        TestCaseUtils.assertThatJson(responseBody).when(IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        // 方式二： 全匹配， 排除部分字段动态字段匹配。固定写法，不需要修改
        expectedStr = TestCaseUtils.getFileContent("module/home/login/response/login_success_assert_exclude_field.json");
        TestCaseUtils.assertThatJson(responseBody).isEqualTo(expectedStr);
        // 方式三：精确断言.比如只想校验某一个字段
        TestCaseUtils.assertThatJson(responseBody).inPath("$.resultCode").isEqualTo(1000); // 使用 JsonPath 方式
        TestCaseUtils.assertThatJson(responseBody).node("resultCode").isEqualTo(1000); // 使用 node 方式
        TestCaseUtils.assertThatJson(responseBody).inPath("$.result.userId").isEqualTo(1398708422); // 使用 JsonPath 方式
        TestCaseUtils.assertThatJson(responseBody).node("result.userId").isEqualTo(1398708422); // 使用 node 方式
        TestCaseUtils.assertThatJson(responseBody).node("result.userId").isNumber();


    }
}
