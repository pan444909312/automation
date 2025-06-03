package com.miller.testcaseuserapp.module.home.shop;
import com.miller.service.framework.annotation.Scenario;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JWG8TJANDRCAGQS0NVB0PYKY",
        scenarioName = "进入店铺获取店铺信息",
        author = "yaoqianhu@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("进入店铺获取店铺信息")
public class ShopInfo {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST + "/api/app/user/shop/base/info";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/home/shop/request/get_shopinfo.json";
    // 断言
    String assert2 = "module/home/shop/response/assert_shopinfo.json";

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
       // 方式二：全匹配，断言 实际结果 包含 预期结果,排除掉额外字段。固定写法，不需要修改
        var expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
    }
}
