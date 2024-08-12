package com.miller.erp.manage.merchant.finance.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.manage.merchant.finance.request.SaveBillInfoConfigRequestDTO;
import com.miller.erp.manage.merchant.finance.response.SaveBillInfoConfigResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_编辑商家-结算信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 18:59:46
 */
public class SaveBillInfoConfigFlow {
    /**
     * 接口
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/merchant/finance/config/saveBillInfo";

    public static SaveBillInfoConfigResponseDTO saveBillInfoConfig(SaveBillInfoConfigRequestDTO saveBillInfoConfigRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(saveBillInfoConfigRequestDTO), null, SaveBillInfoConfigResponseDTO.class);
    }

}