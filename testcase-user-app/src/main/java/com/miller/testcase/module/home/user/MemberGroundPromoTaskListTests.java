package com.miller.testcase.module.home.user;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01JS0R4F4Q7AB6W96Q6R9N3EWC",
        scenarioName = "会员地推任务列表",
        author = "yancancan@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("会员地推任务列表")
public class MemberGroundPromoTaskListTests {
    private static final String URI = TestcaseConfig.HOST_APP + "/api/app/user/member/ground/promo/task/list";
    private static final String METHOD = "POST";
    private static final String HEADERS = "module/home/user/member_ground_promo_task_list/request/headers.json";
    private static final String BODY = "module/home/user/member_ground_promo_task_list/request/body.json";
    private static final String ASSERT_FULL_FIELD = "module/home/user/member_ground_promo_task_list/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        var requestHeaders = TestCaseHelpful.getHeaders(HEADERS);
        requestHeaders.put("authorization", TestCaseHelpful.login("13212341234", "123456"));

        var requestBody = TestCaseHelpful.getJsonRequestBody(BODY);
        var responseBody = TestCaseHelpful.sendRequest(METHOD, URI, null, requestHeaders, requestBody);

        var expectedStr = TestCaseHelpful.getFileContent(ASSERT_FULL_FIELD);
        var taskList= TestCaseHelpful.extractValue(responseBody,"$.result.taskList");
        TestCaseHelpful.assertThat(taskList).asList().isNotNull();
    }
}
