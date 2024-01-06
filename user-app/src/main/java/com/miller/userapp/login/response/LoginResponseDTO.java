package com.miller.userapp.login.response;

import com.hungrypanda.app.server.vo.user.UserTokenSimpleVO;
import com.miller.userapp.dto.BasicResponseDTO;
import lombok.Data;

/**
 * 响应对象_登录
 *
 * <p>
 * app-server-impl/src/main/java/com/hungrypanda/app/server/controller/UserController.java
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/6 20:56:16
 */
@Data
public class LoginResponseDTO extends BasicResponseDTO<UserTokenSimpleVO>
{
    // Result 对象内部额外的字段
    private String tokenSrc;
    private String thirdRefreshToken;
}
