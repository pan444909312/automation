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
        scenarioID = "01K5ZEKP3RM3PSWMAQ69NS4D97",
        scenarioName = "司管-roi列表-冲单活动",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("冲单活动列表")
public class ChongdanActivityListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("创建冲单活动并查询ROI列表")
    @Test
    void shouldCreateChongdanActivityAndQueryRoiList() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当天日期
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 创建冲单活动-第一步新增活动
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/activity/addActivity";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"billingCycle\": 1,\r\n" +
                "    \"name\": \"自动化冲单活动%s\",\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"type\": 0,\r\n" +
                "    \"pushMs\": \"22222\",\r\n" +
                "    \"deliveryType\": 0,\r\n" +
                "    \"distanceType\": 0,\r\n" +
                "    \"orderType\": 0,\r\n" +
                "    \"onTimeType\": [],\r\n" +
                "    \"dutyType\": 0,\r\n" +
                "    \"customerAreaIdList\": [],\r\n" +
                "    \"deliveryAreaIdList\": [],\r\n" +
                "    \"useFor\": 3,\r\n" +
                "    \"scene\": 5,\r\n" +
                "    \"target\": 1,\r\n" +
                "    \"targetValue\": 1,\r\n" +
                "    \"mustDateType\": 0,\r\n" +
                "    \"mustDateConfig\": {\r\n" +
                "        \"mustDateList\": []\r\n" +
                "    },\r\n" +
                "    \"diffDistance\": 0,\r\n" +
                "    \"driverScope\": 0,\r\n" +
                "    \"ruleList\": [\r\n" +
                "        {\r\n" +
                "            \"orderNum\": 1,\r\n" +
                "            \"bonus\": 2\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"timeConfigDTOList\": [\r\n" +
                "        {\r\n" +
                "            \"timeStart\": \"00:00\",\r\n" +
                "            \"timeEnd\": \"23:59\"\r\n" +
                "        }\r\n" +
                "    ]\r\n" +
                "}", todayDate, todayDate, todayDate);
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");
        String chongdanActivityId = TestCaseHelpful.extractValue(responseBody1, "$.data");

        // 4) 等待1秒
        Thread.sleep(1000);

        // 5) 创建冲单活动-第二步添加群组
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/activity/addActivityDriverGroup";
        String body2 = String.format("{\"activityNo\":\"%s\",\"checkedGroupIdList\":[886],\"excludeGroupIdList\":[]}", chongdanActivityId);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");

        // 6) 等待1秒
        Thread.sleep(1000);

        // 7) 发布冲单活动
        String uri3 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/activity/releaseActivity";
        String body3 = String.format("{\"activityNo\":\"%s\"}", chongdanActivityId);
        var responseBody3 = TestCaseHelpful.sendRequest(method, uri3, null, headers, body3);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("message").isEqualTo("成功");

        // 8) 等待1秒
        Thread.sleep(1000);

        // 9) 验证数据库中有新增的活动
        String sql = String.format("select count(0) as count from panda_test.hp_delivery_activity where `name`='自动化冲单活动%s' order by id desc ;", todayDate);
        Map<String, Object> dbResult = PandaTestDBHelpful.executeSelectOneSql(sql);
        Long chongdanCount = (Long) dbResult.get("count");

        assert chongdanCount != null && chongdanCount > 0 : "数据库中没有找到新增的活动";

        // 10) ROI-加价活动列表-传日期查询
        String uri4 = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/activity/roi/list";
        String body4 = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10,\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "    \"startTime\": \"%s\",\r\n" +
                "    \"endTime\": \"%s\",\r\n" +
                "    \"activityType\": 2\r\n" +
                "}", todayDate, todayDate);
        var responseBody4 = TestCaseHelpful.sendRequest(method, uri4, null, headers, body4);
        TestCaseHelpful.assertThatJson(responseBody4)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody4)
                .node("message").isEqualTo("成功");
        assert responseBody4.contains("自动化冲单活动") : "返回结果中应包含自动化冲单活动";
        assert responseBody4.contains("生效中") : "返回的活动应处于生效中状态";

        // 11) 等待1秒
        Thread.sleep(1000);

        // 12) ROI-加价活动列表-不传日期查询
        String uri5 = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/activity/roi/list";
        String body5 = "{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10,\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "    \"activityType\": 2\r\n" +
                "}";
        var responseBody5 = TestCaseHelpful.sendRequest(method, uri5, null, headers, body5);
        TestCaseHelpful.assertThatJson(responseBody5)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody5)
                .node("message").isEqualTo("成功");
        assert responseBody5.contains("自动化冲单活动") : "返回结果中应包含自动化冲单活动";
        assert responseBody5.contains("生效中") : "返回的活动应处于生效中状态";
    }
}

