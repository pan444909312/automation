package com.miller.merchant.order.waiting.lack.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 请求对象-缺菜-退菜
 * <p>
 * 这里我直接引用了开发的代码，减少维护成本，使用统一请求对象。
 * 这里之所以包装了一层是因为对请求字段可能需要二次处理，比如：加密、验签等.
 * 测试用例最终发送请求时需要使用{@code RequestUtils}类对请求字段做统一的二次处理。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 16:29:02
 */
@Data
public class OrderLackProductRequestDTO // extends ProductReturnExchangeReqVO
{
    /**
     * 订单详情id+商品缺菜数量（用"#"隔开）
     */
    private String orderDetailIdsAndNum;

    /**
     * 商品状态
     */
    private Integer productStatus;

    /**
     * 时间模式
     */
    private Integer timeMode;

    private Long shopId;

    /**
     * 操作类型，0-退菜 1-换菜
     */
    private Integer operateType;

    /**
     * 退款金额
     */
    private BigDecimal returnAmount;

    /**
     * 订单号，子订单就传主订单号
     */
    private String orderSn;
}


