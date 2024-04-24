package com.miller.merchant.summi.order.complete.response;

import com.miller.merchant.dto.BasicResponseDTO;
import com.panda.merchant.server.api.vo.app.merchant.resp.OrderTerminalRespDTO;

/**
 * 订单列表响应对象
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 *
 * @author yuwei
 * @version 1.0
 * @since 2024/04/24 15:45:30
 */
public class OrderCompleteListResponseDTO extends BasicResponseDTO<OrderTerminalRespDTO> {
    // 测试用例应该基本不需要添加额外的响应信息
}
