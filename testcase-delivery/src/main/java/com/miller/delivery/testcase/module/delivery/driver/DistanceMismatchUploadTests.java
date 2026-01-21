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
 * 距离不符上传信息
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JZ4XQTK3CCB5DY28KFFH84Q3",
        scenarioName = "骑手app-收餐码距离不符上传信息",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("距离不符上传信息")
public class DistanceMismatchUploadTests {

    @DisplayName("距离不符上传信息")
    @Test
    void shouldUploadDistanceMismatchInfo() {
        // 前置操作：准备订单数据
        // 注意：这里需要先创建订单，获取 orderSn
        // 为了测试，假设使用一个已存在的订单号，实际使用时需要从数据库或创建订单流程中获取
        String orderSn = "APIFOXTEST1767781374605"; // 占位符，实际使用时需要替换

        // 前置操作：更新订单的取餐码
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(
                "update panda_test.`hp_delivery_order_extra_info` " +
                        "set food_delivery_code = '12345678' " +
                        "where order_sn = '" + orderSn + "'"
        );

        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 距离不符上传信息
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/deliveryCheckDistanceAbnormal";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format(
                "{\"orderSn\":\"%s\"," +
                        "\"url\":\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17514355381461465ff0253a94cd4804e862f38c5f772.jpg\"}",
                orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216733");
        headers.put("latitude", "30.203834");
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

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

