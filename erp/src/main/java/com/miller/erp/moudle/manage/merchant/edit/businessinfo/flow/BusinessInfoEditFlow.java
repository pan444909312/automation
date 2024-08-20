package com.miller.erp.moudle.manage.merchant.edit.businessinfo.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_编辑商家-经营信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/27 15:59:46
 */
public class BusinessInfoEditFlow {
    /**
     * 接口_编辑商家-经营信息
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/module/business/info/edit";

    /**
     * 流程_编辑商家-经营信息
     */
    public static BusinessInfoEditResponseDTO businessInfoEdit(BusinessInfoEditRequestDTO businessInfoEditRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(businessInfoEditRequestDTO),
                null, BusinessInfoEditResponseDTO.class);
    }

}