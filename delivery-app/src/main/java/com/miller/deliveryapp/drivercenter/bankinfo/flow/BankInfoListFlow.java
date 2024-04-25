package com.miller.deliveryapp.drivercenter.bankinfo.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.drivercenter.bankinfo.request.BankInfoListRequestDTO;
import com.miller.deliveryapp.drivercenter.bankinfo.response.BankInfoListResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 骑手银行卡列表信息获取
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:59:46
 */
public class BankInfoListFlow {
    /**
     * 骑手银行卡列表信息获取
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/bank/bankInfoList";

    /**
     * 原生的请求发送工具，包含响应的所有内容
     *
     * @param bankInfoListRequestDTO {@link BankInfoListRequestDTO}
     * @return 响应结构
     */

    public static BankInfoListResponseDTO bankInfoList(BankInfoListRequestDTO bankInfoListRequestDTO) {

        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(bankInfoListRequestDTO),
                null, BankInfoListResponseDTO.class);
    }


}