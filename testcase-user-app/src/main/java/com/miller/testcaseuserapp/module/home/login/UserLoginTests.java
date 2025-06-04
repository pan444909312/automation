package com.miller.testcaseuserapp.module.home.login;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
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
        scenarioName = "用户_登录",
        author = "shandongdong@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 5, manualTestTime = 2)
@DisplayName("用户_登录")
public class UserLoginTests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HOST + "/api/user/combine/login";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从resources目录下读取文件。下面的代码表示从resource的module/home/login/request/headers.json读取文件内容作为接口请求头
    String headers = "module/home/login/request/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。
    String body = "module/home/login/request/should_success.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 paras 参数
    String params = null;
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/module/home/login/response/assert_full_field.json 读取文件内容作为断言
    String assert1 = "module/home/login/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldLoginSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式一： 全匹配， 忽略部分动态字段值。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert1);
        TestCaseHelpful.assertThatJson(responseBody).isEqualTo(expectedStr);
    }
}
