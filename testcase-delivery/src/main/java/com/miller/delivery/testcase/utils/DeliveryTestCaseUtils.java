package com.miller.delivery.testcase.utils;

import com.miller.delivery.testcase.config.TestcaseConfig;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

public class DeliveryTestCaseUtils {
    /**
     * 创建司管和调度的请求头
     */
    public static Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("content-type", "application/json;charset=UTF-8");

        return headers;
    }

    /**
     * 创建骑手app请求头
     */
    public static Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("latitude", "30.203579");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.49.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("longitude", "120.216994");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json");
        return headers;
    }

    /**
     * 上传骑手经纬度
     */
    public static void syncGps(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/home/syncGps";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("longitude", "120.207891");
        headers.put("latitude", "30.197069");



        var requestBody = "{}";

        TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
