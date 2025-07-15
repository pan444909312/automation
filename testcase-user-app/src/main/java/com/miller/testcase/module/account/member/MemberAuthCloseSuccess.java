package com.miller.testcase.module.account.member;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.PandaTestDBHelpful;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JZSNT53FBG2WSRNSNPY8C2R0",
        scenarioName = "非自动续费会员，关闭自动续费",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 45, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("/api/app/user/member/auth/close")
public class MemberAuthCloseSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/app/user/member/auth/close";

    @DisplayName("非自动续费会员，关闭自动续费")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        Integer code = TestCaseHelpful.getVerificationCode("13999900002");
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("UPDATE user_log SET user_type = 10 WHERE verifycode = " + code + " ORDER BY create_time desc LIMIT 1");
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/account/member/request/MemberAuthCloseReq.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "captcha", code);
        String responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        String expectedStr = TestCaseHelpful.getFileContent("module/account/member/response/MemberAuthCloseResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
