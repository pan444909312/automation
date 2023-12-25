package com.miller.userapp.order.create.flow;

import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.util.RequestUtils;

/**
 * 流程-创建订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/8 15:45:30
 */
public class CreateOrderFlow {
    /**
     * 创建订单接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/order/create";

    /**
     * 创建订单流程
     *
     * @param createOrderRequestDTO 创建订单请求DTO
     * @return 创建订单响应DTO
     */
    public static CreateOrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        CreateOrderResponseDTO createOrderResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(), RequestUtils.putBodyOfForm(createOrderRequestDTO),
                null, CreateOrderResponseDTO.class);
        // 将订单响应对象存储到缓存中, 包含最重要的订单 ID 字段 orderSn
        CacheUtils.set(TestCaseDataConstant.ORDER_ID_OBJECT_KEY, createOrderResponseDTO);
        return createOrderResponseDTO;
    }

}
