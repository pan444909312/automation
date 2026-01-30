package com.miller.delivery.testcase.module.deliveryAdmin.planManagement.capacityPlan;

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
 * 司管后台-排班计划-添加计划
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JPPP2MCK9ZZANRCPXDZASSCX",
        scenarioName = "司管后台-排班计划-添加计划",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("添加计划")
public class CapacityPlanAddTests {

    @DisplayName("添加计划")
    @Test
    void shouldAddCapacityPlan() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 生成唯一计划名称（使用时间戳）
        long nowTime = System.currentTimeMillis();
        String planName = "自动化添加计划" + nowTime;

        // 3) 校验群组
        checkGroupStatus(token, headers);

        // 4) 添加计划
        addPlan(token, headers, planName);

        // 5) 从数据库查询新建计划的ID
        Long planId = getPlanIdFromDatabase(planName);
        
        // 6) 验证计划ID不为空
        assert planId != null : "数据库中没有找到新建的计划";
    }

    /**
     * 校验群组
     */
    private void checkGroupStatus(String token, Map<String, Object> headers) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/checkGroupStatus";
        String method = "POST";
        headers.put("authorization", token);
        
        String body = "{\"driverGroupIdExcludeList\":[],\"driverGroupIdList\":[886]}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    /**
     * 添加计划
     */
    private void addPlan(String token, Map<String, Object> headers, String planName) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/add";
        String method = "POST";
        headers.put("authorization", token);
        
        String body = String.format(
                "{\n" +
                "    \"planType\": 0,\n" +
                "    \"schedulingStatusList\": [\n" +
                "        99,\n" +
                "        2,\n" +
                "        1,\n" +
                "        0,\n" +
                "        10\n" +
                "    ],\n" +
                "    \"planName\": \"%s\",\n" +
                "    \"cycleType\": 0,\n" +
                "    \"deadLineTime\": \"\",\n" +
                "    \"deadLineWeek\": \"\",\n" +
                "    \"driverGroupList\": [\n" +
                "        {\n" +
                "            \"groupName\": \"自动化群组\",\n" +
                "            \"groupId\": 886,\n" +
                "            \"driverNum\": 0,\n" +
                "            \"city\": \"杭州市\",\n" +
                "            \"updateType\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"driverGroupExcludeList\": [],\n" +
                "    \"releaseType\": 0,\n" +
                "    \"releaseCycleTime\": \"\",\n" +
                "    \"releaseCycleWeek\": \"\",\n" +
                "    \"timeList\": [\n" +
                "        {\n" +
                "            \"areaIdList\": [\n" +
                "                51\n" +
                "            ],\n" +
                "            \"weekList\": [\n" +
                "                {\n" +
                "                    \"week\": 2,\n" +
                "                    \"timeInfoList\": [\n" +
                "                        {\n" +
                "                            \"startTime\": \"00:00\",\n" +
                "                            \"endTime\": \"24:00\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"week\": 3,\n" +
                "                    \"timeInfoList\": [\n" +
                "                        {\n" +
                "                            \"startTime\": \"00:00\",\n" +
                "                            \"endTime\": \"24:00\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"week\": 4,\n" +
                "                    \"timeInfoList\": [\n" +
                "                        {\n" +
                "                            \"startTime\": \"00:00\",\n" +
                "                            \"endTime\": \"24:00\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"week\": 5,\n" +
                "                    \"timeInfoList\": [\n" +
                "                        {\n" +
                "                            \"startTime\": \"00:00\",\n" +
                "                            \"endTime\": \"24:00\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"week\": 6,\n" +
                "                    \"timeInfoList\": [\n" +
                "                        {\n" +
                "                            \"startTime\": \"00:00\",\n" +
                "                            \"endTime\": \"24:00\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"week\": 7,\n" +
                "                    \"timeInfoList\": [\n" +
                "                        {\n" +
                "                            \"startTime\": \"00:00\",\n" +
                "                            \"endTime\": \"24:00\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"week\": 1,\n" +
                "                    \"timeInfoList\": [\n" +
                "                        {\n" +
                "                            \"startTime\": \"00:00\",\n" +
                "                            \"endTime\": \"24:00\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"driverGroupIdList\": [\n" +
                "        886\n" +
                "    ],\n" +
                "    \"driverGroupIdExcludeList\": [],\n" +
                "    \"city\": \"杭州市\",\n" +
                "    \"recId\": \"\"\n" +
                "}",
                planName);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    /**
     * 从数据库查询新建计划的ID
     */
    private Long getPlanIdFromDatabase(String planName) {
        List<Map<String, Object>> planRecords = PandaTestDBHelpful.executeSelectListSql(
                "select * from panda_test.hp_delivery_capacity_plan where plan_name=?",
                planName);
        
        if (planRecords != null && !planRecords.isEmpty()) {
            Map<String, Object> planRecord = planRecords.get(0);
            if (planRecord.get("id") != null) {
                return ((Number) planRecord.get("id")).longValue();
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

