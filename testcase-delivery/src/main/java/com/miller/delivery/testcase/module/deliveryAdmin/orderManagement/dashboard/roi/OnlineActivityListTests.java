package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.roi;

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
        scenarioID = "01K5ZFFD9TWN0A8HDH26T7MR2G",
        scenarioName = "司管-roi列表-上线活动",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("上线活动")
public class OnlineActivityListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("创建上线活动并查询ROI列表")
    @Test
    void shouldCreateOnlineActivityAndQueryRoiList() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当天日期
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 创建上线活动-第一步
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/activity/addOnlineActivity";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"detail\": [\r\n" +
                "        {\r\n" +
                "            \"activityDate\": \"%s\",\r\n" +
                "            \"estimateAmount\": 1,\r\n" +
                "            \"ruleList\": [\r\n" +
                "                {\r\n" +
                "                    \"effectiveTime\": 1,\r\n" +
                "                    \"detailDTOList\": [\r\n" +
                "                        {\r\n" +
                "                            \"activityAmount\": 1\r\n" +
                "                        }\r\n" +
                "                    ]\r\n" +
                "                }\r\n" +
                "            ],\r\n" +
                "            \"timeConfigDTOList\": [\r\n" +
                "                {\r\n" +
                "                    \"timeType\": 0,\r\n" +
                "                    \"timeStart\": \"00:00\",\r\n" +
                "                    \"timeEnd\": \"23:30\"\r\n" +
                "                }\r\n" +
                "            ]\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"effectiveDuration\": 0,\r\n" +
                "    \"orderBased\": 0,\r\n" +
                "    \"punishCoefficient\": 1,\r\n" +
                "    \"pushMs\": \"自动化上线活动\",\r\n" +
                "    \"rewardType\": 0,\r\n" +
                "    \"name\": \"自动化上线活动%s\",\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"type\": \"2\",\r\n" +
                "    \"useFor\": 2,\r\n" +
                "    \"scene\": 2,\r\n" +
                "    \"target\": 2,\r\n" +
                "    \"targetValue\": 1\r\n" +
                "}", todayDate, todayDate, todayDate, todayDate);
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");
        String onlineActivityId = TestCaseHelpful.extractValue(responseBody1, "$.data");

        // 4) 等待2秒
        Thread.sleep(2000);

        // 5) 创建上线活动-第二步添加群组
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/activity/modifyActivityDriverGroup";
        String body2 = String.format("{\"activityNo\":\"%s\",\"checkedGroupIdList\":[886],\"excludeGroupIdList\":[]}", onlineActivityId);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");

        // 6) 等待2秒
        Thread.sleep(2000);

        // 7) 发布上线活动
        String uri3 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/activity/releaseActivity";
        String body3 = String.format("{\"activityNo\":\"%s\"}", onlineActivityId);
        var responseBody3 = TestCaseHelpful.sendRequest(method, uri3, null, headers, body3);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody3)
                .node("message").isEqualTo("成功");

        // 8) 等待3秒
        Thread.sleep(3000);

        // 9) 验证数据库中有新增的活动
        String sql = String.format("select count(0) as count from panda_test.hp_delivery_activity where `name`='自动化上线活动%s' order by id desc ;", todayDate);
        Map<String, Object> dbResult = PandaTestDBHelpful.executeSelectOneSql(sql);
        Long onlineCount = (Long) dbResult.get("count");
        assert onlineCount != null && onlineCount > 0 : "数据库中没有找到新增的活动";

        // 10) ROI-上线活动列表-传日期查询
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
                "    \"activityType\": 3\r\n" +
                "}", todayDate, todayDate);
        var responseBody4 = TestCaseHelpful.sendRequest(method, uri4, null, headers, body4);
        TestCaseHelpful.assertThatJson(responseBody4)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody4)
                .node("message").isEqualTo("成功");
        assert responseBody4.contains("自动化上线活动") : "返回结果中应包含自动化上线活动";
        assert responseBody4.contains("生效中") : "返回的活动应处于生效中状态";
        assert responseBody4.contains(onlineActivityId) : "返回的活动应包含自动化创建的ID";

        // 11) 等待1秒
        Thread.sleep(1000);

        // 12) ROI-上线活动列表-不传日期查询
        String body5 = "{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10,\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "\r\n" +
                "    \"activityType\": 3\r\n" +
                "}";
        var responseBody5 = TestCaseHelpful.sendRequest(method, uri4, null, headers, body5);
        TestCaseHelpful.assertThatJson(responseBody5)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody5)
                .node("message").isEqualTo("成功");
        assert responseBody5.contains("自动化") : "返回结果中应包含自动化";
        assert responseBody5.contains("生效中") : "返回的活动应处于生效中状态";
    }
}

