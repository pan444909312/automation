package com.miller.bdm.app.privateShop.signsuccess.flow;


import com.miller.bdm.app.privateShop.signsuccess.request.SignRequestDTO;
import com.miller.bdm.app.privateShop.signsuccess.response.SignResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_bdm-签约成功 合同数据存储 hp_bdm_eversign_field_value
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */


public class SignFlow {
    /**
     * bdm-发起签约
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/eversign/field/addHpBdmEversignFieldValue";

    /**
     * bdm-发起签约
     */
    public static SignResponseDTO getPageList(SignRequestDTO signRequestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(signRequestDTO), null, SignResponseDTO.class);
    }


}