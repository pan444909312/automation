package com.miller.testcase.module.business.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K73WRD60XRTN1ER1EDQJFW4G",
        scenarioName = "搜索_搜索特殊字符_特殊字符会被过滤",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/v2/search")
public class 搜索_搜索特殊字符_特殊字符会被过滤 {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";


    @DisplayName("搜索_搜索特殊字符_特殊字符会被过滤")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");

        // 沈阳经纬度
        headers.put("latitude", 41.80478);
        headers.put("longitude", 123.43297);
        // 给请求头添加数据，例如这里添加token
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchBaseReq.json");
        String newRequestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.keywords", "&，,");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, newRequestBody);

        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchBaseResp.json");

        Object result1 = TestCaseHelpful.extractValue(responseBody, "$.result.shopList");

        TestCaseHelpful.assertThat(result1.toString()).isEqualTo("[]");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
