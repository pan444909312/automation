package com.miller.userapp.module.pay.payment.request;


import lombok.Data;

@Data
public class BTPaymentRequest extends DefaultPaymentRequest {
    private String cardNoMd5;
}
