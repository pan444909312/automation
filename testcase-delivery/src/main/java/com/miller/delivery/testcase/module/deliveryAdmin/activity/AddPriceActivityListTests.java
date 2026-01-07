package com.miller.delivery.testcase.module.deliveryAdmin.activity;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01K5Y1MR1BHPWNAXK0KYQK06HF",
        scenarioName = "司管-roi列表-加价活动",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("加价活动列表")
public class AddPriceActivityListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("创建加价活动并查询ROI列表")
    @Test
    void shouldCreateAddPriceActivityAndQueryRoiList() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当天日期
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 创建商圈加价活动
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/addSubmitRewardContinue";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = String.format("{\r\n" +
                "    \"driverScope\": 0,\r\n" +
                "    \"useFor\": 5,\r\n" +
                "    \"scene\": 3,\r\n" +
                "    \"target\": 2,\r\n" +
                "    \"targetValue\": 1,\r\n" +
                "    \"mapShow\": 1,\r\n" +
                "    \"customerAreaIdList\": [],\r\n" +
                "    \"checkedGroupIdList\": [],\r\n" +
                "    \"excludeGroupIdList\": [],\r\n" +
                "    \"excludeGroupList\": [],\r\n" +
                "    \"checkedGroupList\": [],\r\n" +
                "    \"rewardTypeDesc\": \"\",\r\n" +
                "    \"tobaccoAlcoholType\": 0,\r\n" +
                "    \"cityId\": 1,\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"areaIdList\": [\r\n" +
                "        51\r\n" +
                "    ],\r\n" +
                "    \"driverType\": 0,\r\n" +
                "    \"driverRunType\": null,\r\n" +
                "    \"name\": \"自动化创建加价活动%s\",\r\n" +
                "    \"rewardType\": 2,\r\n" +
                "    \"rewardMethod\": 0,\r\n" +
                "    \"diffDistance\": 0,\r\n" +
                "    \"distanceRuleList\": [\r\n" +
                "        {}\r\n" +
                "    ],\r\n" +
                "    \"rule\": 1,\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"startTime\": \"00:07:07\",\r\n" +
                "    \"endTime\": \"23:07:07\",\r\n" +
                "    \"budget\": 111,\r\n" +
                "    \"rewardReason\": \"自动化创建%s\",\r\n" +
                "    \"pushMsg\": null,\r\n" +
                "    \"deliveryOrderType\": 0,\r\n" +
                "    \"orderTargetType\": 0,\r\n" +
                "    \"tobaccoType\": 0,\r\n" +
                "    \"shopDataType\": 0,\r\n" +
                "    \"shopGroupList\": [],\r\n" +
                "    \"shopGroupIdList\": []\r\n" +
                "}", todayDate, todayDate, todayDate, todayDate);
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 4) 从数据库获取加价活动编号
        Map<String, Object> rewardResult = PandaTestDBHelpful.executeSelectOneSql(
                String.format("select * from panda_test.hp_delivery_reward where `name`='自动化创建加价活动%s' order by id desc limit 1", todayDate));
        String JJrewardSn = String.valueOf(rewardResult.get("reward_sn"));

        // 5) 等待2秒
        Thread.sleep(2000);

        // 6) ROI-加价活动列表-传日期查询
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/activity/roi/list";
        String body2 = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10,\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "    \"startTime\": \"%s\",\r\n" +
                "    \"endTime\": \"%s\",\r\n" +
                "    \"activityType\": 1\r\n" +
                "}", todayDate, todayDate);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
        // 验证返回包含自动化设置的字段
        assert responseBody2.contains("自动化创建加价活动");
        assert responseBody2.contains("进行中");

        // 7) ROI-加价活动列表-不传日期查询
        String body3 = "{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10,\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "\r\n" +
                "    \"activityType\": 1\r\n" +
                "}";
        var responseBody3 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body3);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("message").isEqualTo("成功");
        // 验证返回包含自动化设置的字段
        assert responseBody3.contains("自动化创建加价活动");
        assert responseBody3.contains("进行中");

        // 8) 从ROI库查询id
        Map<String, Object> roiResult = PandaTestDBHelpful.executeSelectOneSql(
                String.format("select * from panda_delivery_dashboard_test.hp_delivery_activity_roi where activity_no='%s' order by id desc", JJrewardSn));
        String ROIid = String.valueOf(roiResult.get("id"));

        // 9) 加价活动-活动群组下线司机数
        String uri3 = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/activity/roi/offlineDriver";
        String body4 = String.format("{\r\n" +
                "    \"id\": %s,\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10\r\n" +
                "}", ROIid);
        var responseBody4 = TestCaseHelpful.sendRequest(method, uri3, null, headers, body4);
        TestCaseHelpful.assertThatJson(responseBody4)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody4)
                .node("message").isEqualTo("成功");

        // 10) 加价活动-活动群组下线司机数-获取第二页数据
        String body5 = String.format("{\r\n" +
                "    \"id\": %s,\r\n" +
                "    \"pageNo\": 2,\r\n" +
                "    \"pageSize\": 10\r\n" +
                "}", ROIid);
        var responseBody5 = TestCaseHelpful.sendRequest(method, uri3, null, headers, body5);
        TestCaseHelpful.assertThatJson(responseBody5)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody5)
                .node("message").isEqualTo("成功");

        // 11) 加价活动-群组空包在线骑手
        String uri4 = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/activity/roi/emptyOnlineDriver";
        String body6 = String.format("{\"id\":%s,\"pageNo\":1,\"pageSize\":10}", ROIid);
        var responseBody6 = TestCaseHelpful.sendRequest(method, uri4, null, headers, body6);
        TestCaseHelpful.assertThatJson(responseBody6)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody6)
                .node("message").isEqualTo("成功");
    }
}

