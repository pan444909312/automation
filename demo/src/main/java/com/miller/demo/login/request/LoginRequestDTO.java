package com.miller.demo.login.request;

import lombok.Data;

/**
 * 登录_请求对象
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 10:10:12
 */
@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
