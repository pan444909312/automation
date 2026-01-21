package com.miller.testcase.module.business.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01KEDR1EA72NXNSWDJGXHJK2QM",
        scenarioName = "搜索品牌词_搜索词仅匹配品牌词部分_不能命中品牌词",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/v2/search")
public class 搜索品牌词_搜索词仅匹配品牌词部分_不能命中品牌词 {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";

    private String shopId = "282341168";

    @DisplayName("搜索品牌词_搜索词仅匹配品牌词部分_不能命中品牌词")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");

        // 给请求头添加数据，例如这里添加token
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchBaseReq.json");
        String newRequestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.keywords", "沙县");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, newRequestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchBaseResp.json");


        Object result1 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[?(@.shopId==" + shopId + ")]");
        Object result2 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList[?(@.shopId==" + shopId + ")].shopName");

        TestCaseHelpful.assertThat(result1.toString()).isNotEqualTo("[]");
        TestCaseHelpful.assertThat(result2.toString().contains("沙县")).isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
