package com.miller.pos.menu.seq;

import com.miller.pos.menu.list.flow.ListMenuFlow;
import com.miller.pos.menu.list.response.ListMenuResponseDTO;
import com.miller.pos.menu.seq.flow.BatchMenuSeqFlow;
import com.miller.pos.menu.seq.request.BatchMenuSeqRequestDTO;
import com.miller.pos.menu.seq.response.BatchMenuSeqResponseDTO;
import com.miller.pos.token.flow.AccessTokenFlow;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.pos.server.api.dto.open.menu.MenuSeqRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@EnvTag.Test
@Scenario(
        scenarioID="01J5N2G49FNJH4YPS4N5BP1CJ7",
        scenarioName = "POS 开放接口 - 菜单 - 批量修改菜单排序",
        author = "yuwei@hungrypandagroup.com", developmentTime = 30,
        maintenanceTime = 0 ,
        manualTestTime = 5
)
@DisplayName("pos-Menu-批量修改菜单排序")
public class BatchMenuSeqTests {


    @BeforeAll
    void getToken() {
        final String token = AccessTokenFlow.getToken();
        RequestUtils.setToken(token);
    }


    @MethodSource("com.miller.pos.menu.seq.provider.BatchMenuSeqDataProvider#batchMenuSeqDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_批量修改菜单排序")
    void batchMenuSeqTestCases(BatchMenuSeqRequestDTO requestDTO) {

        BatchMenuSeqResponseDTO responseDTO = BatchMenuSeqFlow.batchSeqMenu(requestDTO);
        // 验证响应结果
        final Integer code = responseDTO.getCode();
        Assertions.assertThat(code == 0);
    }

}
