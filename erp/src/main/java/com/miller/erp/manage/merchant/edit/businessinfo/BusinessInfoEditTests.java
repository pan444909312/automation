package com.miller.erp.manage.merchant.edit.businessinfo;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.merchant.add.AddMerchantTests;
import com.miller.erp.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
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
 * 测试用例_编辑商家经营信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/27 16:31:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-编辑商家-经营信息")
public class BusinessInfoEditTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }

    @MethodSource("businessInfoEditDataProvider")
    @ParameterizedTest
    @DisplayName("编辑商家-经营信息流程_正常流程")
    void shouldBusinessInfoEditSuccessfully(BusinessInfoEditRequestDTO businessInfoEditRequestDTO) {
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO = BusinessInfoEditFlow.businessInfoEdit(businessInfoEditRequestDTO);
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    static Stream<Arguments> businessInfoEditDataProvider() {
        String requestJson = ResourceUtils.readTestCaseDataFromResourcesPath("UpdateMerchantOfInfo.json");
        BusinessInfoEditRequestDTO businessInfoEditRequestDTO = JSONUtils.jsonToObject(requestJson, BusinessInfoEditRequestDTO.class);
        if (businessInfoEditRequestDTO.getShopId() == null)
            businessInfoEditRequestDTO.setShopId(AddMerchantTests.addMerchantResponseMap.get("addMerchantResponseDTO").getData().getShopId());
        return Stream.of(arguments(businessInfoEditRequestDTO));
    }
}
