package com.miller.deliveryapp.module.driver.agreement;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.module.driver.agreement.flow.AgreementListFlow;
import com.miller.deliveryapp.module.driver.agreement.request.AgreementListRequestDTO;
import com.miller.deliveryapp.module.driver.agreement.response.AgreementListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.delivery.app.server.domain.dto.agreement.AgreementListReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * 测试用例_骑手协议列表信息获取
 *
 * @author penglulu
 * @version 1.0
 * @since 2024/04/25 10:31:39
 */
// @ApiDoc(value = "http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("骑手-骑手协议列表信息获取")
public class AgreementListTests {

    @MethodSource("agreementListDataProvider")
    @ParameterizedTest
    @DisplayName("骑手协议列表信息获取")
    void shouldGetHistoryOrderDetailSuccessfully(AgreementListReq agreementListReq) {
        AgreementListResponseDTO agreementListResponseDTO = AgreementListFlow.agreementList(agreementListReq);

        assertThat(agreementListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    /**
     * 骑手同意的协议列表获取
     */
    static Stream<Arguments> agreementListDataProvider() {
        AgreementListRequestDTO agreementListRequestDTO = new AgreementListRequestDTO();
        agreementListRequestDTO.setCountry("中国");
        return Stream.of(
                arguments(agreementListRequestDTO)
        );
    }

}
