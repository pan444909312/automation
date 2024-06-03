package com.miller.demo.dto.external;

import lombok.Data;

/**
 * 登录-响应对象
 *
 * <p>
 *     假设这个是开发代码中的响应对象
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 10:14:12
 */
@Data
public class LoginResponse {
    private User user;
    private String token;
}
