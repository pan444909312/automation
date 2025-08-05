package com.miller.testcase.module.coverage.order.bought;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


@Scenario(scenarioID = "01K1T2TVGM3CG4Z6AYZBY2TVEE",
        scenarioName = "订单列表获取买过店铺-用户有买过的店",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/app/user/bought/simple/shopList")
public class UserBoughtSimpleShopListSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/app/user/bought/simple/shopList";

    @DisplayName("订单列表获取买过店铺-用户有买过的店")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13960000003", "123456"));
        headers.put("latitude", 27.99067);
        headers.put("longitude", 120.69639);
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/order/bought/request/UserBoughtSimpleShopListReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/order/bought/response/UserBoughtSimpleShopListResp02.json");

        List<Object> list = TestCaseHelpful.extractValue(responseBody, "$.result.shopSimpleVOList");
        TestCaseHelpful.assertThat(list).isNotNull();
        TestCaseHelpful.assertThat(list.size()).isNotEqualTo(0);


        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
