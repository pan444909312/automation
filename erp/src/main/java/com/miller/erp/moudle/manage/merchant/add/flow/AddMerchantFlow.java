package com.miller.erp.moudle.manage.merchant.add.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.manage.merchant.add.request.AddMerchantRequestDTO;
import com.miller.erp.moudle.manage.merchant.add.response.AddMerchantResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_添加商家
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/06/22 14:59:46
 */
public class AddMerchantFlow {
    /**
     * 接口_新增商家
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/module/save";

    /**
     * 流程_新增商家-基础信息
     *
     */
    public static AddMerchantResponseDTO addMerchant(AddMerchantRequestDTO addMerchantRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(addMerchantRequestDTO),
                null, AddMerchantResponseDTO.class);
    }

}