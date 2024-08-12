package com.miller.erp.manage.merchant.business.config.time.add.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.manage.merchant.business.config.time.add.request.AddShopBusinessTimeRequestDTO;
import com.miller.erp.manage.merchant.business.config.time.add.response.AddShopBusinessTimeResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_编辑商家-营业配置-营业时间-自动营业-添加营业时间
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 16:59:46
 */
public class AddShopBusinessTimeFlow {
    /**
     * 接口_编辑商家-营业配置-营业时间-自动营业-添加营业时间
     */
    private static final String businessTimeUri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/save/shop/business-time";

    public static AddShopBusinessTimeResponseDTO addShopBusinessTime(AddShopBusinessTimeRequestDTO addShopBusinessTimeRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(businessTimeUri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(addShopBusinessTimeRequestDTO), null, AddShopBusinessTimeResponseDTO.class);
    }

}