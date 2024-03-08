package com.miller.userapp.pay.card.flow;

import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.pay.card.request.GetPaymentMethodsRequestDTO;
import com.miller.userapp.pay.card.response.GetPaymentMethodsResponseDTO;
import com.miller.userapp.util.RequestUtils;

import java.util.List;

public class GetPaymentMethodsFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/getPaymentMethods";

    /**
     * 获取stripe已经绑定的卡
     * @param paymentMethodsInfo
     * @return
     */
    public static GetPaymentMethodsResponseDTO getPaymentMethods(GetPaymentMethodsRequestDTO paymentMethodsInfo) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode","SG");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(paymentMethodsInfo), null, GetPaymentMethodsResponseDTO.class);
    }
    public static List<PaymentMethodInfoDTO> getPaymentMethodList(GetPaymentMethodsRequestDTO paymentMethodsInfo){
        GetPaymentMethodsResponseDTO result = getPaymentMethods(paymentMethodsInfo);
        List<PaymentMethodInfoDTO> paymentMethods = result.getResult().getPaymentMethodList();
        return paymentMethods;
    }
    public static PaymentMethodInfoDTO getPaymentMethod(){
        GetPaymentMethodsRequestDTO getPaymentMethodsRequestDTO = new GetPaymentMethodsRequestDTO();
        List<PaymentMethodInfoDTO> paymentMethodList= getPaymentMethodList(getPaymentMethodsRequestDTO);
        String cardMd5 = MD5Util.string2MD5(PaymentConstant.CARDNUMBER.replaceAll(" ",""));
        PaymentMethodInfoDTO paymentMethod = paymentMethodList.stream().filter(paymentMethodInfo -> paymentMethodInfo.getCardNoMd5().equals(cardMd5)).findAny().orElse(null);
        return paymentMethod;
    }
}
