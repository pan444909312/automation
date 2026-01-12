package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单取餐评价
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JZ4YW4JT85FXNBMS135HDC4J",
        scenarioName = "骑手app-订单取餐评价",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("订单取餐评价")
public class TakeMealShopCommentTests {

    // 注意：需要在实际使用时替换为真实的订单号
    private static final String ORDER_SN = "5711469418740708521811"; // 请从质量平台或实际业务中获取订单号

    @DisplayName("订单取餐评价")
    @Test
    void shouldTakeMealShopComment() throws InterruptedException {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 删除评价信息（清理数据）
        deleteCommentFromDatabase();

        // 3) 订单取餐评价
        takeMealShopComment(driverAccessToken, ORDER_SN);

        // 4) 延迟1秒
        Thread.sleep(1000);

        // 5) 订单取餐评价--订单号为空
        takeMealShopCommentWithEmptyOrderSn(driverAccessToken);

        // 6) 延迟1秒
        Thread.sleep(1000);

        // 7) 订单取餐评价--未登录
        takeMealShopCommentWithoutLogin(ORDER_SN);
    }

    /**
     * 订单取餐评价
     */
    private void takeMealShopComment(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/takeMealShopComment";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    /**
     * 订单取餐评价--订单号为空
     */
    private void takeMealShopCommentWithEmptyOrderSn(String driverAccessToken) {
        // 删除评价信息（清理数据）
        deleteCommentFromDatabase();
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/takeMealShopComment";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"orderSn\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 订单取餐评价--未登录
     */
    private void takeMealShopCommentWithoutLogin(String orderSn) {
        // 删除评价信息（清理数据）
        deleteCommentFromDatabase();
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/takeMealShopComment";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization或设置为无效值
        
        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
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
     * 从数据库删除评价信息
     */
    private void deleteCommentFromDatabase() {
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(
                String.format("DELETE FROM `panda_test`.`hp_delivery_take_meal_comment` WHERE order_sn='%s'", ORDER_SN));
    }

    /**
     * 创建骑手app请求头（IOS平台）
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("latitude", "30.203602");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.64.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("longitude", "120.216758");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept", "*/*");
        headers.put("Cookie", "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221398714164%22%2C%22first_id%22%3A%22196f136f4caa4b-07e7232fd42cab-37176e50-370944-196f136f4cbc89%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk2ZjEzNmY0Y2FhNGItMDdlNzIzMmZkNDJjYWItMzcxNzZlNTAtMzcwOTQ0LTE5NmYxMzZmNGNiYzg5IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiMTM5ODcxNDE2NCJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%221398714164%22%7D%7D");

        headers.put("content-type", "application/json");
        return headers;
    }
}

