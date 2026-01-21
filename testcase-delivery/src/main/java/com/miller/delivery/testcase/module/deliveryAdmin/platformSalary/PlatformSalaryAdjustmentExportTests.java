package com.miller.delivery.testcase.module.deliveryAdmin.platformSalary;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 平台薪资调整-导出
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDQPTAF4RSWD3M2MDBSJTZFT",
        scenarioName = "平台薪资调整-导出",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("平台薪资调整-导出")
public class PlatformSalaryAdjustmentExportTests {

    /**
     * 获取昨天日期
     */
    private String getYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return yesterday.format(formatter);
    }

    @DisplayName("平台薪资-导出")
    @Test
    void shouldExportPlatformSalaryAdjustment() {
        // 1) 司管登录获取 token
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        // 2) 获取昨天日期
        String yesterday = getYesterday();

        // 3) 平台薪资-导出
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/platformSalary/platformSalaryAdjustmentExport";
        String method = "POST";
        String body = String.format("{\"amountType\":null,\"driverName\":null,\"mobile\":\"\",\"city\":null,\"siteIdList\":[],\"areaIdList\":[],\"driverBusinessType\":null,\"createStartTime\":\"%s\",\"createEndTime\":\"%s\",\"adjustStartTime\":null,\"adjustEndTime\":null,\"pageNo\":1,\"pageSize\":15}",
                yesterday, yesterday);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("data").isNotNull();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

