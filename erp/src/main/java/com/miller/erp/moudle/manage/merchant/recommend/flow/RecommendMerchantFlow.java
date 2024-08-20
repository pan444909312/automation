package com.miller.erp.moudle.manage.merchant.recommend.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.recommend.request.RecommendMerchantRequestDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_推荐商家
 * 这个是html接口,老的服务，代码工程在 platform 里面。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/12 13:59:46
 */
public class RecommendMerchantFlow {
    /**
     * 接口_推荐商家,这个是html接口,老的服务
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/merchant/recommend.htm";

    /**
     * 流程_推荐商家
     */
    public static String recommendMerchant(RecommendMerchantRequestDTO recommendMerchantRequestDTO){
        ERPLoginFlow.erpLoginByCookie();
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        // RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        return HttpUtils.sendGetRequestReturnBody(uri, RequestUtils.putParams(recommendMerchantRequestDTO),
                RequestUtils.getHeaders(), null);
    }

}