package com.miller.testcase.module.account.member;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K3JSC22F788HW223XQK9B6BC",
        scenarioName = "会员中心页-有服务费减免",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/member/memberCenterDetail")
public class MemberCenterDetailHasPlatformFeeDiscount {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/member/memberCenterDetail";

    @DisplayName("会员中心页-有服务费减免")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("latitude", 41.80478);
        headers.put("longitude", 123.43297);
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13966600001", "123456"));
        String responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/account/member/response/MemberCenterDetailResp.json");
        Object result1 = TestCaseHelpful.extractValue(responseBody, "$.result.memberCenterBenefitList[?(@.benefitType==7)]");
        Object result2 = TestCaseHelpful.extractValue(responseBody, "$.result.memberDetailBenefitDescList[?(@.benefitType==7)].name");

        TestCaseHelpful.assertThat(result1.toString()).isNotEqualTo("[]");
        TestCaseHelpful.assertThat(result2.toString()).isEqualTo("[\"服务费减免\"]");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
