package com.miller.erp.manage.merchant.edit.kp.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.manage.merchant.edit.kp.request.AddKPRequestDTO;
import com.miller.erp.manage.merchant.edit.kp.response.AddKPResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_编辑商家-KP信息-添加KP
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/06/23 14:59:46
 */
public class AddKPFlow {
    /**
     * 接口_编辑商家-KP信息-添加KP
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/module/kp/save";

    public static AddKPResponseDTO addKP(AddKPRequestDTO kPRequestDTOAdd) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfJson(kPRequestDTOAdd),
                null, AddKPResponseDTO.class);
    }

}