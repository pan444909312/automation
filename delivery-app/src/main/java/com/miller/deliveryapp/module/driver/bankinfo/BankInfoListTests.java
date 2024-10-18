package com.miller.deliveryapp.module.driver.bankinfo;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.module.driver.bankinfo.flow.BankInfoListFlow;
import com.miller.deliveryapp.module.driver.bankinfo.request.BankInfoListRequestDTO;
import com.miller.deliveryapp.module.driver.bankinfo.response.BankInfoListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * 测试用例_骑手银行卡信息获取
 *
 * @author penglulu
 * @version 1.0
 * @since 2024/04/25 10:31:39
 */
// @ApiDoc(value = "http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("骑手-银行卡列表信息获取")
public class BankInfoListTests {
     //
    @MethodSource("bankInfoListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_骑手银行卡列表信息获取")
    void shouldGetBankInfoListSuccessfully(BankInfoListRequestDTO bankInfoListRequestDTO) {
        BankInfoListResponseDTO bankInfoListResponseDTO = BankInfoListFlow.bankInfoList(bankInfoListRequestDTO);
        assertThat(bankInfoListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    /**
     * 骑手银行卡信息用例数据提供者
     */
    static Stream<Arguments> bankInfoListDataProvider() {
        BankInfoListRequestDTO bankInfoListRequestDTO = new BankInfoListRequestDTO();
        return Stream.of(
                arguments(bankInfoListRequestDTO)
        );
    }

}
