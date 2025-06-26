package com.miller.testcase.module.home.game;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JYGBRPHPQDDCZXACD4XN2BYD",
        scenarioName = "抽奖用户信息获取成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/activity/game/user")
public class GameUserSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/activity/game/user";

    @DisplayName("抽奖用户信息获取成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/home/game/headers/GameUserHeaders.json");
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));

        // 给请求头添加数据，例如这里添加token
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/game/request/GameUserReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/game/response/GameUserResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
