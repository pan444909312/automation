package com.miller.merchant.order.complain.flow;

import com.miller.merchant.constants.BusinessConstant;
import com.miller.merchant.order.complain.request.ComplainOrderRequestDTO;
import com.miller.merchant.order.complain.response.ComplainOrderResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.http.HttpUtils;


/**
 * 流程_商家催骑手
 * <p>
 * 此接口在 app-server 代码工程中，无法引用开发的代码
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 19:45:30
 */
public class ComplainOrderFlow {
    /**
     * 接口_商家催骑手
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/merchant/order/complain";

    /**
     * 商家催骑手
     */
    public static ComplainOrderResponseDTO complainOrder(ComplainOrderRequestDTO complainOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(complainOrderRequestDTO), null, ComplainOrderResponseDTO.class);
    }

}
