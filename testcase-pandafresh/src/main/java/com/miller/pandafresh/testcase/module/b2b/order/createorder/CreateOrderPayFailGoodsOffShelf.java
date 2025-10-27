package com.miller.pandafresh.testcase.module.b2b.order.createorder;

import com.alibaba.fastjson.JSONObject;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.JSONUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * createOrder
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/27 17:34:03
 */
@TestFramework
@DisplayName("步骤：b2b创建订单失败：商品下架")
public class CreateOrderPayFailGoodsOffShelf {

    @BeforeAll
    static void beforeAll(){
        //下架商品
        String sql = "update goods g set g.status=0 where g.goods_id=149862";
        FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }
    @AfterAll
    static void afterAll(){
        //上架商品
        String sql = "update goods g set g.status=1 where g.goods_id=149862";
        FreshTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
        String uri = TestcaseConfig.H5HOST + "/api/b2b/order/createOrder";
        // 接口请求方式。如： GET、POST、PUT、DELETE
        String method = "POST";
        // 请求头。默认从 resources 目录下读取文件。
        String headers = "module/b2b/order/createorder/request/headers.json";
        // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
        String params = null;
        // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
        String body = "module/b2b/order/createorder/request/body.json";
        // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
        String assertFullField = "module/b2b/order/createorder/response/assert_full_field.json";

        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录用户
        requestHeaders.put("authorization",TestCaseHelpful.loginB2B("17700004444","888888"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.pd.deliveryDate",TestcaseConfig.b2bDeliveryDate);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.pd.deliveryTime",TestcaseConfig.b2bDeliveryTime);
        requestBody = JSONUtils.updateJsonValueByPath(requestBody,"$.pd.addressId",TestcaseConfig.b2bAddressId);
        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assertFullField);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(505);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.message").isEqualTo("结算信息存在变更，请前往购物车确认(505)");
    }
} 