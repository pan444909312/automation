package com.miller.testcase.module.activity.theme.saleproductchannel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

        // 从期望结果中提取指定shopId的对象
        var expectedShopList = TestCaseHelpful.extractValue(expectedStr, "$.result.shopIndexVOS");
        var expectedShopObj = findShopById(JSON.parseArray(expectedShopList.toString()), 221864167);

        // 从实际响应中提取指定shopId的对象
        var actualShopList = TestCaseHelpful.extractValue(responseBody, "$.result.shopIndexVOS");
        var actualShopObj = findShopById(JSON.parseArray(actualShopList.toString()), 221864167);

        TestCaseHelpful.assertThat(actualShopObj).isNotNull();
    }
    /**
     * 从店铺数组中查找指定shopId的店铺对象
     *
     * @param shopArray 店铺数组
     * @param shopId 目标shopId
     * @return 找到的店铺对象，如果未找到则返回null
     */
    private JSONObject findShopById(JSONArray shopArray, int shopId) {
        for (int i = 0; i < shopArray.size(); i++) {
            JSONObject shop = shopArray.getJSONObject(i);
            if (shopId == shop.getIntValue("shopId")) {
                return shop;
            }
        }
        return null;
    }
}
