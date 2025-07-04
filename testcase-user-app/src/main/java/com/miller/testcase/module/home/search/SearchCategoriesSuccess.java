package com.miller.testcase.module.home.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JVKR6DPZH7V8WK4B381AMV6D",
        scenarioName = "搜索分类获取成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/search/categories")
public class SearchCategoriesSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/search/categories";

    @DisplayName("搜索分类获取成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchCategoriesReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/search/response/SearchCategoriesResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
