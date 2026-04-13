package com.miller.testcase.module.home.user;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import io.swagger.v3.oas.models.security.SecurityScheme;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JS0NFFQ1KDF00GY5XRJ93X5M",
        scenarioName = "用户中心接口：地推入口返回",
        author = "yancancan@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("用户中心接口：地推入口返回")
public class UserCenterPandaNewUserTests {
    private static final String URI = TestcaseConfig.HOST_APP + "/api/user/center";
    private static final String METHOD = "GET";
    private static final String HEADERS = "module/home/user/user_center_auto/request/headers.json";
    private static final String ASSERT_FULL_FIELD = "module/home/user/user_center_auto/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        var requestHeaders = TestCaseHelpful.getHeaders(HEADERS);
        requestHeaders.put("authorization", TestCaseHelpful.login("13212341234", "123456"));

        var responseBody = TestCaseHelpful.sendRequest(METHOD, URI, null, requestHeaders, null);

        var expectedStr = TestCaseHelpful.getFileContent(ASSERT_FULL_FIELD);
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        Integer type =TestCaseHelpful.extractValue(responseBody,"$.result.entranceList[0].type");
        String name =TestCaseHelpful.extractValue(responseBody,"$.result.entranceList[0].name");
        TestCaseHelpful.assertThat(type).isEqualTo(6);
        TestCaseHelpful.assertThat(name).isEqualTo("熊猫推广");
    }
}
