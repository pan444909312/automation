package com.miller.pandafresh.testcase.module.b2b.order.paymentpattern;

import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.PandaTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * paymentPattern
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/12/08 14:38:49
 */
@Scenario(
        scenarioID = "01KBYAYA0M17EAXJKFTGFSX3MV", // 自动生成，不要修改
        scenarioName = "B2B订单-支付方式获取",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("B2B订单-支付方式获取")
public class PaymentPattern_Tests {

    @BeforeAll
    static void beforeAll(){
        //查询未支付订单
        String sql = "SELECT * FROM b2b_order o WHERE o.order_version>=1 and o.pay_way_type=1 and o.pay_status=0 and o.order_status=1 and o.custom_id=8 limit 1;";
        TestcaseConfig.b2bpayNowOrderSn = FreshTestDBHelpful.executeSelectOneSql(sql).get("order_sn").toString();
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
        String uri = TestcaseConfig.H5HOST + "/api/app/user/payment/v2/checkOut/paymentPattern";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/b2b/order/paymentpattern/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/b2b/order/paymentpattern/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/b2b/order/paymentpattern/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录用户
        requestHeaders.put("authorization", TestCaseHelpful.loginB2B("17700004444","888888"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.pd.orderSn",TestcaseConfig.b2bpayNowOrderSn);

        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        TestcaseConfig.floatingRate = TestCaseHelpful.extractValue(responseBody,"$.data.patternDTOList.[0].floatingRate");
        TestcaseConfig.floatingAmount = TestCaseHelpful.extractValue(responseBody,"$.data.patternDTOList.[0].floatingAmount");
        TestcaseConfig.floatingType = TestCaseHelpful.extractValue(responseBody,"$.data.patternDTOList.[0].floatingType");
        TestcaseConfig.payChannel = TestCaseHelpful.extractValue(responseBody,"$.data.patternDTOList.[0].payChannel");
        TestcaseConfig.channelRecordId = TestCaseHelpful.extractValue(responseBody,"$.data.patternDTOList.[0].channelRecordId");

    }
} 