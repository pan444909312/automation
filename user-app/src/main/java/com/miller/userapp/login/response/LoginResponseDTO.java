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
    /**
     * resultCode
     */
    private Integer resultCode;
    /**
     * error
     */
    private String error;
    /**
     * reason
     */
    private String reason;
    /**
     * result
     */
    private ResultDTO result;
    /**
     * currency
     */
    private String currency;
    /**
     * success
     */
    private Boolean success;
    /**
     * nowTime
     */
    private Long nowTime;
    /**
     * queryList
     */
    private QueryListDTO queryList;

    /**
     * ResultDTO
     */
    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        /**
         * accessToken
         */
        private String accessToken;
        /**
         * expiresIn
         */
        private Integer expiresIn;
        /**
         * refreshToken
         */
        private String refreshToken;
        /**
         * userPushToken
         */
        private String userPushToken;
        /**
         * marketToken
         */
        private String marketToken;
        /**
         * shopId
         */
        private Object shopId;
        /**
         * userId
         */
        private Integer userId;
        /**
         * userName
         */
        private String userName;
        /**
         * userPhone
         */
        private String userPhone;
        /**
         * aliPaymentUserId
         */
        private Object aliPaymentUserId;
        /**
         * isGive
         */
        private Object isGive;
        /**
         * isRegister
         */
        private Integer isRegister;
        /**
         * bioAuthToken
         */
        private String bioAuthToken;
        /**
         * isOldUser
         */
        private Integer isOldUser;
        /**
         * isMember
         */
        private Integer isMember;
        /**
         * tokenSrc
         */
        private String tokenSrc;
        /**
         * thirdRefreshToken
         */
        private Object thirdRefreshToken;
    }

    /**
     * QueryListDTO
     */
    @NoArgsConstructor
    @Data
    public static class QueryListDTO {
        /**
         * 使用字段别名
         */
        @JSONField(name = "$1")
        private String $1;
        /**
         * $2
         */
        private String $2;
        /**
         * $3
         */
        private String $3;
        /**
         * $4
         */
        private String $4;
        /**
         * $5
         */
        private String $5;
        /**
         * $6
         */
        private String $6;
        /**
         * $7
         */
        private String $7;
        /**
         * $8
         */
        private String $8;
        /**
         * $9
         */
        private String $9;
        /**
         * $10
         */
        private String $10;
        /**
         * $11
         */
        private String $11;
    }
}
