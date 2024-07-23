package com.miller.pos.menu.add;

import com.miller.pos.menu.add.flow.AddMenuFlow;
import com.miller.pos.menu.add.request.AddMenuRequestDTO;
import com.miller.pos.menu.add.response.AddMenuResponseDTO;
import com.miller.pos.token.flow.AccessTokenFlow;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


@EnvTag.Test
@TestFramework
@DisplayName("pos-Menu-新增菜单")
public class AddMenuTests {


    @BeforeAll
    void getToken() {
        final String token = AccessTokenFlow.getToken();
        RequestUtils.setToken(token);
    }


    @MethodSource("com.miller.pos.menu.add.provider.AddMenuDataProvider#addMenuDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_新增菜单")
    void addMenuSuccessfully(AddMenuRequestDTO addMenuRequestDTO) {
        AddMenuResponseDTO responseDTO = AddMenuFlow.addMenu(addMenuRequestDTO);

        // 验证响应结果
        final Integer code = responseDTO.getCode();
        final Long id = responseDTO.getData().getId();
        Assertions.assertThat(code == 0);
        Assertions.assertThat(id > 0L);
    }

}
