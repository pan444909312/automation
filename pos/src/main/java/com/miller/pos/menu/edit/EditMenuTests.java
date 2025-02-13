package com.miller.pos.menu.edit;

import com.miller.pos.menu.edit.flow.EditMenuFlow;
import com.miller.pos.menu.edit.request.EditMenuRequestDTO;
import com.miller.pos.menu.edit.response.EditMenuResponseDTO;
import com.miller.pos.token.flow.AccessTokenFlow;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;


@EnvTag.Test
@Scenario(
        scenarioID="01J5N1X0M0JW76719QVGXXDXPX",
        scenarioName = "POS 开放接口 - 菜单 - 编辑菜单",
        author = "yuwei@hungrypandagroup.com", developmentTime = 30,
        maintenanceTime = 0 ,
        manualTestTime = 5
)
@DisplayName("pos-Menu-编辑菜单")
public class EditMenuTests {


    @BeforeAll
    void getToken() {
        final String token = AccessTokenFlow.getToken();
        RequestUtils.setToken(token);
    }


    @MethodSource("com.miller.pos.menu.edit.provider.EditMenuDataProvider#editMenuDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_编辑菜单")
    void editMenuSuccessfully(EditMenuRequestDTO editMenuRequestDTO) {
        EditMenuResponseDTO responseDTO = EditMenuFlow.editMenu(editMenuRequestDTO);

        // 验证响应结果
        final Integer code = responseDTO.getCode();
        Assertions.assertThat(code == 0);
    }

}
