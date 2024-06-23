package com.miller.userapp.module.pay.nopassword.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pay.nopassword.request.NoPasswordStatusRequestDTO;
import com.miller.userapp.module.pay.nopassword.response.NoPasswordStatusResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class NoPasswordStatusFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/payment/v2/noPassword/status";

    public static NoPasswordStatusResponseDTO noPasswordStatusFlow(NoPasswordStatusRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode","AU");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(requestDTO), null, NoPasswordStatusResponseDTO.class);
    }
}
