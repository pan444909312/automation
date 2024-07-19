package com.miller.userapp.module.pay.notify.airwallex.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Card{
	private String last4;
	private String issuerCountryCode;
	private String bin;
	private String numberType;
	private String cardType;
	@JSONField(name = "is_commercial")
	private boolean isCommercial;
	private String expiryYear;
	private String issuerName;
	private String expiryMonth;
	private String cvcCheck;
	private String fingerprint;
	private String name;
	private String brand;
}