package com.miller.userapp.module.home.captcha.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.captcha.request.UserSendVerificationCodeRequest;
import com.miller.userapp.module.home.captcha.response.UserSendVerificationCodeResponse;
import com.miller.userapp.util.AutoSignUtils;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.Map;

public class UserSendVerificationCodeFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/sendVerificationCode";
    public static UserSendVerificationCodeResponse sendVerificationCode(UserSendVerificationCodeRequest request){
        Map<String,Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        RequestUtils.setHeaders(headers);
        AutoSignUtils.signHandler(RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(request));

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(request),
                null, UserSendVerificationCodeResponse.class);
    }

}
