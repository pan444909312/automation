package com.miller.userapp.order.create.response;

import com.hungrypanda.app.server.api.res.order.OrderReturnVO;
import com.miller.userapp.dto.BasicResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建订单响应对象
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一响应对象。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/9 09:59:18
 */
@NoArgsConstructor
@Data
public class CreateOrderResponseDTO extends BasicResponseDTO<OrderReturnVO> {
//public class CreateOrderResponseDTO extends BasicResponseDTO<OrderReturnVO> {
    // 测试用例应该基本不需要添加额外的响应信息
}
