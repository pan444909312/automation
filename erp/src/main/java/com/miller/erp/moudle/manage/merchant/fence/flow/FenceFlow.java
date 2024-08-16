package com.miller.erp.moudle.manage.merchant.fence.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.fence.request.FenceRequestDTO;
import com.miller.erp.moudle.manage.merchant.fence.response.FenceResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

import java.io.UnsupportedEncodingException;

/**
 * 流程_编辑商家-配送围栏.
 * 这个是html接口,老的服务，代码工程在 platform 里面。
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
    public static FenceResponseDTO saveFence(FenceRequestDTO fenceRequestDTO) throws UnsupportedEncodingException {
        ERPLoginFlow.erpLoginByCookie();
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(fenceRequestDTO),
                null, FenceResponseDTO.class);
    }

}