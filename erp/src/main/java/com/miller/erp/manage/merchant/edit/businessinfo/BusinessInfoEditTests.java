package com.miller.erp.manage.merchant.edit.businessinfo;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例-编辑商家经营信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/27 16:31:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-编辑商家经营信息")
public class BusinessInfoEditTests {
    @MethodSource("com.miller.erp.manage.merchant.edit.businessinfo.provider.BusinessInfoEditDataProvider#businessInfoEdit")
    @ParameterizedTest
    @DisplayName("编辑商家经营信息流程-正常流程")
    void shouldBusinessInfoEditSuccessfully(BusinessInfoEditRequestDTO businessInfoEditRequestDTO) {
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO = BusinessInfoEditFlow.businessInfoEdit(businessInfoEditRequestDTO);
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

}
