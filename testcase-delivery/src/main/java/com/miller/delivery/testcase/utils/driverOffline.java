package com.miller.delivery.testcase.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miller.delivery.testcase.config.TestcaseConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Calendar;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

public class driverOffline {
    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("获取列表数据")
    @Test
    public void cancelDispatchAndOffline(String driverPhone,String driverAccessToken) {
        // 1) 司管登录获取 token
        String token = erpLogin();

        Calendar calendar = Calendar.getInstance();

        // 2. 获取当天日期
        Date today = calendar.getTime();

        // 3. 获取30天前的日期（修改日历实例，减去30天）
        calendar.add(Calendar.DAY_OF_MONTH, -30); // DAY_OF_MONTH 表示「一个月中的第几天」
        Date thirtyDaysAgo = calendar.getTime();

        // 4. 格式化日期（SimpleDateFormat 非线程安全，避免全局共享）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = sdf.format(today);
        String thirtyDaysAgoStr = sdf.format(thirtyDaysAgo);

        // 5. 输出结果
        System.out.println("当天日期：" + todayStr);
        System.out.println("30天前日期（最近30天开始日期）：" + thirtyDaysAgoStr);



        // 3) 获取列表数据
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/order/listOrderHp";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\n" +
                "    \"arrivedEnd\": null,\n" +
                "    \"arrivedStart\": null,\n" +
                "    \"cityList\": [],\n" +
                "    \"areaIdList\": [],\n" +
                "    \"createEnd\": \"%s\",\r\n" +
                "    \"createStart\": \"%s\",\r\n" +
                "    \"deliveryStatus\": 1,\n" +
                "    \"deliveryType\": null,\n" +
                "    \"driverId\": \"\",\n" +
                "    \"driverPhone\": %s,\n" +
                "    \"orderSn\": null,\n" +
                "    \"orderType\": null,\n" +
                "    \"shopName\": null,\n" +
                "    \"deliverableActionList\": [],\n" +
                "    \"orderArriveMethodList\": [],\n" +
                "    \"additionalBusiness\": null,\n" +
                "    \"deliveryPlatformList\": [],\n" +
                "    \"orderStatus\": 0,\n" +
                "    \"tailOrderAwardFlag\": null,\n" +
                "    \"pageNo\": 1,\n" +
                "    \"pageSize\": 10\n" +
                "}", todayStr, thirtyDaysAgoStr,driverPhone);
        String responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);


            List<String> orderSnList = new ArrayList<>();

            // 使用JSONPath直接提取所有orderSn，避免解析单个item
            Object orderSnObj = TestCaseHelpful.extractValue(responseBody, "$.data.list[*].orderSn");

            if (orderSnObj instanceof List) {
                List<?> list = (List<?>) orderSnObj;
                for (Object orderSn : list) {
                    if (orderSn != null) {
                        orderSnList.add(orderSn.toString());
                    }
                }
            }

            System.out.println("获取到的orderSn列表: " + orderSnList);
            System.out.println("orderSn数量: " + orderSnList.size());

        if (!orderSnList.isEmpty()) {
            //取消配送
            for (String orderSn : orderSnList) {
                cancelDispatch(token,orderSn);
            }

        } else {
            System.out.println("骑手身上无单，不需要取消配送");
        }
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        driverOffline(driverAccessToken);
    }
//
    private void cancelDispatch(String siGuanToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/cancelDispatch";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        var requestBody = String.format("{\"orderSn\":\"%s\"}", userAppOrderSn);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String message = jsonObject.get("message").getAsString();
        System.out.println("Message: " + message);

        if(message.contains("请先处理取餐码异常记录")){
            System.out.println("有个收餐码异常的订单: " + message);
            dispatchFoodDeliveryCodeCompleteMethod(siGuanToken,userAppOrderSn);

        }
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void dispatchFoodDeliveryCodeCompleteMethod(String siGuanToken, String orderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/order/foodDeliveryCodecompleteMethod";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        String body = String.format("{\"orderSn\":\"%s\",\"handleType\":%d}", orderSn, 1);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new java.util.HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("origin", "https://hp-dispatch-admin-f2e-test.hungrypanda.cn");
        headers.put("referer", "https://hp-dispatch-admin-f2e-test.hungrypanda.cn/");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    private void driverOffline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "1");
        headers.put("longitude", "120.217095");
        headers.put("latitude", "30.203565");

        String body = "{\"continueDown\":1,\"isOnline\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        System.out.println("下线接口返回："+responseBody);

        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
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

}
