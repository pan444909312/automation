package com.miller.erp.util;

import com.alibaba.fastjson.JSON;
import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.dto.BasicResponseDTO;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.merchant.add.AddMerchantTests;
import com.miller.erp.manage.merchant.add.flow.AddMerchantFlow;
import com.miller.erp.manage.merchant.add.request.AddMerchantRequestDTO;
import com.miller.erp.manage.merchant.add.response.AddMerchantResponseDTO;
import com.miller.erp.manage.merchant.auth.flow.MerchantAuthFlow;
import com.miller.erp.manage.merchant.auth.request.MerchantAuthRequestDTO;
import com.miller.erp.manage.merchant.auth.response.MerchantAuthResponseDTO;
import com.miller.erp.manage.merchant.business.config.time.add.flow.AddShopBusinessTimeFlow;
import com.miller.erp.manage.merchant.business.config.time.add.request.AddShopBusinessTimeRequestDTO;
import com.miller.erp.manage.merchant.business.config.time.add.response.AddShopBusinessTimeResponseDTO;
import com.miller.erp.manage.merchant.edit.additional.AdditionalInfoEditTests;
import com.miller.erp.manage.merchant.edit.additional.flow.AdditionalInfoEditFlow;
import com.miller.erp.manage.merchant.edit.additional.request.AdditionalInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.additional.response.AdditionalInfoEditResponseDTO;
import com.miller.erp.manage.merchant.edit.businessinfo.BusinessInfoEditTests;
import com.miller.erp.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.erp.manage.merchant.edit.kp.AddKPTests;
import com.miller.erp.manage.merchant.edit.kp.flow.AddKPFlow;
import com.miller.erp.manage.merchant.edit.kp.request.AddKPRequestDTO;
import com.miller.erp.manage.merchant.edit.kp.response.AddKPResponseDTO;
import com.miller.erp.manage.merchant.fence.flow.FenceFlow;
import com.miller.erp.manage.merchant.fence.request.FenceRequestDTO;
import com.miller.erp.manage.merchant.fence.response.FenceResponseDTO;
import com.miller.erp.manage.merchant.finance.bill.flow.SaveBillInfoConfigFlow;
import com.miller.erp.manage.merchant.finance.bill.request.SaveBillInfoConfigRequestDTO;
import com.miller.erp.manage.merchant.finance.bill.response.SaveBillInfoConfigResponseDTO;
import com.miller.erp.manage.merchant.finance.commission.flow.SaveCommissionFlow;
import com.miller.erp.manage.merchant.finance.commission.request.SaveCommissionRequestDTO;
import com.miller.erp.manage.merchant.finance.commission.response.SaveCommissionResponseDTO;
import com.miller.erp.manage.merchant.product.flow.CopyOtherShopProductFlow;
import com.miller.erp.manage.merchant.product.request.CopyOtherShopProductRequestDTO;
import com.miller.erp.manage.merchant.product.response.CopyOtherShopProductResponseDTO;
import com.miller.erp.manage.merchant.recommend.flow.RecommendMerchantFlow;
import com.miller.erp.manage.merchant.recommend.request.RecommendMerchantRequestDTO;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.framework.util.ResourceUtils;
import com.miller.service.util.XXLJobUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
@Scenario(scenarioID = "01J4QYGE34BJ7SP84EBBTWEJPT", scenarioName = "一键自动创建模板商家", developmentTime = 6 * 60, maintenanceTime = 0, manualTestTime = 4 * 60)
@Slf4j
@DisplayName("一键自动创建商家")
public class CreateMerchantTests {
    /**
     * true: 编辑商家；false:创建商家。如果为false则使用指定的 ShopId 对商家进行编辑操作。
     */
    private static final boolean isEditMerchant = false;
    private final long shopIdForDebug = 970301088;

    /**
     * 测试用例数据文件路径
     */
    private static final String filePath = "quickCreateMerchant/";
    /**
     * 新增商家返回的数据
     */
    private static AddMerchantResponseDTO addMerchantResponseDTO;

    @BeforeAll
    public static void beforeAll() {
        ERPLoginFlow.loginByDefaultUser();
        if (!isEditMerchant) step02CreateMerchant();
    }

    @AfterAll
    public static void afterAll() {
        System.out.println(addMerchantResponseDTO.getData().getShopId());
        // 删除店铺？
    }

    private static void step02CreateMerchant() {
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
        CreateMerchantTests.addMerchantResponseDTO = addMerchantResponseDTO;
        log.info("创建商家成功，ShopId is:{}", addMerchantResponseDTO.getData().getShopId());
    }

