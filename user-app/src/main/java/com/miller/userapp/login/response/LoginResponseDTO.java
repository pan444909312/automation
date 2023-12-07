package com.miller.userapp.login.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应对象
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/6 20:56:16
 */
@NoArgsConstructor
@Data
public class LoginResponseDTO {
    @JSONField(name = "resultCode")
    private Integer resultCode;
    @JSONField(name = "error")
    private String error;
    @JSONField(name = "reason")
    private String reason;
    @JSONField(name = "result")
    private ResultDTO result;
    @JSONField(name = "currency")
    private String currency;
    @JSONField(name = "success")
    private Boolean success;
    @JSONField(name = "nowTime")
    private Long nowTime;
    @JSONField(name = "queryList")
    private QueryListDTO queryList;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @JSONField(name = "accessToken")
        private String accessToken;
        @JSONField(name = "expiresIn")
        private Integer expiresIn;
        @JSONField(name = "refresh_token")
        private String refreshToken;
        @JSONField(name = "userPushToken")
        private String userPushToken;
        @JSONField(name = "marketToken")
        private String marketToken;
        @JSONField(name = "shopId")
        private Object shopId;
        @JSONField(name = "userId")
        private Integer userId;
        @JSONField(name = "userName")
        private String userName;
        @JSONField(name = "userPhone")
        private String userPhone;
        @JSONField(name = "aliPaymentUserId")
        private Object aliPaymentUserId;
        @JSONField(name = "isGive")
        private Object isGive;
        @JSONField(name = "isRegister")
        private Integer isRegister;
        @JSONField(name = "bioAuthToken")
        private String bioAuthToken;
        @JSONField(name = "isOldUser")
        private Integer isOldUser;
        @JSONField(name = "isMember")
        private Integer isMember;
        @JSONField(name = "tokenSrc")
        private String tokenSrc;
        @JSONField(name = "thirdRefreshToken")
        private Object thirdRefreshToken;
    }

    @NoArgsConstructor
    @Data
    public static class QueryListDTO {
        @JSONField(name = "1")
        private String $1;
        @JSONField(name = "2")
        private String $2;
        @JSONField(name = "3")
        private String $3;
        @JSONField(name = "4")
        private String $4;
        @JSONField(name = "5")
        private String $5;
        @JSONField(name = "6")
        private String $6;
        @JSONField(name = "7")
        private String $7;
        @JSONField(name = "8")
        private String $8;
        @JSONField(name = "9")
        private String $9;
        @JSONField(name = "10")
        private String $10;
        @JSONField(name = "11")
        private String $11;
    }
}
