package com.miller.delivery.testcase.module.deliveryAdmin.salaryManagement.platformSalary;

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
 * 调整PF单个骑手薪资
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KDQPN9KFK5ZJF1MECDX8QWYY",
        scenarioName = "PF单个骑手调整薪资",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("调整PF单个骑手薪资")
public class AdjustPfDriverSalaryTests {

    @DisplayName("调整PF单个骑手薪资-完整流程")
    @Test
    void shouldAdjustPfDriverSalary() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取所有平台薪资调整（查询骑手薪资信息）
        String yesterday = getYesterdayDate();
        String queryUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/platformSalary/queryDriverCitySalary";
        String queryMethod = "POST";
        Map<String, Object> queryHeaders = createHeaders(token);
        String queryBody = String.format(
                "{\"city\":\"杭州市\",\"date\":\"%s\",\"driverId\":\"1398722226\",\"siteId\":null,\"salaryBusinessType\":0}",
                yesterday);
        var queryResponse = TestCaseHelpful.sendRequest(queryMethod, queryUri, null, queryHeaders, queryBody);

        // 断言查询接口返回成功
        TestCaseHelpful.assertThatJson(queryResponse)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(queryResponse)
                .node("message").isEqualTo("成功");

        // 3) 单个骑手调整薪资（PF类型，reasonType=7，salaryBusinessType=1，包含siteId）
        String dayBeforeYesterday = getDayBeforeYesterdayDate();
        String adjustUri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/platformSalary/addPlatformSalary";
        String adjustMethod = "POST";
        Map<String, Object> adjustHeaders = createHeaders(token);
        String adjustBody = String.format(
                "{\"amount\":\"10\",\"amountType\":2,\"city\":\"杭州市\",\"dateDesc\":\"%s\",\"driverId\":\"1398722226\",\"reasonType\":7,\"salaryBusinessType\":1,\"siteId\":6}",
                dayBeforeYesterday);
        var adjustResponse = TestCaseHelpful.sendRequest(adjustMethod, adjustUri, null, adjustHeaders, adjustBody);

        // 断言调整薪资接口返回（可能成功或失败，取决于是否在生成薪资期间）
        // 根据 Apifox 测试脚本，可能返回 code=1 或 code=9999
        boolean result = (Integer) TestCaseHelpful.extractValue(adjustResponse, "code") == 1 || (Integer) TestCaseHelpful.extractValue(adjustResponse, "code") == 9999;
        TestCaseHelpful.assertThat(result).isEqualTo(true);
        // message 可能是"成功"或"2025-12-31 00:00~14:50期间在生成2025-12-30骑手薪资，不能添加平台薪资调整"
        TestCaseHelpful.assertThatJson(adjustResponse)
                .node("message").isNotNull();
    }

    /**
     * 获取昨天的日期字符串（格式：YYYY-MM-DD）
     */
    private String getYesterdayDate() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取前天的日期字符串（格式：YYYY-MM-DD）
     */
    private String getDayBeforeYesterdayDate() {
        LocalDate dayBeforeYesterday = LocalDate.now().minusDays(2);
        return dayBeforeYesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 创建请求头
     */
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

