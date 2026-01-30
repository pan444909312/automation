package com.miller.delivery.testcase.module.deliveryApp.auth;

import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 骑手登录
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01KE5YBY6PRQZSH4KX5120HYRG", // 自动生成，不要修改
        scenarioName = "骑手登录",
        author = "panjuxiang@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手登录")
public class DriverLoginTests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = "https://app-deliverytest.hungrypanda.cn/api/delivery/app/auth/login";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/delivery/auth/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/delivery/auth/request/headers.json");
        // 设置请求头参数
        headers.put("platform", "");

        headers.put("Content-Type", "application/json");

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/delivery/auth/request/body.json");
        // 更新请求体中的动态参数
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.areaCode", "86");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.password", "2c9341ca4cf3d87b9e4eb905d6a3ec45");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.account", "13300010015");

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
//        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        
        // 验证关键字段
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("result.accessToken").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).node("result.userId").isNotNull();
    }
}

