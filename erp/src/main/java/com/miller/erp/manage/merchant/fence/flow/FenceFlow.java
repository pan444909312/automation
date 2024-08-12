package com.miller.erp.manage.merchant.fence.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.merchant.fence.request.FenceRequestDTO;
import com.miller.erp.manage.merchant.fence.response.FenceResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_编辑商家-配送围栏
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 13:59:46
 */
public class FenceFlow {
    /**
     * 接口_编辑商家-配送围栏,这个是html接口,老的服务
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/merchant/fence/save/fenceLatlngChain.htm";

    /**
     * 流程_编辑商家-配送围栏
     */
    public static FenceResponseDTO saveFence(FenceRequestDTO fenceRequestDTO) {
        ERPLoginFlow.erpLoginByCookie();
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        // 这两个参数是从请求头传过去的，在参数 referer 里面
        var value = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/merchant/fence/showInMap.htm" + "?shopId=" + fenceRequestDTO.getShopId() + "&shopType=" + fenceRequestDTO.getShopType();
        RequestUtils.getHeaders().put("Referer", value);

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(fenceRequestDTO),
                null, FenceResponseDTO.class);
    }

}