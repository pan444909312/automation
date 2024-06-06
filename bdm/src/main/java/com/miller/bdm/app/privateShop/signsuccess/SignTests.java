package com.miller.bdm.app.privateShop.signsuccess;

import com.miller.bdm.app.privateShop.signsuccess.flow.SignFlow;
import com.miller.bdm.app.privateShop.signsuccess.request.SignRequestDTO;
import com.miller.bdm.app.privateShop.signsuccess.response.SignResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-发起签约
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-发起签约")
public class SignTests {
    @MethodSource("com.miller.bdm.app.privateShop.sign.provider.SignDataProvider#sign")
    @ParameterizedTest
    @DisplayName("bdm-发起签约")
    void sign(SignRequestDTO singRequestDTO) {
        SignResponseDTO signResponseDTO = SignFlow.getPageList(singRequestDTO);
        assertThat(signResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(signResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        assertThat(signResponseDTO.getData()).isNotNull();
        CacheUtils.set(BusinessConstantOfERP.EVERSIGN_DOC,signResponseDTO);
    }



}
