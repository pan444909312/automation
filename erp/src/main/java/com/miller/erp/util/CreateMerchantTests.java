package com.miller.erp.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.add.AddMerchantTests;
import com.miller.erp.moudle.manage.merchant.add.flow.AddMerchantFlow;
import com.miller.erp.moudle.manage.merchant.add.request.AddMerchantRequestDTO;
import com.miller.erp.moudle.manage.merchant.add.response.AddMerchantResponseDTO;
import com.miller.erp.moudle.manage.merchant.auditIdentity.flow.AuditIdentityMerchantFlow;
import com.miller.erp.moudle.manage.merchant.auditIdentity.request.AuditIdentityMerchantRequestDTO;
import com.miller.erp.moudle.manage.merchant.auth.flow.MerchantAuthFlow;
import com.miller.erp.moudle.manage.merchant.auth.request.MerchantAuthRequestDTO;
import com.miller.erp.moudle.manage.merchant.auth.response.MerchantAuthResponseDTO;
import com.miller.erp.moudle.manage.merchant.business.config.time.add.flow.AddShopBusinessTimeFlow;
import com.miller.erp.moudle.manage.merchant.business.config.time.add.request.AddShopBusinessTimeRequestDTO;
import com.miller.erp.moudle.manage.merchant.business.config.time.add.response.AddShopBusinessTimeResponseDTO;
import com.miller.erp.moudle.manage.merchant.edit.additional.AdditionalInfoEditTests;
import com.miller.erp.moudle.manage.merchant.edit.additional.flow.AdditionalInfoEditFlow;
import com.miller.erp.moudle.manage.merchant.edit.additional.request.AdditionalInfoEditRequestDTO;
import com.miller.erp.moudle.manage.merchant.edit.additional.response.AdditionalInfoEditResponseDTO;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.BusinessInfoEditTests;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.erp.moudle.manage.merchant.edit.kp.AddKPTests;
import com.miller.erp.moudle.manage.merchant.edit.kp.flow.AddKPFlow;
import com.miller.erp.moudle.manage.merchant.edit.kp.request.AddKPRequestDTO;
import com.miller.erp.moudle.manage.merchant.edit.kp.response.AddKPResponseDTO;
import com.miller.erp.moudle.manage.merchant.fence.flow.FenceFlow;
import com.miller.erp.moudle.manage.merchant.fence.request.FenceRequestDTO;
import com.miller.erp.moudle.manage.merchant.fence.response.FenceResponseDTO;
import com.miller.erp.moudle.manage.merchant.finance.bill.flow.SaveBillInfoConfigFlow;
import com.miller.erp.moudle.manage.merchant.finance.bill.request.SaveBillInfoConfigRequestDTO;
import com.miller.erp.moudle.manage.merchant.finance.bill.response.SaveBillInfoConfigResponseDTO;
import com.miller.erp.moudle.manage.merchant.finance.commission.flow.SaveCommissionFlow;
import com.miller.erp.moudle.manage.merchant.finance.commission.request.SaveCommissionRequestDTO;
import com.miller.erp.moudle.manage.merchant.finance.commission.response.SaveCommissionResponseDTO;
import com.miller.erp.moudle.manage.merchant.product.flow.CopyOtherShopProductFlow;
import com.miller.erp.moudle.manage.merchant.product.request.CopyOtherShopProductRequestDTO;
import com.miller.erp.moudle.manage.merchant.product.response.CopyOtherShopProductResponseDTO;
import com.miller.erp.moudle.manage.merchant.recommend.flow.RecommendMerchantFlow;
import com.miller.erp.moudle.manage.merchant.recommend.request.RecommendMerchantRequestDTO;
import com.miller.erp.mapper.shop.ShopMapper;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.framework.util.ResourceUtils;
import com.miller.service.util.XXLConfUtils;
import com.miller.service.util.XXLJobUtils;
import com.panda.merchant.server.api.dto.merchant.module.MerchantModuleOperationNameSortDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 一键自动创建商家。支持创建新商家 或 修改已存在的商家。当 {@code isEditMerchant = true} 则会根据 {@code shopIdForDebug} 的ID进行编辑商家操作。
 * <p>
 * 手工查看创建的商家路径：ERP-商家管理-商家列表-新增商家
 * <p>
 * 创建商家步骤：
 * <ul>
 *     <li>会自动根据指定路径{@code filePath}下的Json文件创建商家。</li>
 *     <li>关闭首页店铺流缓存,否则创建完商家不会自动显示在首页店铺流。</li>
 *     <li>如果 {@code isEditMerchant = false} 则自动创建商家。</li>
 *     <li>ERP-编辑商家-经营信息</li>
 *     <li>ERP-编辑商家-费用配置</li>
 *     <li>ERP-编辑商家-补充信息</li>
 *     <li>ERP-编辑商家-KP信息</li>
 *     <li>ERP-编辑商家-复制其他店铺商品</li>
 *     <li>ERP-编辑商家-修改店铺营业时间</li>
 *     <li>ERP-编辑商家-配送围栏</li>
 *     <li>ERP-编辑商家-结算信息</li>
 *     <li>ERP-编辑商家-结算信息-佣金</li>
 *     <li>ERP-编辑商家-审核</li>
 *     <li>ERP-编辑商家-推荐商家</li>
 *     <li>ERP-商家管理-商家认证-店主认证。<b>注意:</b>这一步暂未自动化，如果业务需要用到商家认证请手动上传照片认证，后续完善</li>
 *
 * </ul>
 *
 * </p>
 * <p>
 *     <ur>
 *         <li>使用场景1: 一键自动创建模板商家，用于测试首页、商卡、订单流等, 不包含红包、会员、优惠券等信息。</li>
 *         <li>使用场景2: 根据现有商家快速创建商家，使用方式为为先将自己的商家相关信息抓包保存到 {@code filePath} 文件夹下，然后使用此测试类进行创建商家.</li>
 *     </ur>
 *
 *
 * </p>
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
        // 关闭首页店铺流缓存
        XXLConfUtils.updateConfig(new PropertiesUtils().getProperty(CreateMerchantTests.class, "erp.xxl.env"), "user-app-server.shoplist.cache", "【首页店铺流】是否读redis缓存", false);
        if (!isEditMerchant) step02CreateMerchant();
    }

    @AfterAll
    public static void afterAll() {
        // 删除店铺？
    }

    /**
     * 创建商家
     */
    private static void step02CreateMerchant() {
        /* 名称添加时间戳的后6位，避免重复 */
        String merchantName = String.valueOf(System.currentTimeMillis()).substring(7, 13);

        // Given
        AddMerchantRequestDTO addMerchantRequestDTO = JSONUtils.jsonToObject(
                // 读取测试用例数据
                new ResourceUtils().readTestCaseDataFromResourcesPath(AddMerchantTests.class, filePath + "Step02AddMerchant.json"),
                // 转换为对象
                AddMerchantRequestDTO.class);
        // 商家中文名称不能重复
        MerchantModuleOperationNameSortDTO merchantModuleOperationNameSortDTO = addMerchantRequestDTO.getBaseInfo().getOperationNameList()
                // 获取 "lang": "CH" 对象
                .stream().filter(item -> item.getLang().equalsIgnoreCase("CN")).findFirst().orElseThrow().getSortList()
                // 设置中文名称
                .stream().filter(item -> item.getOperationName().name().equalsIgnoreCase("NAME")).findFirst().orElseThrow();
        // 设置中文名称
        merchantModuleOperationNameSortDTO.setValue(merchantModuleOperationNameSortDTO.getValue() + merchantName);

        // 英文名称不能重复
        MerchantModuleOperationNameSortDTO merchantModuleOperationNameSortDTO1 = addMerchantRequestDTO.getBaseInfo().getOperationNameList()
                // 获取 "lang": "EN" 对象
                .stream().filter(item -> item.getLang().equalsIgnoreCase("EN")).findFirst().orElseThrow().getSortList()
                // 设置英文名称
                .stream().filter(item -> item.getOperationName().name().equalsIgnoreCase("NAME")).findFirst().orElseThrow();
        // 设置英文名称
        merchantModuleOperationNameSortDTO1.setValue(merchantModuleOperationNameSortDTO1.getValue() + merchantName);

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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(BusinessInfoEditTests.class, filePath + "Step03EditMerchantInfoOfBusiness.json");
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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(AdditionalInfoEditTests.class, filePath + "Step05EditMerchantInfoOfAdditional.json");
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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(AddKPTests.class, filePath + "Step06EditMerchantInfoOfAddKP.json");
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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(CopyOtherShopProductFlow.class, filePath + "Step07CopyOtherShopGoods.json");
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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(CopyOtherShopProductFlow.class, filePath + "Step08AddShopBusinessTime.json");
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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(CopyOtherShopProductFlow.class, filePath + "Step09AddFence.json");
        FenceRequestDTO fenceRequestDTO = JSONUtils.jsonToObject(requestJson, FenceRequestDTO.class);

        List<FenceRequestDTO.BaseFenceOperateDTO> baseFenceOperateDTOS = JSON.parseArray(fenceRequestDTO.getAreaFenceData(), FenceRequestDTO.BaseFenceOperateDTO.class);
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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(SaveCommissionFlow.class, filePath + fileName);
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
        String requestJson = new ResourceUtils().readTestCaseDataFromResourcesPath(SaveBillInfoConfigFlow.class, filePath + fileName);
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
    public void step13RecommendMerchant() {
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

    @Disabled
    @DependsOnMethod("step13RecommendMerchant")
    @Test
    @DisplayName("ERP-商家管理-商家认证-店主认证")
    public void step14MerchantAuth() {
        // TODO 上传资料接口是老的表单接口，请求为 multipart/form-data ，请求体里面需要添加文件，所以暂时无法测试

        // Given
        AuditIdentityMerchantRequestDTO auditIdentityMerchantRequestDTO = new AuditIdentityMerchantRequestDTO();
        if (isEditMerchant) {
            auditIdentityMerchantRequestDTO.setShopId(shopIdForDebug);
        } else {
            // 修改 ShopId 为创建商家的 ShopId
            auditIdentityMerchantRequestDTO.setShopId(addMerchantResponseDTO.getData().getShopId());
        }
        // 根据 shopID 查询 UserId
        ShopMapper mapper = DBUtils.getDBOfPandaTest().getMapper(ShopMapper.class);
        ShopEntity shopEntity = mapper.selectOne(new LambdaQueryWrapper<ShopEntity>().eq(ShopEntity::getShopId, auditIdentityMerchantRequestDTO.getShopId()));
        auditIdentityMerchantRequestDTO.setUserId(shopEntity.getUserId());
        auditIdentityMerchantRequestDTO.setIdentityStatus(1);


        // When
        String responseBody = AuditIdentityMerchantFlow.auditIdentityMerchant(auditIdentityMerchantRequestDTO);

        // Then
        assertThat(responseBody).isNotEmpty();  // 返回的是html页面。。。
    }
}
