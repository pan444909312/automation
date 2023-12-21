package com.miller.merchant.order.complain.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.complain.request.ComplainOrderRequestDTO;
import com.miller.merchant.order.complain.response.ComplainOrderResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 商家催骑手流程
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 19:45:30
 */
public class ComplainOrderFlow {
    /**
     * 商家催骑手接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/merchant/order/complain";

    /**
     * 商家催骑手流程
     */
    public static ComplainOrderResponseDTO complainOrder(ComplainOrderRequestDTO complainOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "x-www-form-urlencoded");
        ComplainOrderResponseDTO complainOrderResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(complainOrderRequestDTO),
                null, ComplainOrderResponseDTO.class);

        return complainOrderResponseDTO;
    }

}
