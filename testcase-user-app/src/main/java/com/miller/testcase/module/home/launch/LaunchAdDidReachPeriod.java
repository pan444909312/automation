package com.miller.testcase.module.home.launch;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.LaunchAdUtils;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K44F9XW1FD7M9J1B7KAQT14X",
        scenarioName = "拉取启动页广告-未到有效期会正常返回（客户端处理）",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/v1/launchAd/pull")
public class LaunchAdDidReachPeriod {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v1/launchAd/pull";

    @BeforeAll
    void beforeAll() {
        LaunchAdUtils.initCityLaunchAd("九江市");
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("update hp_launch_ad set state = 1 where la_id = 281");
    }

    @AfterAll
    void afterAll() {
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("update hp_launch_ad set state = 0 where la_id = 281");
    }

    @DisplayName("拉取启动页广告-未到有效期会正常返回（客户端处理）")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/launch/request/LaunchAdReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/launch/response/LaunchAdResp.json");
        Object jumpType = TestCaseHelpful.extractValue(responseBody, "$.result.launchAdList[0].jumpType");
        Object jumpUrl = TestCaseHelpful.extractValue(responseBody, "$.result.launchAdList[0].jumpUrl");

        TestCaseHelpful.assertThat(jumpUrl).isNull();
        TestCaseHelpful.assertThat((int)jumpType).isEqualTo(0);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
