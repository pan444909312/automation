package com.miller.merchant.login.response;

import com.miller.merchant.dto.BasicResponseDTO;
import com.panda.common.base.AppResult;
import com.panda.merchant.server.api.vo.app.merchant.req.AppMerchantVirtualLoginRespVO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应对象
 *
 * <p>
 * app-server-impl/src/main/java/com/hungrypanda/app/server/controller/UserController.java
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/6 20:56:16
 */
@NoArgsConstructor
@Data
public class LoginResponseDTO extends BasicResponseDTO<AppMerchantVirtualLoginRespVO> {
}
