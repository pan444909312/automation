package com.miller.merchant.order.complain;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.complain.flow.ComplainOrderFlow;
import com.miller.merchant.order.complain.request.ComplainOrderRequestDTO;
import com.miller.merchant.order.complain.response.ComplainOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家崔骑手
 *
 * <p>
 * 此接口在 app-server 代码工程中，无法引用开发的代码
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-催骑手")
public class ComplainOrderTests {

    @MethodSource("com.miller.merchant.order.complain.provider.ComplainOrderDataProvider#complainOrder")
    @ParameterizedTest
    @DisplayName("正常流程_商家催骑手")
    void shouldComplainOrderSuccessfully(ComplainOrderRequestDTO complainOrderRequestDTO) {
        ComplainOrderResponseDTO complainOrderResponseDTO = ComplainOrderFlow.complainOrder(complainOrderRequestDTO);
        assertThat(complainOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(complainOrderResponseDTO.getSuccess()).isTrue();
    }
}
