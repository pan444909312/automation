package com.miller.delivery.testcase.module.deliveryApp.driverCenter;

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
 * 个人中心-历史订单
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JPP713ZKPBA2TK8ZF4CNTZ71",
        scenarioName = "骑手app-个人中心-历史订单",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("个人中心-历史订单")
public class OrderHistoryListTests {

    @DisplayName("历史订单-完单")
    @Test
    void shouldGetCompletedOrderHistory() throws InterruptedException {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 历史订单-完单
        getOrderHistory(driverAccessToken, 1);

        // 3) 延迟1秒
        Thread.sleep(1000);

        // 4) 历史订单-撤单
        getOrderHistory(driverAccessToken, 2);

        // 5) 延迟1秒
        Thread.sleep(1000);

        // 6) 历史订单-拒单
        getOrderHistory(driverAccessToken, 3);

        // 7) 延迟1秒
        Thread.sleep(1000);

        // 8) 历史订单-到店晚单
        getOrderHistory(driverAccessToken, 4);

        // 9) 延迟1秒
        Thread.sleep(1000);

        // 10) 历史订单-超时近单
        getOrderHistory(driverAccessToken, 8);

        // 11) 延迟1秒
        Thread.sleep(1000);

        // 12) 未登录 -历史订单-超时近单
        getOrderHistoryWithoutLogin(8);
    }

    /**
     * 获取历史订单
     */
    private void getOrderHistory(String driverAccessToken, int type) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/v2/historyList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        // 获取当前日期
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        String body = String.format(
                "{\"endDate\":\"%s\",\"pageNo\":1,\"pageSize\":20,\"type\":%d,\"startDate\":\"%s\"}",
                todayDate, type, todayDate);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 未登录 -历史订单
     */
    private void getOrderHistoryWithoutLogin(int type) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/v2/historyList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        // 获取当前日期
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        String body = String.format(
                "{\"endDate\":\"%s\",\"pageNo\":1,\"pageSize\":20,\"type\":%d,\"startDate\":\"%s\"}",
                todayDate, type, todayDate);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216876");
        headers.put("latitude", "30.203527");
        headers.put("version", "5.56.1");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

