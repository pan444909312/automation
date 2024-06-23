package com.miller.userapp.module.order.shopping.settlement.response;

import com.hungrypanda.app.server.api.res.order.OrderVirtualDetailVO;
import com.miller.userapp.dto.BasicResponseDTO;

/**
 * 响应对象_结算页(由于历史原因，也叫创建虚单)
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 14:59:18
 */
public class SettlementResponseDTO extends BasicResponseDTO<OrderVirtualDetailVO> {
    // 测试用例应该基本不需要添加额外的响应信息
}
