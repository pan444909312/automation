package com.miller.market.specialOrder.getPageList.flow;

import com.miller.market.constants.SystemConfigConstant;
import com.miller.market.specialOrder.getPageList.request.MarketSpecialOrderListRequestDTO;
import com.miller.market.specialOrder.getPageList.response.MarketSpecialOrderListResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 特殊单订单详情接口
 *
 */
public class MarketSpecialOrderListFlow {
    /**
     * 特殊单订单详情接口
     */
    private static final String uri = SystemConfigConstant.DOMAIN + "/specialOrder/getPageList";

    public static MarketSpecialOrderListResponseDTO specialOderList(MarketSpecialOrderListRequestDTO requestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json;charset=UTF-8");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,RequestUtils.getHeaders(),
                // 所有的请求体请使用带加密的请求体{@code RequestUtils.putBodyOfJson(Object)}
                RequestUtils.putBodyOfJson(requestDTO), null, MarketSpecialOrderListResponseDTO.class);
    }


}