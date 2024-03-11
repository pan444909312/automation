package com.miller.userapp.pay.checkout.flow;

import com.hungrypanda.app.server.common.enums.payment.PaymentEnum;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.pay.checkout.request.PaymentPatternCheckOutRequestDTO;
import com.miller.userapp.pay.checkout.response.PaymentPatternCheckOutResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程_收银台检出app请求,获取支付方式列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/23 14:37:07
 */
public class PaymentPatternCheckOutFlow {
    /**
     * 获取支付方式接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/payment/v2/checkOut/paymentPattern";


    /**
     * 收银台检出app请求,获取支付方式列表
     *
     * @param payByBalanceRequestDTO 收银台检出app请求对象
     * @return 订单支付详情，合单的明细
     */
    public static PaymentPatternCheckOutResponseDTO getPaymentPatternCheckOut(PaymentPatternCheckOutRequestDTO payByBalanceRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode","SG");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(payByBalanceRequestDTO), null, PaymentPatternCheckOutResponseDTO.class);
    }

    /**
     * 根据订单ID，查询出合单ID
     *
     * @param orderSn 普通订单ID
     * @return 合单ID
     */
    public static String getOrderCombineSn(String orderSn) {
        return getOrderCombineInfo(orderSn).getResult().getCheckStandCombinedDTO().getOrderCombinedSn();
    }

    /**
     * 根据订单ID查询合单信息
     *
     * @param orderSn 普通订单ID
     * @return {@link PaymentPatternCheckOutResponseDTO }
     */
    public static PaymentPatternCheckOutResponseDTO getOrderCombineInfo(String orderSn) {
        // 查询合单ID
        var paymentPatternCheckOutRequestDTO = new PaymentPatternCheckOutRequestDTO();
        paymentPatternCheckOutRequestDTO.setOrderSn(orderSn);
        paymentPatternCheckOutRequestDTO.setPaymentType(PaymentEnum.ORDER_PAY.getValue());
        return getPaymentPatternCheckOut(paymentPatternCheckOutRequestDTO);
    }

}
