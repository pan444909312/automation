package com.miller.testcase.module.account.member;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JY42XMFSSV2JSNVHZ2GS3K39",
        scenarioName = "获取会员购买记录成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5, manualTestTime = 5)
@DisplayName("/api/user/member/buyMemberRecord/list")
public class BuyMemberRecordSuccess {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/member/buyMemberRecord/list";

    @DisplayName("获取会员购买记录成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900007", "123456"));
        String responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/account/member/response/BuyMemberRecordListResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
