package com.miller.erp.moudle.manage.merchant.product.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.product.request.CopyOtherShopProductRequestDTO;
import com.miller.erp.moudle.manage.merchant.product.response.CopyOtherShopProductResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_编辑商家-商品-复制其他店铺商品到本店铺
 * 这个是html接口,老的服务，代码工程在 platform 里面。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 13:59:46
 */
public class CopyOtherShopProductFlow {
    /**
     * 接口_这个是html接口，老的服务，前后端不分离
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/merchant/copyOtherShopProduct.htm";

    /**
     * 流程_编辑商家-商品-复制其他店铺商品到本店铺
     */
    public static CopyOtherShopProductResponseDTO copyOtherShopProduct(CopyOtherShopProductRequestDTO copyOtherShopProductRequestDTO) {
        ERPLoginFlow.erpLoginByCookie();
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(copyOtherShopProductRequestDTO),
                null, CopyOtherShopProductResponseDTO.class);
    }

}