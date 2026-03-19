package com.miller.delivery.testcase.module.deliveryApp.orderCompletionProcess;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWineTests;
import com.miller.delivery.testcase.utils.DriverOffline;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.SwitchCityCollectionCodeTests.switchCityCollectionCode;
import static com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.SwitchCountryCollectionCodeTests.switchCountryCollectionCode;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 主干流程-骑手抢单-完单【烟酒订单信息核验不一致调度送达】
 *
 * Apifox: docs/d-apifox/todo/骑手抢单-完单流程(烟酒信息核验不一致调度送达） .apifox-cli.json
 */
@Scenario(
        scenarioID = "01K1WZ1RAPK93R7JD1NMNWGHGZ",
        scenarioName = "主干流程-骑手抢单-完单【烟酒订单信息核验不一致调度送达】",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 35)
@DisplayName("骑手抢单-完单流程(烟酒信息核验不一致调度送达）")
public class GrabOrderCompleteAlcoholMismatchTests {

    @DisplayName("主干流程-烟酒核验不一致->调度处理（按Apifox步骤）")
    @Test
    void shouldCompleteGrabOrderAlcoholMismatchFlow() {
        // 1) C侧下单
        CreateInstantOrderWineTests create = new CreateInstantOrderWineTests();
        String userAppOrderSn = create.orderFlow();

        // 2) 骑手登录 & 上线
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("15199010387", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("15199010387",driverAccessToken);
        onOffline(driverAccessToken, true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 3) 司管登录
        String siGuanToken = erpLogin();

        // 4) 新订单列表 -> 抢单
        newOrderList(driverAccessToken);
        grabOrder(driverAccessToken, userAppOrderSn);

        // 5) 待取餐列表 -> 地址指引 -> 商品信息
        waitPickUpList(driverAccessToken);
        merchantAddressInfo(driverAccessToken, userAppOrderSn);
        productInfo(driverAccessToken, userAppOrderSn);
        // 步骤8: 开启到店距离限制和送达距离限制
        //到店和送达开关关闭
        switchCityCollectionCode(siGuanToken, 0,"city_function_on_shop_take_meal_distance");
        switchCityCollectionCode(siGuanToken, 0,"city_function_deliver_distance");
        //报税开关，0关闭，1开启
        switchCountryCollectionCode(siGuanToken, "hp-delivery-server.us.uk.tax.config",0);
        // 6) 到店 -> 未出餐 -> 已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3);

        // 7) 配送中列表 -> 用户手机号 -> 离线地图
        waitDeliveringList(driverAccessToken);
        customerPhone(driverAccessToken, userAppOrderSn);
        offlineMap(driverAccessToken);

        // 8) 烟酒信息核验不一致提交调度
        tobaccoAlcoholCheckResultMismatch(driverAccessToken, userAppOrderSn);

        // 9) 获取recordId（从列表最后一条取）
        String recordId = getLatestTobaccoAlcoholRecordId(siGuanToken);

        // 10) 调度处理返回商家并完成订单
        dispatchTobaccoAlcoholProcess(siGuanToken, recordId, userAppOrderSn);

        // 11) 下线
        onOffline(driverAccessToken, false);
    }
    private void switchMealCollectionCode(String siGuanToken, int switchType,String switchCode) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/sysCityConfig/switch";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);

        String body = String.format("{\"city\":\"杭州市\",\"functionKey\":\"%s\",\"switchType\":%d}", switchCode,switchType);


        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        // apifox未给明确code断言，这里按通用message=成功
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }
    private void newOrderList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/newOrderList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"pageNo\":1,\"pageSize\":10}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void grabOrder(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/grabOrder";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\",\"confirmDiscount\":0}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void waitPickUpList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitPickUpList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"pageNo\":1,\"sortType\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void merchantAddressInfo(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderHelp/merchantAddressInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"shopId\":892716498,\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void productInfo(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/productInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void modifyDeliveryStatus(String driverAccessToken, String orderSn, int operationType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json");

        String body = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0,\"operationType\":%d,\"orderSnList\":[\"%s\"],\"driverArriveType\":0}", orderSn, operationType, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void waitDeliveringList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"pageNo\":1,\"sortType\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void customerPhone(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/customerInfo/customerPhone";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void offlineMap(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/route/plan";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"point\":[{\"type\":1,\"toPoint\":{\"lat\":30.1965002066,\"lon\":120.2165139689},\"fromPoint\":{\"lat\":30.203602097957489,\"lon\":120.21675840570194}},{\"fromPoint\":{\"lat\":30.1965002066,\"lon\":120.2165139689},\"toPoint\":{\"lon\":120.244367,\"lat\":30.183143999999999},\"type\":1}]}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void tobaccoAlcoholCheckResultMismatch(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/tobaccoAlcoholCheckResult";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json;charset=UTF-8");

        String body = String.format("{\"orderSn\":\"%s\",\"reasonType\":1,\"checkResult\":1}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
    }

    private String getLatestTobaccoAlcoholRecordId(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/tobaccoAlcohol/taskList";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        String body = "{\"pageNo\":1,\"pageSize\":10,\"taskNewType\":1,\"city\":\"杭州市\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");

        int totalNumber = Integer.parseInt(TestCaseHelpful.extractValue(responseBody, "$.data.totalNumber").toString());
        int idx = totalNumber - 1;
        return TestCaseHelpful.extractValue(responseBody, "$.data.list[" + idx + "].recordId").toString();
    }

    private void dispatchTobaccoAlcoholProcess(String siGuanToken, String recordId, String orderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/tobaccoAlcohol/process";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        String body = String.format("{\"recordId\":%s,\"subOrderSn\":\"%s\",\"taskNewType\":1,\"deliveryTimeout\":\"0min\",\"content\":\"烟酒订单无法送达,原因:用户ID与订单不一致\",\"driverName\":\"自动化测试【勿动】\",\"taskNo\":%s}",
                recordId, orderSn, recordId);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void onOffline(String driverAccessToken, boolean online) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = online ? "{\"isOnline\":1}" : "{\"isOnline\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216758");
        headers.put("latitude", "30.203602");
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
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json");
        return headers;
    }

    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}
