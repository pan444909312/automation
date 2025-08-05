package com.miller.testcase.module.home.shop;
import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JX2CJM4Z0AZB8H9P7NV0213Y",
        scenarioName = "进入店铺获取店铺信息（英文版）",
        author = "yaoqianhu@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 10, manualTestTime = 10)
@DisplayName("进入店铺获取店铺信息（英文版）")
public class GetShopInfo_EN {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/user/v1/shop/info?shopId=930937488";

    public GetShopInfo_EN() {
        super();
    }

    // 请求方式
    String method = "GET";
    // 请求头
    String headers = "module/headers_EN.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = null;
    // 断言
    String assert2 = "module/home/shop/response/getShopInfo_ENResp.json";

    @DisplayName("正向流程")
    @Test
    public void getMenuListSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式一： 全匹配， 忽略部分动态字段值。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
    }
}
