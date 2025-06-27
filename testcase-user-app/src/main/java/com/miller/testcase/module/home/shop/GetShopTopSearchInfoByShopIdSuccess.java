package com.miller.testcase.module.home.shop;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JY3VWZQZHFKG1859B0G53HGX",
        scenarioName = "根据店铺ID获取到当前店铺的配置的搜索热词成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/app/user/product/recommend")
public class GetShopTopSearchInfoByShopIdSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v1/shop/getShopTopSearchInfoByShopId?shopId=160288176";

    @DisplayName("根据店铺ID获取到当前店铺的配置的搜索热词成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
//        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/shop/response/GetShopTopSearchInfoByShopIdResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
