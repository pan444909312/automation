package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心-获取骑手垫付金额
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JWB5HM3T40S0212K3FDPMPM8",
        scenarioName = "骑手app-获取骑手垫付金额",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("个人中心-获取骑手垫付金额")
public class DriverAdvanceAmountTests {

    @DisplayName("获取骑手垫付金额")
    @Test
    void shouldGetDriverAdvanceAmount() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 获取骑手垫付金额
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driverSalary/instead/order/amount";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        // 生成日期：今天和过去第6天（即6天前）
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String pastEightDays = LocalDate.now().minusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String body = String.format("{\"dateEnd\":\"%s\",\"dateStart\":\"%s\"}", todayDate, pastEightDays);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216873");
        headers.put("latitude", "30.203664");
        headers.put("version", "5.64.0");
        headers.put("platform", "IOS");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("locationstatus", "2");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("accept", "*/*");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        return headers;
    }
}

