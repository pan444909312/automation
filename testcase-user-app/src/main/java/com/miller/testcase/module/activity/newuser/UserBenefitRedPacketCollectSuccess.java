package com.miller.testcase.module.activity.newuser;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K4YEK2RPG51KX76D9GYJVHT5",
        scenarioName = "领取新人权益红包-权益不存在",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("/api/user/benefit/redPacket/collect")
public class UserBenefitRedPacketCollectSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/benefit/redPacket/collect";

    // 如果再次报错 就添加一个before操作，清楚一下数据 SELECT * FROM hp_user_benefit_red_packet_record WHERE user_id in (1398715012)

    @DisplayName("领取新人权益红包-权益不存在")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900003", "123456"));
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/activity/newuser/request/UserBenefitRedPacketCollectReq.json");
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/activity/newuser/response/UserBenefitRedPacketCollectResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
