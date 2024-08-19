package com.miller.erp.moudle.manage.merchant.auditIdentity.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.auditIdentity.request.AuditIdentityMerchantRequestDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_商家管理-商家认证
 * 这个是前后端不分离的接口,老的服务，代码工程在 platform 里面，无法直接引入jar包，这个是一个war包，而且请求参数是字符串，而不是对象
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/13 14:59:46
 */
public class AuditIdentityMerchantFlow {
    /**
     * 接口_商家管理-商家认证
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_BACKUP + "/admin/merchant/auditIdentity.htm";

    /**
     * 流程_商家管理-商家认证
     */
    public static String auditIdentityMerchant(AuditIdentityMerchantRequestDTO auditIdentityMerchantRequestDTO){
        ERPLoginFlow.erpLoginByCookie();
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        // RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        return HttpUtils.sendGetRequestReturnBody(uri, RequestUtils.putParams(auditIdentityMerchantRequestDTO),
                RequestUtils.getHeaders(), null);
    }

}