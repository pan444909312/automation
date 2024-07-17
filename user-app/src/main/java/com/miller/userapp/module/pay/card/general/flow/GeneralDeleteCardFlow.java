package com.miller.userapp.module.pay.card.general.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.pay.card.general.request.GeneralDeleteCardRequest;
import com.miller.userapp.module.pay.card.general.request.GeneralQueryCardListRequest;
import com.miller.userapp.module.pay.card.general.response.GeneralDeleteCardResponse;
import com.miller.userapp.module.pay.card.general.response.GeneralQueryCardListResponse;
import com.miller.userapp.util.RequestUtils;

public class GeneralDeleteCardFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/payment/v2/channel/deleteCard";
    public static GeneralDeleteCardResponse deleteCard(GeneralDeleteCardRequest request, String countryCode) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode", countryCode);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(request), null, GeneralDeleteCardResponse.class);
    }
}
