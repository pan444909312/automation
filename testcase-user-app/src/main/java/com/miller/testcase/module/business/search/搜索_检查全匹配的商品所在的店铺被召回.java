package com.miller.testcase.module.business.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K5EC96S3Y2BDM8SDPK31WFJP",
        scenarioName = "搜索_检查全匹配的商品所在的店铺被召回",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/v2/search")
public class 搜索_检查全匹配的商品所在的店铺被召回 {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";

    private String shopId = "151015504";
    private String productId = "82552232";

    @DisplayName("搜索_检查全匹配的商品所在的店铺被召回")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");

        // 沈阳经纬度
        headers.put("latitude", 41.80478);
        headers.put("longitude", 123.43297);
        // 给请求头添加数据，例如这里添加token
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchBaseReq.json");
        String newRequestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.keywords", "烤茄子");

        String responseBody = TestCaseHelpful.searchGetShopVOByShopId(shopId, newRequestBody, headers, null, null, null, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchBaseResp.json");

        Object result1 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[?(@.shopId==" + shopId + ")]");
        Object result2 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[?(@.shopId==" + shopId + ")].shopName");
        Object result3 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[*].productVOList[?(@.productId==" + productId + ")].productName");


        TestCaseHelpful.assertThat(result1.toString()).isNotEqualTo("[]");
        TestCaseHelpful.assertThat(result2.toString().contains("烤茄子")).isEqualTo(false);
        TestCaseHelpful.assertThat(result3.toString().contains("烤茄子")).isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
