package com.miller.merchant.order.evaluate.request;

import lombok.Data;

/**
 * 请求对象-评价订单
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一请求对象。
 * 这里之所以包装了一层是因为对请求字段可能需要二次处理，比如：加密、验签等.
 * 测试用例最终发送请求时需要使用{@link com.miller.merchant.util.RequestUtils}类对请求字段做统一的二次处理。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 14:29:02
 */
@Data
public class EvaluateOrderRequestDTO // extends EvaluateReq
{
    private String orderSn;
    private String replyContent;
}


