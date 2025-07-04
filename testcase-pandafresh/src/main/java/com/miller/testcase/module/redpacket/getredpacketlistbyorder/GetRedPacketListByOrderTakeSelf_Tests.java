package com.miller.testcase.module.redpacket.getredpacketlistbyorder;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * getRedPacketListByOrder
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/06/19 13:51:47
 */
@Scenario(
        scenarioID = "01JY3BWJDW3ZMGRWXMJ11QMWPC", // 自动生成，不要修改
        scenarioName = "获取订单可用红包：自提单",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("获取订单可用红包：自提单")
public class GetRedPacketListByOrderTakeSelf_Tests {
    // TestcaseConfig.HOST 是接口的请求域名。 后面的 + "是接口的请求路径"
    String uri = TestcaseConfig.HpfHost + "/redPacket/getRedPacketListByOrder";
    // 接口请求方式。如： GET、POST、PUT、DELETE
    String method = "POST";
    // 请求头。默认从 resources 目录下读取文件。
    String headers = "module/getredpacketlistbyorder/request/headers.json";
    // 请求参数。如果没有传 null 即可（params = null）。比如 POST 请求通常没有 params 参数
    String params = null;
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求可能没有请求体。作用同请求头
    String body = "module/getredpacketlistbyorder/request/body.json";
    // 断言。默认从resources目录下读取文件。下面的代码表示从 resource 的 module/xxx/response/assert_full_field.json 读取文件内容作为断言
    String assertFullField = "module/getredpacketlistbyorder/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        //登录用户(请求头包含userid，需修改)
        requestHeaders.put("userid",249222);
        requestHeaders.put("authorization",TestCaseHelpful.login("18968046019","123456"));

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        //订单金额
        requestBody = JSONUtils.updateJsonValue(requestBody,"goodPriceCount",10);
        //配送方式-配送
        requestBody = JSONUtils.updateJsonValue(requestBody,"deliveryMethod",2);
        //红包类型-红包
        requestBody = JSONUtils.updateJsonValue(requestBody,"redPacketCustomerType",0);

        // 如果请求有参数，则设置参数。基本固定写法，不需要修改
        var requestParams = TestCaseHelpful.getJsonRequestParams(params);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, requestParams, requestHeaders, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.usableRedPacket").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.disableRedPacket").isNotNull();

    }
} 