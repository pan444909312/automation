package com.miller.userapp.order.create.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.order.create.request.CouponSubmitOrderRequestDTO;
import com.miller.userapp.order.create.response.CouponSubmitOrderResponsetDTO;
import com.miller.userapp.util.RequestUtils;

public class CouponSubmitOrderFlow {
    private final static String uri=BusinessConstant.DOMAIN+"/api/user/voucher/order/submit";

    public static CouponSubmitOrderResponsetDTO couponCheckoutFlow(CouponSubmitOrderRequestDTO couponSubmitOrderRequestDTO){
        RequestUtils.getHeaders().put("content-type","application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),RequestUtils.putBodyOfJson(couponSubmitOrderRequestDTO),null, CouponSubmitOrderResponsetDTO.class);
    }
}
