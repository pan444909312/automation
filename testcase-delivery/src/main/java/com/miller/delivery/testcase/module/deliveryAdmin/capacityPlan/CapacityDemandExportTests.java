package com.miller.delivery.testcase.module.deliveryAdmin.capacityPlan;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-排班计划-导出运力
 */
@Scenario(
        scenarioID = "01JPPNWD7NJVQFFKHBN2VR05R7",
        scenarioName = "司管后台-排班计划-导出运力",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("导出运力")
public class CapacityDemandExportTests {

    @DisplayName("导出运力")
    @Test
    void shouldExportCapacityDemand() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算本周周一和周日
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayOffset = dayOfWeek == DayOfWeek.SUNDAY ? -6 : 1 - dayOfWeek.getValue();
        LocalDate monday = now.plusDays(dayOffset);
        LocalDate sunday = monday.plusDays(6);
        String mondayStr = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sundayStr = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 导出运力
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityDemand/exportDemand";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\n" +
                "    \"city\": \"杭州市\",\n" +
                "    \"startDate\": \"" + mondayStr + "\",\n" +
                "    \"endDate\": \"" + sundayStr + "\",\n" +
                "    \"startTime\": \"00:00\",\n" +
                "    \"endTime\": \"24:00\",\n" +
                "    \"areaIdList\": [\n" +
                "        51\n" +
                "    ]\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言导出成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
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
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

