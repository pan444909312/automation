package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动列表-包括 骑手冲单,单单奖，上线奖励，新人奖励
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JWGQAVVGXY2KYNKCVXVY4CYF",
        scenarioName = "骑手app-获取我的活动列表页",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("活动列表-包括 骑手冲单,单单奖，上线奖励，新人奖励")
public class ActivityRewardListTests {

    // 注意：需要在实际使用时替换为真实的活动编号
    private static final String ACTIVITY_NO = "483268092383477280"; // 请从质量平台或实际业务中获取
    private static final Integer ACTIVITY_DETAIL_ID = 24576; // 请从质量平台或实际业务中获取

    @DisplayName("我的活动列表")
    @Test
    void shouldGetActivityRewardList() throws InterruptedException {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 我的活动列表
        getActivityRewardList(driverAccessToken);

        // 3) 延迟1秒
        Thread.sleep(1000);

        // 4) 我的活动列表-未登录
        getActivityRewardListWithoutLogin();

        // 5) 查看新人活动详情
        getNoviceActivityOrderData(driverAccessToken);

        // 6) 延迟1秒
        Thread.sleep(1000);

        // 7) 查看新人活动详情--未登录
        getNoviceActivityOrderDataWithoutLogin();

        // 8) 新人活动 关联订单查看
        getNoviceActivityDetails(driverAccessToken);

        // 9) 延迟1秒
        Thread.sleep(1000);

        // 10) 新人活动 关联订单查看-参数为空
        getNoviceActivityDetailsWithEmptyParam(driverAccessToken);
    }

    /**
     * 我的活动列表
     */
    private void getActivityRewardList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/activity/activityRewardList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
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
     * 我的活动列表-未登录
     */
    private void getActivityRewardListWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/activity/activityRewardList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String body = "{}";
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
     * 查看新人活动详情
     */
    private void getNoviceActivityOrderData(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/noviceActivity/noviceActivityOrderData";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForNovice();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"activityNo\":\"%s\"}", ACTIVITY_NO);
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
     * 查看新人活动详情--未登录
     */
    private void getNoviceActivityOrderDataWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/noviceActivity/noviceActivityOrderData";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForNovice();
        // 不设置authorization
        
        String body = "{}";
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
     * 新人活动 关联订单查看
     */
    private void getNoviceActivityDetails(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/noviceActivity/noviceActivityDetails";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForNovice();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"activityDetailId\":%d,\"activityNo\":\"%s\"}", ACTIVITY_DETAIL_ID, ACTIVITY_NO);
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
     * 新人活动 关联订单查看-参数为空
     */
    private void getNoviceActivityDetailsWithEmptyParam(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/noviceActivity/noviceActivityDetails";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForNovice();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"activityDetailId\":%d,\"activityNo\":\"\"}", ACTIVITY_DETAIL_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21674955924935");
        headers.put("latitude", "30.20344076263413");
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

    /**
     * 创建骑手app请求头（用于新人活动相关接口）
     */
    private Map<String, Object> createDriverAppHeadersForNovice() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216806");
        headers.put("latitude", "30.203427");
        headers.put("version", "5.64.0");
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