    @Test
    @DisplayName("ERP-编辑商家-经营信息")
    public void step03EditMerchantInfoOfBusiness() {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(BusinessInfoEditTests.class,
                filePath + "Step03EditMerchantInfoOfBusiness.json");
        BusinessInfoEditRequestDTO businessInfoEditRequestDTO = JSONUtils.jsonToObject(requestJson, BusinessInfoEditRequestDTO.class);
        if (isEditMerchant) {
            // 修改 ShopId 为指定的 ShopId
            businessInfoEditRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            businessInfoEditRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }

        // When
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO = BusinessInfoEditFlow.businessInfoEdit(businessInfoEditRequestDTO);

        // Then
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);

    }

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

    @Test
    @DisplayName("ERP-编辑商家-补充信息")
    public void step05EditMerchantInfoOfAdditional() {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(AdditionalInfoEditTests.class,
                filePath + "Step05EditMerchantInfoOfAdditional.json");
        AdditionalInfoEditRequestDTO additionalInfoEditRequestDTO = JSONUtils.jsonToObject(requestJson, AdditionalInfoEditRequestDTO.class);
        if (isEditMerchant) {
            additionalInfoEditRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            additionalInfoEditRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }
        // When
        AdditionalInfoEditResponseDTO additionalInfoEditResponseDTO = AdditionalInfoEditFlow.additionalInfoEdit(additionalInfoEditRequestDTO);

        // Then
        assertThat(additionalInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @Test
    @DisplayName("ERP-编辑商家-KP信息")
    public void step06EditMerchantInfoOfAddKP() {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(AddKPTests.class,
                filePath + "Step06EditMerchantInfoOfAddKP.json");
        AddKPRequestDTO AddKPRequestDTO = JSONUtils.jsonToObject(requestJson, AddKPRequestDTO.class);
        if (isEditMerchant) {
            AddKPRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            AddKPRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }
        // When
        AddKPResponseDTO AddKPResponseDTO = AddKPFlow.addKP(AddKPRequestDTO);

        // Then
        assertThat(AddKPResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @Test
    @DisplayName("ERP-编辑商家-复制其他店铺商品")
    public void step07CopyOtherShopGoods() {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(CopyOtherShopProductFlow.class,
                filePath + "Step07CopyOtherShopGoods.json");
        CopyOtherShopProductRequestDTO copyOtherShopProductRequestDTO = JSONUtils.jsonToObject(requestJson, CopyOtherShopProductRequestDTO.class);
        if (isEditMerchant) {
            copyOtherShopProductRequestDTO.setOrgShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            copyOtherShopProductRequestDTO.setOrgShopId(addMerchantResponseDTO.getData().getShopId());
        }

        // When
        CopyOtherShopProductResponseDTO copyOtherShopProductResponseDTO = CopyOtherShopProductFlow.copyOtherShopProduct(copyOtherShopProductRequestDTO);

        // Then
        assertThat(copyOtherShopProductResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @Test
    @DisplayName("ERP-编辑商家-修改店铺营业时间")
    public void step08AddShopBusinessTime() {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(CopyOtherShopProductFlow.class,
                filePath + "Step08AddShopBusinessTime.json");
        AddShopBusinessTimeRequestDTO addShopBusinessTimeRequestDTO = JSONUtils.jsonToObject(requestJson, AddShopBusinessTimeRequestDTO.class);
        if (isEditMerchant) {
            addShopBusinessTimeRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            addShopBusinessTimeRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }

        // When
        AddShopBusinessTimeResponseDTO addShopBusinessTimeResponseDTO = AddShopBusinessTimeFlow.addShopBusinessTime(addShopBusinessTimeRequestDTO);

        // Then
        assertThat(addShopBusinessTimeResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @Test
    @DisplayName("ERP-编辑商家-配送围栏")
    public void step09AddFence() throws UnsupportedEncodingException {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(CopyOtherShopProductFlow.class,
                filePath + "Step09AddFence.json");
        FenceRequestDTO fenceRequestDTO = JSONUtils.jsonToObject(requestJson, FenceRequestDTO.class);

        List<FenceRequestDTO.BaseFenceOperateDTO> baseFenceOperateDTOS =
                JSON.parseArray(fenceRequestDTO.getAreaFenceData(), FenceRequestDTO.BaseFenceOperateDTO.class);
        if (isEditMerchant) {
            baseFenceOperateDTOS.forEach(baseFenceOperateDTO -> {
                baseFenceOperateDTO.setPid(shopIdForDebug);
            });


        } else {
            // 修改 ShopId 为创建商家的 ShopId
            baseFenceOperateDTOS.forEach(baseFenceOperateDTO -> {
                baseFenceOperateDTO.setPid(addMerchantResponseDTO.getData().getShopId());
            });
        }
        fenceRequestDTO.setAreaFenceData(JSON.toJSONString(baseFenceOperateDTOS));

//        URLEncoder.encode(String.valueOf(fenceRequestDTO.getAreaFenceData().get(0)), "UTF-8");
        // When
        FenceResponseDTO fenceResponseDTO = FenceFlow.saveFence(fenceRequestDTO);

        // Then
        assertThat(fenceResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.code);
    }

    @Test
    @DisplayName("ERP-编辑商家-结算信息")
    public void step10SaveBillInfo() {
        saveBillInfo("Step10SaveBillInfoOfPanda.json");
        saveBillInfo("Step10SaveBillInfoOfPandaWeb.json");
        saveBillInfo("Step10SaveBillInfoOfPandaGFO.json");
    }

    @DependsOnMethod("step10SaveBillInfo")
    @Test
    @DisplayName("ERP-编辑商家-结算信息-佣金")
    public void step11SaveCommission() {
        saveCommission("Step11SaveCommissionOfPanda-1.json");
        saveCommission("Step11SaveCommissionOfPanda-2.json");
        saveCommission("Step11SaveCommissionOfPanda-3.json");
        saveCommission("Step11SaveCommissionOfPanda-4.json");
        saveCommission("Step11SaveCommissionOfPanda-5.json");
        saveCommission("Step11SaveCommissionOfPanda-6.json");
        saveCommission("Step11SaveCommissionOfPandaWeb-1.json");
        saveCommission("Step11SaveCommissionOfGFO-1.json");
    }

    /**
     * 结算信息子项-佣金
     *
     * @param fileName 文件名
     */
    private void saveCommission(String fileName) {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(SaveCommissionFlow.class,
                filePath + fileName);
        SaveCommissionRequestDTO saveCommissionRequestDTO = JSONUtils.jsonToObject(requestJson, SaveCommissionRequestDTO.class);
        if (isEditMerchant) {
            saveCommissionRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            saveCommissionRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }
        // When
        SaveCommissionResponseDTO saveCommissionResponseDTO = SaveCommissionFlow.saveCommission(saveCommissionRequestDTO);
        // Then
        assertThat(saveCommissionResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }


    /**
     * 保存结算信息
     *
     * @param fileName 文件名
     */
    private void saveBillInfo(String fileName) {
        // Given
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(SaveBillInfoConfigFlow.class,
                filePath + fileName);
        SaveBillInfoConfigRequestDTO saveBillInfoConfigRequestDTO = JSONUtils.jsonToObject(requestJson, SaveBillInfoConfigRequestDTO.class);
        if (isEditMerchant) {
            saveBillInfoConfigRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            saveBillInfoConfigRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }
        // When
        SaveBillInfoConfigResponseDTO saveBillInfoConfigResponseDTO = SaveBillInfoConfigFlow.saveBillInfoConfig(saveBillInfoConfigRequestDTO);
        // Then
        assertThat(saveBillInfoConfigResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @DependsOnMethod("step11SaveCommission")
    @Test
    @DisplayName("ERP-编辑商家-审核")
    public void step12MerchantAuth() {
        // Given
        MerchantAuthRequestDTO merchantAuthRequestDTO = new MerchantAuthRequestDTO();
        merchantAuthRequestDTO.setStatus(1);
        if (isEditMerchant) {
            merchantAuthRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            merchantAuthRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }
        // When
        MerchantAuthResponseDTO merchantAuthResponseDTO = MerchantAuthFlow.merchantAuth(merchantAuthRequestDTO);
        // Then
        assertThat(merchantAuthResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @DependsOnMethod("step12MerchantAuth")
    @Test
    @DisplayName("ERP-编辑商家-推荐商家")
    public void step13Recommend() {
        // Given
        RecommendMerchantRequestDTO recommendMerchantRequestDTO = new RecommendMerchantRequestDTO();
        recommendMerchantRequestDTO.setType(1);
        if (isEditMerchant) {
            recommendMerchantRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            recommendMerchantRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }
        // When
        String response = RecommendMerchantFlow.recommendMerchant(recommendMerchantRequestDTO);
        // Then
        assertThat(response).containsIgnoringCase("code\\\":1000");
        // 搜索索引更新
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "erp.job.increment.index.update.id"));
    }

}
