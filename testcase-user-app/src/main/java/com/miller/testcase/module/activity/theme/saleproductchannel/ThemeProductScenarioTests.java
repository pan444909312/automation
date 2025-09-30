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

import java.util.ArrayList;


@Scenario(scenarioID = "01JWCZVXDASV9FCE7F774FJR30", scenarioName = "未登录用户-首页天天特价楼层"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ThemeProductScenarioTests {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/app/user/theme/product/index";

    @DisplayName("未登录用户-首页天天特价楼层")
    @Test
    void shouldReturnRedpacketSuccessfully() {
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/activity/theme/saleproductchannel/request/success.json");
        var expectedStr = TestCaseHelpful.getFileContent("module/activity/theme/saleproductchannel/response/assert_some_fields.json");

        //发送请求
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, requestBody);

        // 判断商品列表不为空
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.productList").isNotNull();
        //判断商品列表存在指定店铺的商品
        ArrayList<Object> productList=TestCaseHelpful.extractValue(responseBody, "$.result.productList");
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.productList").isArray();
        var actualShopObj = findShopById(JSON.parseArray(productList.toString()), 221864167);
        TestCaseHelpful.assertThat(actualShopObj).isNotNull();

        }
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
