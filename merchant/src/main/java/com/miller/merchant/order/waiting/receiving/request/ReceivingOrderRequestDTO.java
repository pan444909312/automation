package com.miller.merchant.order.waiting.receiving.request;

import com.panda.merchant.server.api.vo.app.merchant.req.OrderTerminalStatusUpdateQueryReqVO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求对象_接单并备餐
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一请求对象。
 * 这里之所以包装了一层是因为对请求字段可能需要二次处理，比如：加密、验签等.
 * 测试用例最终发送请求时需要使用{@code RequestUtils}类对请求字段做统一的二次处理。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 19:59:02
 */
public class ReceivingOrderRequestDTO extends OrderTerminalStatusUpdateQueryReqVO {
    // 测试用例需要使用的额外字段可以补充在这里。
}


