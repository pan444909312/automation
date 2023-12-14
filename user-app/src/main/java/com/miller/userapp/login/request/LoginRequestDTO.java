package com.miller.userapp.login.request;

import com.alibaba.fastjson2.annotation.JSONField;
import com.hungrypanda.app.server.api.req.user.UserLoginReq;
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
public class LoginRequestDTO extends UserLoginReq {
}
