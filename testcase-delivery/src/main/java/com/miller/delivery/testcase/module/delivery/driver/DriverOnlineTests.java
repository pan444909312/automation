package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.common.util.MD5Util;
import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手上线（已完成多case）
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JPP7VKTVXSP7ZGZH7AGW43H4",
        scenarioName = "【主干用例】骑手app-骑手上线/下线",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("骑手上线（已完成多case）")
public class DriverOnlineTests {

    @DisplayName("骑手上线-正常上线")
    @Test
    void shouldOnlineSuccessfully() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 骑手上线前校验状态
        checkStatusBeforeOnline(driverAccessToken);

        // 3) 骑手上线前获取自动接单状态
        getAutoAcceptOrderStatus(driverAccessToken);

        // 4) 司机上线操作-正常上线
        driverOnline(driverAccessToken);

        // 5) 司机下线操作
        driverOffline(driverAccessToken);
    }

    @DisplayName("骑手上线-不可上线，必须到接单商圈才可上线")
    @Test
    void shouldFailOnlineWhenNotInAcceptArea() {
        // 1) 骑手登录获取 token 和 userId
        Map<String, String> loginInfo = driverLogin("13300010015", "Test1234");
        String driverAccessToken = loginInfo.get("accessToken");
        Long newUserId = Long.parseLong(loginInfo.get("userId"));

        // 2) 前置操作：更新骑手必须到接单商圈才可上线（prerequest脚本）
        String updateSql = String.format("UPDATE `panda_test`.`app_config` SET `out_area_online` = 0 WHERE `user_id` = %d", newUserId);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(updateSql);

        // 3) 司机上线操作-不可上线，必须到接单商圈才可上线
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "1");
        headers.put("longitude", "120.217095");
        headers.put("latitude", "30.203565");
        
        String body = "{\"isOnline\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(160033);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("请到您的接单区域上线");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);

        // 5) 后置操作：更新骑手其他商圈也可以上线（test脚本）
        String restoreSql = String.format("UPDATE `panda_test`.`app_config` SET `out_area_online` = 1 WHERE `user_id` = %d", newUserId);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(restoreSql);
    }

    /**
     * 骑手登录并返回token和userId
     */
    private Map<String, String> driverLogin(String account, String password) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = String.format("{\"areaCode\":\"86\",\"password\":\"%s\",\"account\":\"%s\"}",
                MD5Util.string2MD5(password), account);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        String accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        String userId = TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString();
        
        return Map.of("accessToken", accessToken, "userId", userId);
    }

    /**
     * 骑手上线前校验状态
     */
    private void checkStatusBeforeOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/config/checkStatusBeforeOnline";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
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
     * 骑手上线前获取自动接单状态
     */
    private void getAutoAcceptOrderStatus(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/driverAutoAcceptOrder";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
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
     * 司机上线操作
     */
    private void driverOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "1");
        headers.put("longitude", "120.217095");
        headers.put("latitude", "30.203565");
        
        String body = "{\"isOnline\":1}";
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
     * 司机下线操作
     */
    private void driverOffline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("longitude", "120.2168953");
        headers.put("latitude", "30.2035072");
        headers.put("version", "5.55.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "samsung");
        headers.put("uniquetoken", "34ea70ca94766bbc");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        
        String body = "{\"continueDown\":0,\"isOnline\":0}";
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
     * 创建骑手app请求头（Android）
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        return headers;
    }

    /**
     * 创建骑手app请求头（iOS）
     */
    private Map<String, Object> createIOSDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
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
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        return headers;
    }
}

