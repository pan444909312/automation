package com.miller.bdm.app.privateShop.formula.flow;


import com.miller.bdm.app.privateShop.formula.request.GetFormulaListRequestDTO;
import com.miller.bdm.app.privateShop.formula.response.GetFormulaListResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_bdm-移动端私海池-普通订单佣金结算公式下拉列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */


public class GetFormulaListFlow {
    /**
     * bdm-移动端私海池-普通订单佣金结算公式下拉列表
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/bdm/merchant/getFormulaList";

    /**
     * bdm-移动端私海池-普通订单佣金结算公式下拉列表
     */
    public static GetFormulaListResponseDTO getPageList(GetFormulaListRequestDTO getFormulaListRequestDTO) {
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(getFormulaListRequestDTO), null, GetFormulaListResponseDTO.class);
    }

}