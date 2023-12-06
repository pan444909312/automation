package com.miller.userapp.login.request;

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
     * areaCode
     */
    private String areaCode;
    /**
     * account
     */
    private String account;
    /**
     * password，使用MD5进行加密
     */
    private String password;
    /**
     * cityName
     */
    private String cityName;
    /**
     * type
     */
    private Integer type;
    /**
     * distinctId
     */
    private String distinctId;
}
