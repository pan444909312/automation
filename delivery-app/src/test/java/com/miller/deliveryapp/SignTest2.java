package com.miller.deliveryapp;

import com.alibaba.fastjson.JSONObject;
import com.panda.common.util.HttpUtil;
import com.panda.delivery.app.server.common.util.SignGenerateUtil;
import com.panda.iam.server.api.constant.CountryEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口验签测试
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/5 16:28:41
 */
public class SignTest2 {

    private static String uri = "https://app-deliverytest.hungrypanda.cn";

    public static void main(String[] args) {
        String token = "84e193434509a2dc2f476e59b8bf9bf7";
        Map<String,String> headers = new HashMap<String, String>();
        // 公共参数：开始
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("version", "5.19.0");
        headers.put("longitude", "120.21654");
        headers.put("latitude", "30.203542");
        headers.put("brand", "HUAWEI");
        headers.put("locale", "zh-CN");
        headers.put("apptypeid", "2");
        headers.put("countrycode", CountryEnum.CN.getCode());
        headers.put("uniquetoken", "HUAWEI");
        headers.put("devicesafetoken", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("authorization",token);
        headers.put("Content-Type","application/json");


        // {"continueDown":0,"isOnline":0}
        JSONObject jsonObjectBody = new JSONObject();
        jsonObjectBody.put("continueDown",0);
        jsonObjectBody.put("isOnline",0);
        String body = jsonObjectBody.toJSONString();

        long time = System.currentTimeMillis();

        jsonObjectBody.put("authorization", token);
        jsonObjectBody.put("_ts", time);

        String signReal = SignGenerateUtil.getSign(jsonObjectBody, "ldkai_1ldal#nvhsl*afl3g2akgbvsa");
        headers.put("_sign", signReal);
        headers.put("_ts", time+"");

        String returnBody = HttpUtil.doPost(uri+"/api/delivery/app/driver/onOffline",  headers, body);

        System.out.println(time);
        System.out.println(signReal);
        System.out.println("结果"+returnBody);
    }

}
