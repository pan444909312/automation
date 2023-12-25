package com.miller.userapp.order.confirm.request;

import lombok.Data;

/**
 * 请求对象-用户确认订单已送到
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一请求对象。
 * 这里之所以包装了一层是因为对请求字段可能需要二次处理，比如：加密、验签等.
 * 测试用例最终发送请求时需要使用{@link com.miller.userapp.util.RequestUtils}类对请求字段做统一的二次处理。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 10:59:02
 */
@Data
public class ConfirmOrderStatusRequestDTO {
    // 这个接口的请求字段居然使用字符串来接受。。。
    private String orderSn;
}


