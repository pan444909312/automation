package com.miller.testcase.module.home.channel.pf;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

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
    String headers = "module/home/module/channel/pfshop/request/heades.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/home/module/channel/pfshop/request/success.json";
    // 断言
    String assert2 = "module/home/module/channel/pfshop/response/assert_some_fields.json";

    @DisplayName("PF品类频道-获取页面信息")
    @Test
    void shouldReturnSuccessfully() {
        // 步骤1-3: 请求头、请求体、发起请求
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        requestHeaders.put("authorization", TestCaseHelpful.login("17700000077", "123456"));
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        var expectedStr = TestCaseHelpful.getFileContent(assert2);

        // 提取断言里的 filterGroup 和 shopList
        var expectedFilters = TestCaseHelpful.extractValue(expectedStr, "$.result.filterGroup");
        var expectedShopList = TestCaseHelpful.extractValue(expectedStr, "$.result.shopList[0]");
        ArrayList<Object>expectedShopListArr =new ArrayList<>();
        expectedShopListArr.add(expectedShopList);
        // 校验 filterGroup
        TestCaseHelpful.assertThatJson(responseBody)
                .when(Option.IGNORING_EXTRA_ARRAY_ITEMS, Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_ARRAY_ORDER)
                .inPath("$.result.filterGroup")
                .isEqualTo(expectedFilters);

        // 打印期望和实际的 shopList 值以便调试
        System.out.println("Expected ShopList: " + expectedShopList);
        var actualShopList = TestCaseHelpful.extractValue(responseBody, "$.result.shopList");
        System.out.println("Actual ShopList: " + Optional.ofNullable(actualShopList));

        // 校验 shopList
        TestCaseHelpful.assertThatJson(responseBody)
                .when(Option.IGNORING_EXTRA_ARRAY_ITEMS, Option.IGNORING_EXTRA_FIELDS, Option.IGNORING_ARRAY_ORDER)
                .inPath("$.result.shopList")
                .isEqualTo(expectedShopListArr);
    }
}