package com.miller.testcaseuserapp.module.home.module.mobilebanner;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JWCZVXDASV9FCE7F774FJR2Z", scenarioName = "m站-广告banner展示"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ModuleMobileBannerScenarioTests {
    private static final String uri = TestcaseConfig.HostMobile+"/api/app/user/web/banner/image";
    @DisplayName("m站-广告banner展示")
    @Test
    public void shouldReturnBannerSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers_mobile.json");
        var requestBody=TestCaseHelpful.getJsonRequestBody("module/home/module/mobilebanner/request/success.json");
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, requestBody, null);
        var expectedStr = TestCaseHelpful.getFileContent("module/home/module/mobilebanner/response/assert_some_fields.json");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
}
