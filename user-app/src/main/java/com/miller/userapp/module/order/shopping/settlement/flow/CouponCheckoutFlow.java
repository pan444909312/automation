package com.miller.userapp.module.order.shopping.settlement.flow;

import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.order.shopping.settlement.request.CouponCheckOutRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.CouponCheckoutResponseDTO;
import com.miller.userapp.util.RequestUtils;

public class CouponCheckoutFlow {
    private final static String uri=BusinessConstant.DOMAIN+"/api/user/voucher/order/checkOut";

    public static CouponCheckoutResponseDTO couponCheckoutFlow(CouponCheckOutRequestDTO couponCheckOutRequestDTO){
        RequestUtils.getHeaders().put("content-type","application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri,null,RequestUtils.getHeaders(),RequestUtils.putBodyOfJson(couponCheckOutRequestDTO),null, CouponCheckoutResponseDTO.class);
    }
}
