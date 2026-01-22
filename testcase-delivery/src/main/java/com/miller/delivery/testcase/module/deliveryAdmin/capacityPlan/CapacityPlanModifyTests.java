package com.miller.delivery.testcase.module.deliveryAdmin.capacityPlan;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-排班计划-修改计划
 *
 * Apifox: docs/d-apifox/toCheck/修改计划.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JPPP397TP8B3BACX4P53AJBE",
        scenarioName = "司管后台-排班计划-修改计划",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("修改计划")
public class CapacityPlanModifyTests {

    @DisplayName("修改计划")
    @Test
    void shouldModifyPlan() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 先创建一个计划，拿到 planId（避免依赖环境变量）
        String originalPlanName = "自动化待修改计划-" + System.currentTimeMillis();
        checkGroupStatus(token, headers);
        addPlan(token, headers, originalPlanName);
        Long planId = getPlanIdFromDatabase(originalPlanName);
        assert planId != null : "未获取到 planId";

        // 3) 修改计划（apifox: /api/deliveryAdmin/capacityPlan/modify）
        String newPlanName = "自动化修改计划-" + System.currentTimeMillis();
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/modify";
        String method = "POST";

        // body 基本按 apifox 原样，仅替换 recId/planName
        String body = String.format("{\n" +
                        "  \"recId\": %d,\n" +
                        "  \"planName\": \"%s\",\n" +
                        "  \"cycleType\": 0,\n" +
                        "  \"releaseType\": 0,\n" +
                        "  \"releaseCycleWeek\": 0,\n" +
                        "  \"releaseCycleTime\": \"\",\n" +
                        "  \"deadLineWeek\": 0,\n" +
                        "  \"deadLineTime\": \"\",\n" +
                        "  \"timeList\": [\n" +
                        "    {\n" +
                        "      \"areaIdList\": [51],\n" +
                        "      \"weekList\": [\n" +
                        "        {\"week\": 2, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 3, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 4, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 5, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 6, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 7, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 1, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]}\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"areaIdList\": [163],\n" +
                        "      \"weekList\": [\n" +
                        "        {\"week\": 2, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 3, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 4, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 5, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 6, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 7, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]},\n" +
                        "        {\"week\": 1, \"timeInfoList\": [{\"startTime\": \"00:00\", \"endTime\": \"24:00\"}]}\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"driverGroupList\": [{\"groupId\": 886, \"groupName\": \"自动化群组\", \"driverNum\": 4}],\n" +
                        "  \"driverGroupExcludeList\": [],\n" +
                        "  \"planType\": 0,\n" +
                        "  \"schedulingStatusList\": [99, 2, 1, 0, 10],\n" +
                        "  \"driverGroupIdList\": [886],\n" +
                        "  \"driverGroupIdExcludeList\": [],\n" +
                        "  \"city\": \"杭州市\"\n" +
                        "}", planId, newPlanName);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void checkGroupStatus(String token, Map<String, Object> headers) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/checkGroupStatus";
        String method = "POST";
        headers.put("authorization", token);
        String body = "{\"driverGroupIdExcludeList\":[],\"driverGroupIdList\":[886]}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void addPlan(String token, Map<String, Object> headers, String planName) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/add";
        String method = "POST";
        headers.put("authorization", token);
        String body = String.format("{\"planType\":0,\"schedulingStatusList\":[99,2,1,0,10],\"planName\":\"%s\",\"cycleType\":0,\"deadLineTime\":\"\",\"deadLineWeek\":\"\",\"driverGroupList\":[{\"groupName\":\"自动化群组\",\"groupId\":886,\"driverNum\":0,\"city\":\"杭州市\",\"updateType\":0}],\"driverGroupExcludeList\":[],\"releaseType\":0,\"releaseCycleTime\":\"\",\"releaseCycleWeek\":\"\",\"timeList\":[{\"areaIdList\":[51],\"weekList\":[{\"week\":2,\"timeInfoList\":[{\"startTime\":\"00:00\",\"endTime\":\"24:00\"}]},{\"week\":3,\"timeInfoList\":[{\"startTime\":\"00:00\",\"endTime\":\"24:00\"}]},{\"week\":4,\"timeInfoList\":[{\"startTime\":\"00:00\",\"endTime\":\"24:00\"}]},{\"week\":5,\"timeInfoList\":[{\"startTime\":\"00:00\",\"endTime\":\"24:00\"}]},{\"week\":6,\"timeInfoList\":[{\"startTime\":\"00:00\",\"endTime\":\"24:00\"}]},{\"week\":7,\"timeInfoList\":[{\"startTime\":\"00:00\",\"endTime\":\"24:00\"}]},{\"week\":1,\"timeInfoList\":[{\"startTime\":\"00:00\",\"endTime\":\"24:00\"}]}]}],\"driverGroupIdList\":[886],\"driverGroupIdExcludeList\":[],\"city\":\"杭州市\",\"recId\":\"\"}", planName);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private Long getPlanIdFromDatabase(String planName) {
        List<Map<String, Object>> planRecords = PandaTestDBHelpful.executeSelectListSql(
                "select * from panda_test.hp_delivery_capacity_plan where plan_name=?",
                planName);
        if (planRecords != null && !planRecords.isEmpty()) {
            Object id = planRecords.get(0).get("id");
            if (id != null) {
                return ((Number) id).longValue();
            }
        }
        return null;
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

