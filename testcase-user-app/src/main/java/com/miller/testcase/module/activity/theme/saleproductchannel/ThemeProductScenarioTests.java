package com.miller.testcase.module.activity.theme.saleproductchannel;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Scenario(scenarioID = "01JWCZVXDASV9FCE7F774FJR30", scenarioName = "未登录用户-首页天天特价楼层"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ThemeProductScenarioTests {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/app/user/theme/product/index";

    @DisplayName("未登录用户-首页天天特价楼层")
    @Test
    void shouldReturnRedpacketSuccessfully() {
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/activity/theme/saleproductchannel/request/success.json");
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);
        var expectedStr = TestCaseHelpful.getFileContent("module/activity/theme/saleproductchannel/response/assert_some_fields.json");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
}
