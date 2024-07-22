package com.miller.erp.manage.merchant.edit.kp;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.merchant.add.AddMerchantTests;
import com.miller.erp.manage.merchant.edit.kp.flow.AddKPFlow;
import com.miller.erp.manage.merchant.edit.kp.request.AddKPRequestDTO;
import com.miller.erp.manage.merchant.edit.kp.response.AddKPResponseDTO;
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
 * 测试用例_编辑商家-KP信息-添加KP
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/6/23 16:31:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-编辑商家-KP信息-添加KP")
public class AddKPTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }

    @MethodSource("addKPDataProvider")
    @ParameterizedTest
    @DisplayName("编辑商家-KP信息-添加KP_正常流程")
    void shouldAddKPSuccessfully(AddKPRequestDTO AddKPRequestDTO) {
        AddKPResponseDTO AddKPResponseDTO = AddKPFlow.addKP(AddKPRequestDTO);
        assertThat(AddKPResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    static Stream<Arguments> addKPDataProvider() {
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(AddKPTests.class,"AddKP.json");
        AddKPRequestDTO AddKPRequestDTO = JSONUtils.jsonToObject(requestJson, AddKPRequestDTO.class);
        if (AddKPRequestDTO.getShopId() == null)
            AddKPRequestDTO.setShopId(AddMerchantTests.addMerchantResponseMap.get("addMerchantResponseDTO").getData().getShopId());
        return Stream.of(arguments(AddKPRequestDTO));
    }
}
