package com.miller.erp.moudle.service.customer.refund.duty.flow;

import com.miller.erp.constants.BusinessConstantOfERP;
import com.miller.erp.moudle.service.customer.refund.duty.request.DutyRequestDTO;
import com.miller.erp.moudle.service.customer.refund.duty.response.DutyResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;

/**
 * 流程_客户服务-退款审核-定责
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:30:46
 */
public class DutyFlow {
    /**
     * 接口_客户服务-退款审核-定责
     */
    private static final String uri = BusinessConstantOfERP.DOMAIN_TEST_GATEWAY + "/api/erp/refund/confirm/duty";

    /**
     * 流程_根据订单查询出需要定责的数据进行定责。
     * 注意: 定责之后是异步完成
     */
    public static DutyResponseDTO confirmDuty(DutyRequestDTO dutyRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(dutyRequestDTO), null, DutyResponseDTO.class);
    }

}