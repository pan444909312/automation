package com.miller.erp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.service.framework.http.HttpUtils;

public class EncodeUtils {

    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/encryption/crypto";

    /**
     * 输入手机号，返回加密的手机号
     * @param str
     * @return
     */
    public static String encodePhone(String str) {
        ERPLoginFlow.loginByDefaultUser();

        String body = "{\"sceneType\":1,\"text\":\"" + str + "\",\"cryptoType\":1}";


        String resp = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), body,
                null, String.class);

        JSONObject jsonObject = JSON.parseObject(resp);
        String encodeStr = JSONPath.eval(jsonObject, "$.data.content").toString();

        return encodeStr;
    }

}
