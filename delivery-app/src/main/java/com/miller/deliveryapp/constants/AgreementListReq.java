package com.miller.deliveryapp.constants;

import lombok.Data;

/**
 * @Author: panjuxiang
 * @Since: 2025/7/8
 */
@Data
public class AgreementListReq {

    private String country;

    private String agreementNo;

    private Byte operation;
}
