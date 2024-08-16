package com.miller.userapp.module.home.captcha.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungrypanda.message.api.enums.sms.SmsSignatureEnum;
import com.hungrypanda.message.api.enums.sms.SmsTemplateEnum;
import lombok.Data;

@Data
public class UserSendVerificationCodeRequest {
    private Long userId;
    private String ip;
    private String deviceId;
    private String areaCode;
    private String phoneNumber;
    private String verificationCode;
    private Integer sendType;
    private String countryCode;
    private Integer riskPlatform;
    private VoiceLanguageEnum voiceLanguageEnum;
    private SmsTemplateEnum smsTemplateEnum;
    private Integer scene;
    private SmsSignatureEnum smsSignatureEnum;
    private String captchaToken;
    private CaptchaCheckDTO captchaCheckInfo;

    @Data
    public static class CaptchaCheckDTO{
        private Integer captchaType;
        private GeetestCaptchaCheckDTO geetestCheckInfo;
        private ImageCaptchaCheckDTO imageCheckInfo;
        @Data
        public static class GeetestCaptchaCheckDTO{
            @JsonProperty("captcha_id")
            private String captchaId;
            @JsonProperty("lot_number")
            private String lotNumber;
            @JsonProperty("captcha_output")
            private String captchaOutput;
            @JsonProperty("pass_token")
            private String passToken;
            @JsonProperty("gen_time")
            private String genTime;
        }
        @Data
        public static class ImageCaptchaCheckDTO{
            private String checkCode;
        }

    }
}
