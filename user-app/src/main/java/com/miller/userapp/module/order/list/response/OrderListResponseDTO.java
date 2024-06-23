package com.miller.userapp.module.order.list.response;

import com.hungrypanda.app.server.vo.order.OrderProcessListVO;
import com.miller.userapp.dto.BasicResponseDTO;

/**
 * 响应对象_订单列表
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/25 14:39:18
 */
public class OrderListResponseDTO extends BasicResponseDTO<OrderProcessListVO> {
    // 测试用例应该基本不需要添加额外的响应信息
}
