package com.miller.erp.manage.customer.order.list.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.manage.customer.order.list.flow.request.OrderDetailRequestDTO;
import com.miller.erp.manage.customer.order.list.flow.response.OrderDetailResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_客户服务-订单查询-订单详情-代金劵
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/25 17:47:51
 */
public class OrderDetailOfVoucherFlow {

    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/customer/voucher/list";

    /**
     * 根据订单ID查询订单详情中的代金券信息
     */
    public static OrderDetailResponseDTO getVoucherByOrderId(String orderSn) {
        OrderDetailRequestDTO orderDetailRequestDTO = new OrderDetailRequestDTO();
        orderDetailRequestDTO.setOrderSn(orderSn);

        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(orderDetailRequestDTO), null, OrderDetailResponseDTO.class);

    }
}
