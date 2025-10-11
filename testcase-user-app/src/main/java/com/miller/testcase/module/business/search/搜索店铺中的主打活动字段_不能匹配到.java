package com.miller.testcase.module.business.search;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K5DV4PRV40AVKVB0VBNKMATC",
        scenarioName = "搜索店铺中的主打活动字段_不能匹配到",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/v2/search")
public class 搜索店铺中的主打活动字段_不能匹配到 {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/v2/search";

    private String shopId = "623420023";

    @DisplayName("搜索店铺中的主打活动字段_不能匹配到")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");

        // 沈阳经纬度
        headers.put("latitude", 41.80478);
        headers.put("longitude", 123.43297);
        // 给请求头添加数据，例如这里添加token
        String requestBody = TestCaseHelpful.getJsonRequestBody("module/home/search/request/SearchBaseReq.json");
        String newRequestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.keywords", "主打");
        String responseBody = TestCaseHelpful.searchGetShopVOByShopId(shopId, newRequestBody, headers, null, null, null, null);

        TestCaseHelpful.assertThat(responseBody).isEqualTo(null);
    }
}
