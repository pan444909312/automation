package com.miller.pos.login.request;


import com.panda.pos.server.api.dto.open.auth.AccessTokenRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * 登录请求数据
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/6 19:49:23
 */
@Getter
@Setter
@ToString
public class PosLoginRequestDTO //extends AccessTokenRequest
{
    private String app_key;


    private String app_secret;
}
