package com.miller.erp.util;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.merchant.add.AddMerchantTests;
import com.miller.erp.manage.merchant.add.flow.AddMerchantFlow;
import com.miller.erp.manage.merchant.add.request.AddMerchantRequestDTO;
import com.miller.erp.manage.merchant.add.response.AddMerchantResponseDTO;
import com.miller.erp.manage.merchant.edit.additional.AdditionalInfoEditTests;
import com.miller.erp.manage.merchant.edit.additional.flow.AdditionalInfoEditFlow;
import com.miller.erp.manage.merchant.edit.additional.request.AdditionalInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.additional.response.AdditionalInfoEditResponseDTO;
import com.miller.erp.manage.merchant.edit.businessinfo.BusinessInfoEditTests;
import com.miller.erp.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.framework.util.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 一键自动创建商家。
 * 路径：ERP-商家管理-商家列表-新增商家
 * <p>
 * 创建商家流程：
 * <p>
 * 1.
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/8/7 20:25:24
 */
@Disabled
// @Scenario(scenarioID = "01J4QYGE34BJ7SP84EBBTWEJPT", scenarioName = "一键自动创建模板商家", developmentTime = 6 * 60, maintenanceTime = 0, manualTestTime = 4 * 60)
@Slf4j
public class CreateMerchantTests {
    /**
     * 测试用例数据文件路径
     */
    private String filePath = "quickCreateMerchant/";
    /**
     * 新增商家返回的数据
     */
    private AddMerchantResponseDTO addMerchantResponseDTO;

    /**
     * 这个是调试模式，如果是调试模式，则使用指定的 ShopId。使用场景一般为仅编辑商家信息，想要手动指定 shopId.
     * 注意: 调用模式时注释掉 方法上的 @DependsOnMethod 或 @Scenario
     */
    private final boolean isDebug = true;
    private final long shopIdForDebug = 640561624;

    @BeforeAll
    public static void step01Login() {
        ERPLoginFlow.loginByDefaultUser();
    }

    @Test
    @DisplayName("ERP-创建商家")
    public void step02CreateMerchant() {
        // Given
        AddMerchantRequestDTO addMerchantRequestDTO = JSONUtils.jsonToObject(
                // 读取测试用例数据
                new ResourceUtils().readTestCaseDataFromResourcesPath(AddMerchantTests.class,
                        filePath + "Step02AddMerchant.json"),
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
        // When
        AddMerchantResponseDTO addMerchantResponseDTO = AddMerchantFlow.addMerchant(addMerchantRequestDTO);

        // Then
        assertThat(addMerchantResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        // 将响应结果存储起来提供给下一个测试用例使用
        this.addMerchantResponseDTO = addMerchantResponseDTO;
        log.info("创建商家成功，ShopId is:{}", addMerchantResponseDTO.getData().getShopId());
    }


    @DependsOnMethod("step02CreateMerchant")
    @Test
    @DisplayName("ERP-编辑商家-经营信息")
    public void step03EditMerchantInfoOfBusiness() {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(BusinessInfoEditTests.class,
                filePath + "Step03EditMerchantInfoOfBusiness.json");
        BusinessInfoEditRequestDTO businessInfoEditRequestDTO = JSONUtils.jsonToObject(requestJson, BusinessInfoEditRequestDTO.class);
        if (isDebug) {
            businessInfoEditRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            businessInfoEditRequestDTO.setShopId(this.addMerchantResponseDTO.getData().getShopId());
        }

        // When
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO = BusinessInfoEditFlow.businessInfoEdit(businessInfoEditRequestDTO);

        // Then
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);

    }

    @DependsOnMethod("step03EditMerchantInfoOfBusiness")
    @Test
    @DisplayName("ERP-编辑商家-费用配置")
    public void step04EditMerchantInfoOfCost() {
        // 使用默认值，暂不需要编辑费用配置
        // 配送平台服务费
        // 自取平台服务费
        // 塑料袋打包费
        // 配送小额订单费
        // 自取小额订单费
    }

    @DependsOnMethod("step04EditMerchantInfoOfCost")
    @Test
    @DisplayName("ERP-编辑商家-补充信息")
    public void step05EditMerchantInfoOfAdditional() {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(AdditionalInfoEditTests.class, this.filePath + "Step05EditMerchantInfoOfAdditional.json");
        AdditionalInfoEditRequestDTO additionalInfoEditRequestDTO = JSONUtils.jsonToObject(requestJson, AdditionalInfoEditRequestDTO.class);
        if (isDebug) {
            additionalInfoEditRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            additionalInfoEditRequestDTO.setShopId(this.addMerchantResponseDTO.getData().getShopId());
        }
        // When
        AdditionalInfoEditResponseDTO additionalInfoEditResponseDTO = AdditionalInfoEditFlow.additionalInfoEdit(additionalInfoEditRequestDTO);

        // Then
        assertThat(additionalInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }


}
