package com.miller.delivery.testcase.module.deliveryAdmin.planManagement.capacityPlan;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 司管后台-排班计划-自行发布下周
 *
 * Apifox: docs/d-apifox/toCheck/自行发布-下周.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JPPP5JKKYQV0RRNV7H0QB2NE",
        scenarioName = "司管后台-排班计划-自行发布下周",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("自行发布-下周")
public class CapacityPlanManualReleaseNextWeekTests {

    @DisplayName("下周")
    @Test
    void shouldManualReleaseNextWeek() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 先创建一个计划，拿到 planId（避免依赖环境变量）
        String planName = "自动化发布下周-" + System.currentTimeMillis();
        checkGroupStatus(token, headers);
        addPlan(token, headers, planName);
        Long planId = getPlanIdFromDatabase(planName);
        assert planId != null : "未获取到 planId";

        // 3) 计算下周周一、周二和周日（对齐 apifox 逻辑）
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayOffset = dayOfWeek == DayOfWeek.SUNDAY ? -6 : 1 - dayOfWeek.getValue();
        LocalDate monday = now.plusDays(dayOffset);
        LocalDate nextMonday = monday.plusDays(7);
        LocalDate nextTuesday = monday.plusDays(8);
        LocalDate nextSunday = monday.plusDays(13);
        String nextMondayStr = nextMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nextTuesdayStr = nextTuesday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nextSundayStr = nextSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 4) 自行发布下周（apifox: /api/deliveryAdmin/capacityPlan/manualRelease）
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/manualRelease";
        String method = "POST";
        String body = String.format("{\"deadLineDate\":\"%s\",\"deadLineTime\":\"23:00\",\"endDate\":\"%s\",\"recId\":%d,\"releaseType\":0,\"schedulingStatusList\":[99,2,1,0,10],\"week\":\"%s\",\"startDate\":\"%s\"}",
                nextSundayStr, nextSundayStr, planId, nextTuesdayStr, nextMondayStr);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 5) 断言
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

