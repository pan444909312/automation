package com.miller.userapp.pay.balance.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

/**
 * 余额(礼品卡)支付 请求
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/13 15:59:34
 */
@NoArgsConstructor
@Data
public class PayByBalanceRequestDTO // extends AppBalancePaymentRequestDTO
{
    /**
     * 外卖订单号、会员订单号、合单单号
     */
    private String orderSn;
    /**
     * 余额支付密码
     */
    private String password;

    /**
     * 前端传递的数值 支付类型
     */
    private String paymentType;
}
