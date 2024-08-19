package com.miller.erp.moudle.manage.merchant.auth.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.manage.merchant.auth.request.MerchantAuthRequestDTO;
import com.miller.erp.moudle.manage.merchant.auth.response.MerchantAuthResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_商家认证
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 19:59:46
 */
public class MerchantAuthFlow {
    /**
     * 接口
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/merchant/auth";

    public static MerchantAuthResponseDTO merchantAuth(MerchantAuthRequestDTO merchantAuthRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(merchantAuthRequestDTO), null, MerchantAuthResponseDTO.class);
    }

}