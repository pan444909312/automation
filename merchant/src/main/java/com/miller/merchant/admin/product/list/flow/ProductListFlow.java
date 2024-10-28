package com.miller.merchant.admin.product.list.flow;

import com.alibaba.fastjson.JSONObject;
import com.miller.merchant.admin.config.AdminDefaultConfig;
import com.miller.merchant.admin.product.list.request.ProductListDTO;
import com.miller.merchant.login.request.MerchantLoginRequestDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class ProductListFlow {

    private static final String uri = AdminDefaultConfig.DOMAIN.concat("/admin/merchant/merchantProduct.htm");


    private static Map<String, Object> list(ProductListDTO reqDto) {


//      TODO 还没完成  return RequestUtils.sendGetRequest(uri, params);

        return new HashMap<>();
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"code\": 1,\n" +
                "    \"message\": \"成功\",\n" +
                "    \"data\": {\n" +
                "        \"token\": \"" + 22 + "\",\n" +
                "        \"manager\": {\n" +
                "            \"userId\": 1748,\n" +
                "            \"userName\": \"dongdong_test\",\n" +
                "            \"userNick\": \"单东东\",\n" +
                "            \"userPic\": \"\",\n" +
                "            \"userTelphone\": \"15606690056\",\n" +
                "            \"passwordUpdateTime\": null\n" +
                "        },\n" +
                "        \"showManagerBinding\": false\n" +
                "    },\n" +
                "    \"currencySymbol\": null,\n" +
                "    \"sql\": null,\n" +
                "    \"queryList\": null\n" +
                "}";

        String s = JSONObject.toJSONString(json);
        System.out.println(s);
    }


}
