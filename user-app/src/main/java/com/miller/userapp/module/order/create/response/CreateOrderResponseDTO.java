package com.miller.userapp.module.order.create.response;

import com.hungrypanda.app.server.api.res.order.OrderReturnVO;
import com.miller.userapp.dto.BasicResponseDTO;

/**
 * 响应对象_创建订单
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/9 09:59:18
 */
public class CreateOrderResponseDTO extends BasicResponseDTO<OrderReturnVO> {
    // 测试用例应该基本不需要添加额外的响应信息
}
