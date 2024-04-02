package com.miller.deliveryapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.http.HttpUtils;
import com.panda.common.enums.CountryEnum;
import com.panda.delivery.app.server.common.util.SignGenerateUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 测试接口验签
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/4 15:17:53
 */
public class SignTest {
    private String uri = "https://app-deliverytest.hungrypanda.cn";
    private static String token;

    @Test
    void testLogin() {
        // 发送请求
        JSONObject requestJsonObject = new JSONObject();
        requestJsonObject.put("longitude", 120.21654);
        requestJsonObject.put("latitude", 30.203542);
        requestJsonObject.put("version", "5.19.0");
        requestJsonObject.put("platform", "ANDROID_DELIVERY");
        requestJsonObject.put("type", 3);
        requestJsonObject.put("user-agent", "5.19.0");
        requestJsonObject.put("locale", "zh-CN");
        requestJsonObject.put("operatingsystem", 1);
        requestJsonObject.put("brand", "HUAWEI");
        requestJsonObject.put("uniquetoken", "HUAWEI");
        requestJsonObject.put("apptypeid", 2);
        requestJsonObject.put("countrycode", CountryEnum.CHINA.getCode());
        requestJsonObject.put("devicesafetoken", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");
        requestJsonObject.put("Content-Type", "application/json");

        requestJsonObject.put("authorization", token);
        requestJsonObject.put("_ts", System.currentTimeMillis());

        // 发送请求
        var headers = new HashMap<String, Object>();
        headers.putAll(requestJsonObject.getInnerMap());
        System.out.println(requestJsonObject.getInnerMap());
        String body = "{\n" + "\t\"areaCode\": \"86\",\n" + "\t\"password\":\"" + SignGenerateUtil.encrypt("Test123456", "MD5") + "\",\"account\": \"18733330001\"\n" + "}";
        String responseBody = HttpUtils.sendPostRequestReturnBody(uri + "/api/delivery/app/auth/login", null, headers, body, null);
        token = JSONPath.extract(responseBody, "result.accessToken").toString();
        System.out.println("token:" + token);
    }

    @DisplayName("骑手上线-需要验签")
    @DependsOnMethod("testLogin")
    @Test
    void testOnline() {
        var headers = new HashMap<String, Object>();
        // 公共参数：开始
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("version", "5.19.0");
        headers.put("longitude", "120.21654");
        headers.put("latitude", "30.203542");
        headers.put("brand", "HUAWEI");
        headers.put("locale", "zh-CN");
        headers.put("apptypeid", "2");
        headers.put("countrycode", CountryEnum.CHINA.getCode());
        headers.put("uniquetoken", "HUAWEI");
        headers.put("devicesafetoken", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("authorization", token);
        headers.put("Content-Type", "application/json");
        // 访客ID，现在客户端好像都没有传啊
        // requestJsonObject.put("fpVisitorId", "192.168.1.1");
        // 公共参数：结束

        /*
        验签规则：
        1. 获取所有请求体内容，放入到 JSONObject 对象中。
        2. 将 ：authorization， _ts 添加到 JSONObject。
        3. 调用 SignGenerateUtil.getSign（）方法获取 _sign。
        4. 将 _sign和_ts放到请求头发送给服务端。
         */
        String body = "{\"isOnline\":1}";
        JSONObject jsonObjectBody = new JSONObject();
//        jsonObjectBody.put("isOnline", 1);

//        Gson gson = new Gson();
//        Map requestBody = gson.fromJson(body, Map.class);
//        jsonObjectBody.putAll(requestBody);

        // 替换使用 fastjson 工具类，因为 Gson 会出现转换之后类型变了的问题。比如：1 变成 1.0
        Map requestBody = JSON.parseObject(body, Map.class);
        jsonObjectBody.putAll(requestBody);


        var time = System.currentTimeMillis();
        jsonObjectBody.put("_ts", time);
        jsonObjectBody.put("authorization", token);


        var requestSignatureKey = "ldkai_1ldal#nvhsl*afl3g2akgbvsa";
        var signReal = SignGenerateUtil.getSign(jsonObjectBody, requestSignatureKey);
        headers.put("_sign", signReal);
        headers.put("_ts", time);
        // 发送请求
        String returnBody = HttpUtils.sendPostRequestReturnBody(uri + "/api/delivery/app/driver/onOffline", null, headers, body, null);
        System.out.println(returnBody);
        assertThat(JSONPath.extract(returnBody, "resultCode"), Matchers.is(1000));
    }

}
