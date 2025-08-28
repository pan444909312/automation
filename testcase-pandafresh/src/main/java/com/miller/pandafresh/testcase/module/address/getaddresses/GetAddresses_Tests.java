package com.miller.pandafresh.testcase.module.address.getaddresses;

import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * getAddresses
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/06/19 14:52:28
 */
@Scenario(
        scenarioID = "01JY3FBNEBHD6V440Z1QV8GDQP", // 自动生成，不要修改
        scenarioName = "获取pf收货地址",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("获取pf收货地址")
public class GetAddresses_Tests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HOST + "/address/getAddresses";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/getaddresses/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/getaddresses/request/body.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/getaddresses/response/assert_full_field.json";

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
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].location").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].postcode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].longitude").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].countryCode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].contact").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].contactTelephone").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].addressCity").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].countryCode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].addressProvince").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].addressDistrict").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].addressStreet").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data.addressDTOList.[0].addressCity").isNotNull();

    }
} 