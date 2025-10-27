package com.miller.testcase.module.activity.topic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(
        scenarioID = "01K4Q2FZN299S7GEFAFCHV056Y",
        scenarioName = "获取活动专题信息-店铺楼层",
        author = "yancancan@hungrypandagroup.com",
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取活动专题信息-店铺楼层")
public class ActivityShopListTests {
    // 接口请求的 path
    String uri = TestcaseConfig.HOST_APP + "/api/user/activity/getShopListByModuleId";
    // 请求方式
    String method = "POST";
    // 请求头
    String headers = "module/activity/topic/activityinfo/request/headers_topic.json";
    // 请求体。如果没有传 null 即可（body = null）。比如 GET 请求
    String body = "module/activity/topic/shoplist/request/success.json";
    // 断言
    String assert2 = "module/activity/topic/shoplist/response/assert_some_fields.json";
    @DisplayName("获取活动专题信息-店铺楼层")
    @Test
    void shouldReturnSuccessfully() {
        // 步骤1: 设置请求头。基本固定写法，不需要修改
        var requestHeaders = TestCaseHelpful.getHeaders(headers);
        // 步骤2: 设置请求体。基本固定写法，不需要修改
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);

        // 步骤3: 发起请求,并获取响应结果。基本固定写法，不需要修改
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, requestHeaders, requestBody);

        // 步骤4: 断言响应结果，直接拷贝抓包响应结果作为断言。基本固定写法，不需要修改
        // 方式二：部份匹配，
//        String expectedStr = TestCaseHelpful.getFileContent(assert2);
//        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
        var shopIndexVO= TestCaseHelpful.extractValue(responseBody, "$.result");
        var actualShopObj = findShopById(JSON.parseArray(shopIndexVO.toString()), 498708250);
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
