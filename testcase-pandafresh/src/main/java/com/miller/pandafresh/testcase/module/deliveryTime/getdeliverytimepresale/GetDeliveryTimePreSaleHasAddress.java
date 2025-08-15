package com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytimepresale;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * getDeliveryTimePreSaleNoAddress
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/15 16:16:33
 */
//用于组合场景
@TestFramework
@DisplayName("获取配送时间段:预售单-有地址")
public class GetDeliveryTimePreSaleHasAddress {

    @BeforeAll
    static void beforeAll(){
        // 所有 @Test 方法执行之前会执行  @BeforeAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行前置的操作，比如: SQL 初始化用例的前置条件
    }
    @AfterAll
    static void afterAll(){
        // 所有 @Test 方法执行之后会执行  @@AfterAll 注解的方法, 这里的代码当前测试类期间只会执行一次
        // 你可以在这里执行后置的操作，比如: 销毁测试数据、还原数据库、清理环境等
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.HpfHost + "/deliveryTime/getDeliveryTime";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/deliveryTime/getdeliverytimepresale/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/deliveryTime/getdeliverytimepresale/request/bodyHasAddress.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/deliveryTime/getdeliverytimepresale/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录老用户
        requestHeaders.replace("userid","1398661332");
        requestHeaders.put("authorization",TestCaseHelpful.login("17700004444","123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.addressId",TestcaseConfig.addressId);

        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList.[0].date").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList.[0].realDate").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList.[0].deliveryTime").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList.[0].deliveryTimeId").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList.[0].timeFulls.[0].timeSpace").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList.[0].timeFulls.[0].deliveryTime").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.deliveryTimeList.[0].timeFulls.[0].deliveryType").isNotNull();

        //获取配送时间
        TestcaseConfig.deliveryDate = TestCaseHelpful.extractValue(responseBody,"$.result.deliveryTimeList.[0].date").toString();
        TestcaseConfig.deliveryTime = TestCaseHelpful.extractValue(responseBody,"$.result.deliveryTimeList.[0].deliveryTime.[0]").toString();
        TestcaseConfig.deliveryTimeId = TestCaseHelpful.extractValue(responseBody,"$.result.deliveryTimeList.[0].deliveryTimeId").toString();

    }
} 