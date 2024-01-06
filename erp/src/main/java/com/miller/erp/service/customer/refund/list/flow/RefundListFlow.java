package com.miller.erp.service.customer.refund.list.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.service.customer.refund.list.request.RefundListRequestDTO;
import com.miller.erp.service.customer.refund.list.response.RefundListResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_客户服务-退款审核-根据订单查询特殊单ID
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:10:46
 */
public class RefundListFlow {
    /**
     * 接口_客户服务-退款审核-根据订单查询特殊单ID
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/order/refund/list";

    /**
     * 流程_客户服务-退款审核-根据订单查询特殊单ID
     */
    public static RefundListResponseDTO queryRefundList(RefundListRequestDTO refundListRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(refundListRequestDTO), null, RefundListResponseDTO.class);
    }

}