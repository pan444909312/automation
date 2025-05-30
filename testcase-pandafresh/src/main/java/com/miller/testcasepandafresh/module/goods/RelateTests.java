package com.miller.testcasepandafresh.module.goods;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcasepandafresh.config.TestcaseConfig;
import com.miller.testcasepandafresh.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 商详页-关联商品
 * 相似商品和搭配购买商品
 */
@Scenario(
        scenarioID = "01JWG74KCTFV96ZPCSGK9GYHRT",
        scenarioName = "商品详情-关联商品:相似商品和搭配购买商品",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("商品详情-关联商品:相似商品和搭配购买商品")
public class RelateTests {
    String uri = TestcaseConfig.HOST + "/goods/relate";
    String method = "POST";
    String headers = "module/headersPF.json";
    String body = "module/goods/request/relate.json";
    String assert1 = "module/goods/response/assert_relate.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccessfully() {
        var requestHeaders = TestCaseHelpful.getHeaders(headers);

        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.code").isEqualTo(1);// 使用 JsonPath 方式
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.data").isNotNull();
    }
}
