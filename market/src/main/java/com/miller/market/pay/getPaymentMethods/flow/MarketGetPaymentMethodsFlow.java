package com.miller.market.pay.getPaymentMethods.flow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.pay.getPaymentMethods.response.MarketGetPaymentMethodsResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * PaymentMethods查询接口
 *
 */
public class MarketGetPaymentMethodsFlow {
    /**
     * PaymentMethods查询接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/getPaymentMethods";

    public static MarketGetPaymentMethodsResponseDTO getPaymentMethods() {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                // 无请求参数
                RequestUtils.putBodyOfJson(RequestUtils.getHeaders()), null, MarketGetPaymentMethodsResponseDTO.class);
    }


}