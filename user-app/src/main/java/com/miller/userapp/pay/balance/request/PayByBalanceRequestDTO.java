package com.miller.userapp.pay.balance.request;

import com.hungrypanda.app.server.service.pay.pandapay.dto.AppBalancePaymentRequestDTO;
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
public class PayByBalanceRequestDTO extends AppBalancePaymentRequestDTO {
}
