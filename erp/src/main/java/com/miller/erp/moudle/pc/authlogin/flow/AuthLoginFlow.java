package com.miller.erp.moudle.pc.authlogin.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.pc.authlogin.response.AuthLoginResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


public class AuthLoginFlow {



    private static  String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/merchant/auth/login/";


    public static AuthLoginResponseDTO autoLogin(String authCode) {
        uri = uri.concat(authCode);

        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(), null, null, AuthLoginResponseDTO.class);
    }

    public static String getPcToken(String authCode){
        AuthLoginResponseDTO responseDTO = autoLogin(authCode);
        return responseDTO.getData().getToken();
    }
    
    
}