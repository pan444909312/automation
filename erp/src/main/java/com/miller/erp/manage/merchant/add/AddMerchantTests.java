package com.miller.erp.manage.merchant.add;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.merchant.add.flow.AddMerchantFlow;
import com.miller.erp.manage.merchant.add.request.AddMerchantRequestDTO;
import com.miller.erp.manage.merchant.add.response.AddMerchantResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.ResourceUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 测试用例_新增商家-基础信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/06/22 15:31:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-商家管理-商家列表-新增商家-基础信息")
public class AddMerchantTests {

    public static Map<String, AddMerchantResponseDTO> addMerchantResponseMap = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }

    @Disabled
    @MethodSource("addMerchantForShopCardOfSecondVersionDataProvider")
    @ParameterizedTest
    @DisplayName("商家管理-商家列表-新增商家-基础信息_正常流程")
    void shouldAddMerchantSuccessfully(AddMerchantRequestDTO addMerchantRequestDTO) {
        AddMerchantResponseDTO addMerchantResponseDTO = AddMerchantFlow.addMerchant(addMerchantRequestDTO);
        assertThat(addMerchantResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        // 将响应结果存储起来提供给下一个测试用例使用
        addMerchantResponseMap.putIfAbsent("addMerchantResponseDTO", addMerchantResponseDTO);
    }


    /**
     * 东东测试商家·商卡二期·自动化测试·数据
     */
    static Stream<Arguments> addMerchantForShopCardOfSecondVersionDataProvider() {
        // Given
        AddMerchantRequestDTO addMerchantRequestDTO = JSONUtils.jsonToObject(
                // 读取测试用例数据
                new ResourceUtils().readTestCaseDataFromResourcesPath(AddMerchantTests.class, "AddMerchant.json"),
                // 转换为对象
                AddMerchantRequestDTO.class);
        // 商家中文名称不能重复
        addMerchantRequestDTO.getBaseInfo().getOperationNameList()
                // 获取 "lang": "CH" 对象
                .stream().filter(item -> item.getLang().equalsIgnoreCase("CN")).findFirst().orElseThrow().getSortList()
                // 设置中文名称
                .stream().filter(item -> item.getOperationName().name().equalsIgnoreCase("NAME")).findFirst().orElseThrow()
                // 名称从配置文件读取
                .setValue(new PropertiesUtils().getProperty(AddMerchantTests.class, "erp.merchant.chinese.name.prefix") +
                        // 名称添加时间戳的后6位，避免重复
                        String.valueOf(System.currentTimeMillis()).substring(7, 13)
                );
        // 英文名称不能重复
        addMerchantRequestDTO.getBaseInfo().getOperationNameList()
                // 获取 "lang": "EN" 对象
                .stream().filter(item -> item.getLang().equalsIgnoreCase("EN")).findFirst().orElseThrow().getSortList()
                // 设置英文名称
                .stream().filter(item -> item.getOperationName().name().equalsIgnoreCase("NAME")).findFirst().orElseThrow()
                // 名称从配置文件读取
                .setValue(new PropertiesUtils().getProperty(AddMerchantTests.class, "erp.merchant.english.name.prefix") +
                        // 名称添加时间戳的后6位，避免重复
                        String.valueOf(System.currentTimeMillis()).substring(7, 13));
        return Stream.of(arguments(addMerchantRequestDTO));
    }

}
