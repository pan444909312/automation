package com.miller.deliveryapp.drivercenter.agreement;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.drivercenter.agreement.flow.AgreementListFlow;
import com.miller.deliveryapp.drivercenter.agreement.request.AgreementListRequestDTO;
import com.miller.deliveryapp.drivercenter.agreement.response.AgreementListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


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
@DisplayName("骑手-骑手银行卡信息获取")
public class AgreementListTests {

    @MethodSource("com.miller.deliveryapp.drivercenter.agreement.provider.AgreementListDataProvider#agreementListDataProvider")
    @ParameterizedTest
    @DisplayName("骑手银行卡信息列表获取")
    void shouldGetHistoryOrderDetailSuccessfully(AgreementListRequestDTO agreementListRequestDTO) {
        AgreementListResponseDTO agreementListResponseDTO = AgreementListFlow.agreementList(agreementListRequestDTO);

        assertThat(agreementListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
