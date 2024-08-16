package com.miller.userapp.module.home.captcha.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SmsSendVerificationCodeRes {
    @ApiModelProperty("行为验证类型")
    private Integer captchaType;
    @ApiModelProperty("行为验证token")
    private String captchaToken;
}
