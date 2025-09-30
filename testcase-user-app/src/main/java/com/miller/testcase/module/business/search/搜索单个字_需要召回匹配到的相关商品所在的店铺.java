package com.miller.testcase.module.business.search;

import com.alibaba.fastjson.JSONPath;
import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K5EBWH913TC2FGJVHGJVQ2R7",
        scenarioName = "搜索单个字_需要召回匹配到的相关商品所在的店铺",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/v2/search")
public class 搜索单个字_需要召回匹配到的相关商品所在的店铺 {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";

    private String shopId = "538448084";
    private String productId = "82551552";

    @DisplayName("搜索单个字_需要召回匹配到的相关商品所在的店铺")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");

        // 沈阳经纬度
        headers.put("latitude", 41.80478);
        headers.put("longitude", 123.43297);
        // 给请求头添加数据，例如这里添加token
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchBaseReq.json");
        String newRequestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.keywords", "包");

        String responseBody = TestCaseHelpful.searchGetShopVOByShopId(shopId, newRequestBody, headers, null, null, null, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchBaseResp.json");

        Object result1 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[?(@.shopId==" + shopId + ")]");
        Object result2 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[?(@.shopId==" + shopId + ")].shopName");
        Object result3 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[*].productVOList[?(@.productId==" + productId + ")].productName");


        TestCaseHelpful.assertThat(result1.toString()).isNotEqualTo("[]");
        TestCaseHelpful.assertThat(result2.toString().contains("包")).isEqualTo(false);
        TestCaseHelpful.assertThat(result3.toString().contains("包")).isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
