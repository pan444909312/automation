package com.miller.testcase.module.home.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K21XR3Y6FA315J1XXGENNBT5",
        scenarioName = "搜索无结果页推荐列表-店铺名称展示成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/v1/recommendList")
public class SearchRecommendListHasShopName {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v1/recommendList";

    @DisplayName("搜索无结果页推荐列表-店铺名称展示成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchRecommendListReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchRecommendListResp.json");

        Object shopName = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[0].shopName");
        TestCaseHelpful.assertThat(shopName).isNotNull();

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
    }
}
