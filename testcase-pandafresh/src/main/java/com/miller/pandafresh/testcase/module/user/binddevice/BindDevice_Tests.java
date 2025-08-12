package com.miller.pandafresh.testcase.module.user.binddevice;

import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * bindDevice
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/06/17 14:32:32
 */
@Scenario(
        scenarioID = "01JXY9DQM9NB3MRS7C5G1AVZ0Y", // 自动生成，不要修改
        scenarioName = "设备绑定",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("设备绑定")
public class BindDevice_Tests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HOST + "/user/bindDevice";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/binddevice/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/binddevice/request/should_success.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assert1 = "module/binddevice/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("authorization",TestCaseHelpful.loginPF("18968046019","888888"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data").isNotNull();

        String sql = "SELECT * FROM device_user t where t.user_id=249222 and t.registration_id='190e35f7e15ac344960' and t.device_number='8d221af5-d4d9-4837-9d5b-53271542a59e' and t.is_del=0  limit 1";
        // 查询记录
        Map<String, Object> selectSql = FreshTestDBHelpful.executeSelectOneSql(sql);
        TestCaseHelpful.assertThatJson(selectSql).isNotNull();
    }
} 