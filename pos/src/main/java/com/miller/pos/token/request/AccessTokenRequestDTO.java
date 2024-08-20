package com.miller.pos.token.request;

import com.panda.pos.server.api.dto.open.auth.AccessTokenRequest;

import java.io.Serializable;

/**
 * 登录请求数据
 *
 * @author zhangli
 * @version 1.0
 * @since 2024/4/3 11:49:23
 */
public class AccessTokenRequestDTO extends AccessTokenRequest implements Serializable {
    public AccessTokenRequestDTO(String appKey, String appSecret) {
        this.setAppKey(appKey);
        this.setAppSecret(appSecret);
    }

    public AccessTokenRequestDTO() {
    }
}
