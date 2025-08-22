package com.miller.pandafresh.testcase.module.address.getaddresses;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/21 10:45:29
 */
//用于组合场景
@TestFramework
@DisplayName("步骤：融合订单结算页获取收货地址：pf配送区域内")
public class GetHpfAddresses {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HpfHost + "/address/hpf/getAddresses";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/gethpfaddresses/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/gethpfaddresses/request/body.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/gethpfaddresses/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录老用户
        requestHeaders.replace("userid","1398661332");
        requestHeaders.put("authorization",TestCaseHelpful.login("17700004444","123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);


        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addId").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addressId").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addConnName").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addConnTel").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addProvince").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addCity").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addStreet").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addLocation").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addPostcode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addLongitude").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addLatitude").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addCountry").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].countryCode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addTag").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].buildingName").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].deliverableAction").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliverableAddressList.[0].addConnTelMask").isNotNull();
        //获取第一个可用地址
        TestcaseConfig.addressId= TestCaseHelpful.extractValue(responseBody,"$.result.deliverableAddressList.[0].addId").toString();

        System.out.println("地址"+TestcaseConfig.addressId);
    }
} 