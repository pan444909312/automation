package com.miller.delivery.testcase.module.deliveryApp.orderCompletionProcess;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.delivery.testcase.utils.DriverOffline;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 主干流程-骑手抢单-完单【C侧下单】
 *
 * Apifox: docs/d-apifox/todo/骑手抢单-抢单场景.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JZ556ZZTHS2RVBPKYZP3YHTW",
        scenarioName = "主干流程-骑手抢单【C侧下单】",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 35)
@DisplayName("骑手抢单-抢单场景")
public class DriverGrabOrderTests {

    @DisplayName("骑手下线不可抢单")
    @Test
    void shouldFailGrabOrderWhenDriverOffline() {
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();

        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010676", "Test1234");
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
//        driverOnOffline(driverAccessToken, 0, 0);

        var responseBody = grabOrderRaw(driverAccessToken, userAppOrderSn);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(100026);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("您当前已下线，请上线后再操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("订单不存在不可抢单")
    @Test
    void shouldFailGrabOrderWhenOrderNotExist() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010676", "Test1234");
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);

        driverOnOffline(driverAccessToken, 1, null);

        var responseBody = grabOrderRaw(driverAccessToken, "11111111");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(5051);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("订单获取失败");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("订单号为空不可抢单")
    @Test
    void shouldFailGrabOrderWhenOrderSnEmpty() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010676", "Test1234");
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
        driverOnOffline(driverAccessToken, 1, null);

        var responseBody = grabOrderRaw(driverAccessToken, "");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("正常可抢单")
    @Test
    void shouldGrabOrderSuccessfully() {
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();

        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010676", "Test1234");
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);

        driverOnOffline(driverAccessToken, 1, null);

        var responseBody = grabOrderRaw(driverAccessToken, userAppOrderSn);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);

    }

    private void driverOnOffline(String driverAccessToken, int isOnline, Integer continueDown) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body;
        if (continueDown == null) {
            body = String.format("{\"isOnline\":%d}", isOnline);
        } else {
            body = String.format("{\"continueDown\":%d,\"isOnline\":%d}", continueDown, isOnline);
        }

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private String grabOrderRaw(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/grabOrder";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\",\"confirmDiscount\":0}", orderSn);
        return TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
    }

    /**
     * 创建骑手app请求头（参考 apifox）
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216878");
        headers.put("latitude", "30.203616");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.64.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("accept", "*/*");
        headers.put("user-agent", "PandaDelivery/5.64.0 (iPhone; iOS 18.3.1; Scale/2.00) OKPOS");
        headers.put("content-type", "application/json");
        return headers;
    }
}

