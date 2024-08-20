package com.miller.pos.menu.edit.info;

import com.miller.pos.menu.edit.info.response.InfoMenuResponseDTO;
import com.miller.pos.menu.edit.info.flow.InfoMenuFlow;
import com.miller.pos.token.flow.AccessTokenFlow;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnvTag.Test
@TestFramework
@DisplayName("pos-Menu-菜单详情")
public class InfoMenuTests {


    @BeforeAll
    void getToken() {
        final String token = AccessTokenFlow.getToken();
        RequestUtils.setToken(token);
    }


    @Test
    @DisplayName("正常流程_菜单详情")
    void InfoMenuTestCases() {
        InfoMenuResponseDTO responseDTO = InfoMenuFlow.infoMenu(false);
        // 验证响应结果
        final Integer code = responseDTO.getCode();
        Assertions.assertThat(code == 0);
    }

}
