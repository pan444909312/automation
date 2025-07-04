package com.miller.erp.moudle.pc.authcode.flow;

import com.alibaba.fastjson.JSON;
import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.request.ERPLoginRequestDTO;
import com.miller.erp.moudle.login.response.ERPLoginResponseDTO;
import com.miller.erp.moudle.manage.merchant.add.request.AddMerchantRequestDTO;
import com.miller.erp.moudle.manage.merchant.add.response.AddMerchantResponseDTO;
import com.miller.erp.moudle.pc.authcode.response.AuthCodeResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;


public class AuthCodeFlow {

    private static String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/login-merchant-server/v1/";


    public static AuthCodeResponseDTO authCode(String shopId) {
        uri = uri.concat(shopId);

        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(), null, null, AuthCodeResponseDTO.class);
    }


    /**
     * 获取 authCode
     * @param shopId
     * @return
     */
    public static String getAuthCode(String shopId) {
        AuthCodeResponseDTO responseDTO = authCode(shopId);
        return responseDTO.getData().getAuthCode();
       }


}