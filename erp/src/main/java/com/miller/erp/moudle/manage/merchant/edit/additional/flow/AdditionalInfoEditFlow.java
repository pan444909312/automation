package com.miller.erp.moudle.manage.merchant.edit.additional.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.manage.merchant.edit.additional.request.AdditionalInfoEditRequestDTO;
import com.miller.erp.moudle.manage.merchant.edit.additional.response.AdditionalInfoEditResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_编辑商家-补充信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/06/23 13:59:46
 */
public class AdditionalInfoEditFlow {
    /**
     * 接口_编辑商家-补充信息
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/module/additional/edit";

    /**
     * 流程_编辑商家-补充信息
     *
     * @see com.panda.merchant.server.api.dto.merchant.module.MerchantModuleDTO
     */
    public static AdditionalInfoEditResponseDTO additionalInfoEdit(AdditionalInfoEditRequestDTO additionalInfoEditRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(additionalInfoEditRequestDTO),
                null, AdditionalInfoEditResponseDTO.class);
    }

}