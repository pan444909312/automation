package com.miller.erp.manage.merchant.edit.additional;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.merchant.add.AddMerchantTests;
import com.miller.erp.manage.merchant.edit.additional.flow.AdditionalInfoEditFlow;
import com.miller.erp.manage.merchant.edit.additional.request.AdditionalInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.additional.response.AdditionalInfoEditResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.ResourceUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * 测试用例_编辑商家-补充信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/06/23 13:31:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-编辑商家-补充信息")
public class AdditionalInfoEditTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }

    @MethodSource("additionalInfoEditDataProvider")
    @ParameterizedTest
    @DisplayName("编辑商家-补充信息_正常流程")
    void shouldAdditionalInfoEditSuccessfully(AdditionalInfoEditRequestDTO additionalInfoEditRequestDTO) {
        AdditionalInfoEditResponseDTO additionalInfoEditResponseDTO = AdditionalInfoEditFlow.additionalInfoEdit(additionalInfoEditRequestDTO);
        assertThat(additionalInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    static Stream<Arguments> additionalInfoEditDataProvider() {
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(AdditionalInfoEditTests.class,"AdditionalInfoEdit.json");
        AdditionalInfoEditRequestDTO additionalInfoEditRequestDTO = JSONUtils.jsonToObject(requestJson, AdditionalInfoEditRequestDTO.class);
        if (additionalInfoEditRequestDTO.getShopId() == null)
            additionalInfoEditRequestDTO.setShopId(AddMerchantTests.addMerchantResponseMap.get("addMerchantResponseDTO").getData().getShopId());
        return Stream.of(arguments(additionalInfoEditRequestDTO));
    }
}
