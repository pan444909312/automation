package com.miller.merchant.order.delivery.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.delivery.request.MerchantConfirmUserReceivedOrderRequestDTO;
import com.miller.merchant.order.delivery.response.MerchantConfirmUserReceivedOrderResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程-配送中列表-商家点击"用户已取餐"
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 19:45:30
 */
public class MerchantConfirmUserReceivedOrderFlow {
    /**
     * 接口-配送中列表-商家点击"用户已取餐"
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/merchantTerminal/order/updateUndoneOrder";

    /**
     * 商家接单并备餐流程
     *
     */
    public static MerchantConfirmUserReceivedOrderResponseDTO merchantConfirmUserReceivedOrderFlow(
            MerchantConfirmUserReceivedOrderRequestDTO merchantConfirmUserReceivedOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(merchantConfirmUserReceivedOrderRequestDTO),
                null, MerchantConfirmUserReceivedOrderResponseDTO.class);
    }

}
