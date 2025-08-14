package com.miller.testcase.module.home.supermarket;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Scenario(scenarioID = "01JWWF717DPCCRW2Y1KVH2EK1R", scenarioName = "获取首页底部tab：定位运营区域外"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
public class SupermarketOutScenarioTests {
    private static final String uri = TestcaseConfig.HOST_APP+"/api/user/supermarket";
    @DisplayName("获取首页底部tab：定位运营区域外")
    @Test
    void shouldReturnTaskSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("latitude","50.203565");
        headers.put("longitude","12.21706");
        var responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);


        var expectedStr = TestCaseHelpful.getFileContent("module/home/supermarket/response/assert_out_field.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.isOpen").isEqualTo(0);

    }
}
