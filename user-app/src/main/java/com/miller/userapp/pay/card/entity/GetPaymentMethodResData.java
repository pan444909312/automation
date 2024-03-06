package com.miller.userapp.pay.card.entity;

import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: liuzf
 * @date: 2021/02/20 2:40 下午
 */
@Data
public class GetPaymentMethodResData implements Serializable {
    private static final long serialVersionUID = -7360066634311805031L;

    private List<PaymentMethodInfoDTO> paymentMethodList = new ArrayList<>();
}
