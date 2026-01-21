package com.miller.delivery.testcase.module.dispatch.weather;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 获取天气详情
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPEMQAMAWQXPVMN49WYX62X", // 自动生成，不要修改
        scenarioName = "调度系统-天气-获取天气详情",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("调度系统-天气-获取天气详情")
public class WeatherInfoTests {
    // TestcaseConfig.HOST_ERP 是接口的请求域名（调度系统使用ERP域名）。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/city/weather/info";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/dispatch/weather/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 先登录获取token
        String token = erpLogin();
        
        // 步骤2: 设置请求头。基本固定写法，不需要修改
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/weather/request/headers.json");
        // 设置请求头参数
        headers.put("token", token);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");
         

        // 步骤3: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/weather/request/body.json");
        // 更新请求体中的动态参数
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityName", "杭州市");

        // 步骤4: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        // 步骤5: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        // var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        // TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        
        // 验证关键字段
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 司管后台登录并返回token
     *
     * @return token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/erp/auth/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/erp/auth/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.password", "4f2142904392cbef6974ad0260caeb33");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.userName", "ding17058431144045523");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }
}

