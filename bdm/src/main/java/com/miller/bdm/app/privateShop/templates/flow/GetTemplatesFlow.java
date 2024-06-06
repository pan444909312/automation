package com.miller.bdm.app.privateShop.templates.flow;


import com.miller.bdm.app.privateShop.templates.request.GetTemplatesRequestDTO;
import com.miller.bdm.app.privateShop.templates.response.GetTemplatesResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_bdm-移动端私海池-获取合同列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */


public class GetTemplatesFlow {
    /**
     * bdm-移动端私海池-获取合同列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/eversign/getTemplates";

    /**
     * bdm-移动端私海池-获取合同列表
     */
    public static GetTemplatesResponseDTO getPageList(GetTemplatesRequestDTO getTemplatesRequestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(getTemplatesRequestDTO), null, GetTemplatesResponseDTO.class);
    }

}