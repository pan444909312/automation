package com.miller.bdm.app.privateShop.templates;

import com.miller.bdm.app.privateShop.templates.flow.GetTemplatesFlow;
import com.miller.bdm.app.privateShop.templates.request.GetTemplatesRequestDTO;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.common.base.Result;
import com.panda.erp.server.dal.dataobject.bdm.HpBdmEversignTemplates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-移动端私海池-获取合同列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-移动端私海池-获取合同列表")
public class GetTemplatesTests {
    @MethodSource("com.miller.bdm.app.privateShop.templates.provider.GetTemplatesDataProvider#GetTemplatesList")
    @ParameterizedTest
    @DisplayName("bdm-移动端私海池-获取合同列表")
    void QueryPrivateShopList(GetTemplatesRequestDTO getTemplatesRequestDTO) {
        Result<List<HpBdmEversignTemplates>> getTemplatesResponseDTO = GetTemplatesFlow.getPageList(getTemplatesRequestDTO);
        assertThat(getTemplatesResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(getTemplatesResponseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        assertThat(getTemplatesResponseDTO.getData()).isNotNull();
    }

}
