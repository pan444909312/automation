package com.miller.deliveryapp.module.driver.agreement.flow;

import com.miller.deliveryapp.constants.BusinessConstant;
import com.miller.deliveryapp.module.driver.agreement.request.AgreementListRequestDTO;
import com.miller.deliveryapp.module.driver.agreement.response.AgreementListResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 骑手同意的协议列表获取
 *
 * @author penglulu
 * @version 1.0
 * @since 2024/04/25 14:59:46
 */
public class AgreementListFlow {
    /**
     *骑手同意的协议列表获取
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/delivery/app/agreement/list";
    public static AgreementListResponseDTO agreementList(AgreementListRequestDTO agreementListRequestDTO) {

        RequestUtils.getHeaders().put("Content-Type", "application/json");
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(agreementListRequestDTO),
                null, AgreementListResponseDTO.class);
    }

}