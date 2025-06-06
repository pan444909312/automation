package com.miller.testcase.module.home.shop;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JVKR6DPZH7V8WK4B381AMV6H",
        scenarioName = "获取推荐菜品成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/app/user/product/recommend")
public class UserProductRecommendSuccess {
    private static final String uri = TestcaseConfig.HOST + "/api/app/user/product/recommend";

    @DisplayName("获取推荐菜品成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
//        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/shop/request/UserInteractRecommendReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/shop/response/UserInteractRecommendResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
