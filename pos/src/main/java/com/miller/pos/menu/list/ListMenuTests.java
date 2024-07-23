package com.miller.pos.menu.list;

import com.miller.pos.menu.add.flow.AddMenuFlow;
import com.miller.pos.menu.add.request.AddMenuRequestDTO;
import com.miller.pos.menu.add.response.AddMenuResponseDTO;
import com.miller.pos.menu.list.flow.ListMenuFlow;
import com.miller.pos.menu.list.response.ListMenuResponseDTO;
import com.miller.pos.token.flow.AccessTokenFlow;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.provider.MethodSource;

@EnvTag.Test
@TestFramework
@DisplayName("pos-Menu-菜单列表")
public class ListMenuTests {


    @BeforeAll
    void getToken() {
        final String token = AccessTokenFlow.getToken();
        RequestUtils.setToken(token);
    }


    @Test
    @DisplayName("正常流程_菜单列表")
    void listMenuTestCases() {
        ListMenuResponseDTO responseDTO = ListMenuFlow.listMenu(false);
        // 验证响应结果
        final Integer code = responseDTO.getCode();
        Assertions.assertThat(code == 0);
    }

}
