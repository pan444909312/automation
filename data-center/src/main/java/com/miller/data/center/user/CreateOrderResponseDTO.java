package com.miller.data.center.user;

import com.hungrypanda.app.server.api.res.order.OrderReturnVO;

/**
 * 创建订单响应对象，用于存储数据结构。订单数据是多个业务域都需要使用的公共数据。
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 09:59:18
 */
public class CreateOrderResponseDTO extends BasicResponseDTO<OrderReturnVO> {
    // 测试用例应该基本不需要添加额外的响应信息
}
