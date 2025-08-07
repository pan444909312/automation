package com.miller.pandafresh.testcase.module.goods;

import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 商品推荐
 */
@Scenario(
        scenarioID = "01JWG74KCTFV96ZPCSGK9GYHR1",
        scenarioName = "商品推荐(加购A推荐B)",
        author = "zhangpei@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("商品推荐(加购A推荐B)")
public class RecommendGoodsTests {
    String uri = TestcaseConfig.HOST + "/goods/recommend/goods";
    String method = "POST";
    String headers = "module/headersPF.json";
    String body = "module/goods/request/recommendGoods.json";
    String assert1 = "module/goods/response/assert_recommend_goods.json";

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
