package com.miller.userapp.login.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 登录请求数据
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/6 19:49:23
 */
@NoArgsConstructor
@Data
public class LoginRequestDTO {
    /**
     * 区号。
     */
    @JSONField(name = "areaCode")
    private String areaCode;

    /**
     * 账号。
     */
    @JSONField(name = "account")
    private String account;

    /**
     * 密码需要 MD5 加密
     */
    @JSONField(name = "password")
    private String password;

    /**
     * 城市名称。
     */
    @JSONField(name = "cityName")
    private String cityName;

    /**
     * 登陆类型：1：验证码登陆 2：密码登陆
     */
    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "distinctId")
    private String distinctId;
}
