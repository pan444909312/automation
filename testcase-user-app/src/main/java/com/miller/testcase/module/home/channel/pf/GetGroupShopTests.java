package com.miller.testcase.module.home.channel.pf;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JWSS1KB2H2X5WV4YW6GE43GE",
        scenarioName = "PF品类频道-获取商家列表",
        author = "yancancan@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("PF品类频道-获取商家列表")
public class GetGroupShopTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/app/user/channel/group/shop";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/headers.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/home/module/channel/pfshop/request/success.json";
    // 断言
    String assert2 = "module/home/module/channel/pfshop/response/assert_some_fields.json";
    @DisplayName("PF品类频道-获取页面信息")
    @Test
    void shouldReturnSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("authorization",TestCaseHelpful.login("17700000077","123456"));
        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
//        HashMap<String,Object> params = new HashMap<>();
//        params.put("moduleId",2411);
//        params.put("pageNo",1);
//        params.put("pageSize",10);
//        params.put("cityName","%E4%B9%9D%E6%B1%9F%E5%B8%82");
//        params.put("sortType","COMPOSITE");
//        params.put("zipCode",null);
        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：部份匹配，
        String expectedStr = TestCaseHelpful.getFileContent(assert2);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
