package com.miller.userapp.order.create;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.LoginFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.create.flow.CreateOrderFlow;
import com.miller.userapp.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.order.create.response.CreateOrderResponseDTO;
import com.panda.merchant.server.api.constant.MerchantEnum;
import com.panda.merchant.server.api.dto.info.PhoneInfo;
import com.panda.merchant.server.api.dto.merchant.module.ImageModuleDTO;
import com.panda.merchant.server.api.dto.merchant.module.MerchantModuleOperationInfoDTO;
import com.panda.merchant.server.api.dto.merchant.module.TaxTypeModuleDTO;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_创建订单-商家配送
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 15:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-商家配送")
public class CreateOrderByMerchantDeliveryTests {

    @BeforeAll
    static void beforeAll() {
        // ERP 登录
        LoginFlow.loginByDefaultUser();
        // 修改配送方式为商家配送
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO = BusinessInfoEditFlow.businessInfoEdit(getBusinessInfoEditRequestDTO(0));
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @AfterAll
    static void afterAll() {
        // 修改配送方式为平台配送
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO = BusinessInfoEditFlow.businessInfoEdit(getBusinessInfoEditRequestDTO(1));
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);

    }

    @MethodSource("com.miller.userapp.order.create.provider.CreateOrderDataProvider#createOrderByMerchantDelivery")
    @ParameterizedTest
    @DisplayName("创建订单-正常流程_商家配送")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
        // TODO 订单数据校验
    }

    @NotNull
    private static BusinessInfoEditRequestDTO getBusinessInfoEditRequestDTO(Integer deliveryType) {
        BusinessInfoEditRequestDTO businessInfoEditRequestDTO = new BusinessInfoEditRequestDTO();
        businessInfoEditRequestDTO.setShopId(59750820L);
        businessInfoEditRequestDTO.setShopType(0);
        MerchantModuleOperationInfoDTO merchantModuleOperationInfoDTO = new MerchantModuleOperationInfoDTO();

        // images 字段
        ImageModuleDTO shopLogo = new ImageModuleDTO();
        shopLogo.setFileSource(MerchantEnum.MerchantFileSourceEnum.SHOP_LOGO);
        shopLogo.setUrl("https://static.hungrypanda.co/crm/1699603913366e76c5b765a6e491ab976d83b65c75a42.jpg");
        shopLogo.setSort(0);

        ImageModuleDTO backgroundImageOfCN = new ImageModuleDTO();
        backgroundImageOfCN.setFileSource(MerchantEnum.MerchantFileSourceEnum.BG_IMAGE_CN);
        backgroundImageOfCN.setUrl("https://static.hungrypanda.co/crm/1700206343524ef7d22d2c0b54c67bc6018d1d2358761.jpg");
        backgroundImageOfCN.setSort(0);

        ImageModuleDTO shopLogoGIF = new ImageModuleDTO();
        shopLogoGIF.setFileSource(MerchantEnum.MerchantFileSourceEnum.SHOP_LOGO_GIF);
        shopLogoGIF.setUrl("https://static.hungrypanda.co/crm/1700206191472ec9c8a416dcc4a8eb38a8a3fd8aab8e1.gif");
        shopLogoGIF.setSort(0);

        merchantModuleOperationInfoDTO.setImages(List.of(shopLogo, backgroundImageOfCN, shopLogoGIF));

        // shopSiteList 字段
        merchantModuleOperationInfoDTO.setShopSiteList(List.of(MerchantEnum.ShopSiteEnum.HP));

        merchantModuleOperationInfoDTO.setLang(List.of("EN", "CN"));

        TaxTypeModuleDTO taxTypeModuleDTO = new TaxTypeModuleDTO();
        taxTypeModuleDTO.setTaxRate(BigDecimal.ZERO);
        taxTypeModuleDTO.setTaxSetType(0);
        merchantModuleOperationInfoDTO.setTaxType(taxTypeModuleDTO);

        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.setCountryCallingCode("86");
        phoneInfo.setPhoneNumber("18722220001");
        merchantModuleOperationInfoDTO.setServicePhoneList(List.of(phoneInfo));
        merchantModuleOperationInfoDTO.setStartSendMoney(BigDecimal.TEN);
        merchantModuleOperationInfoDTO.setDeliveryRange(BigDecimal.valueOf(666));
        // 配送方式: 0=商家配送；1=平台配送；2=自取
        merchantModuleOperationInfoDTO.setDeliveryType(deliveryType);
        merchantModuleOperationInfoDTO.setIsOnlinePay(List.of(1));
        merchantModuleOperationInfoDTO.setIsSpecialDriveFaraway(1);
        merchantModuleOperationInfoDTO.setIsMandatoryTip(0);
        merchantModuleOperationInfoDTO.setIsTipShowZero(0);
        merchantModuleOperationInfoDTO.setHasPlatformPrice(0);
        merchantModuleOperationInfoDTO.setIsUserPack(1);
        merchantModuleOperationInfoDTO.setUserPackCanOfflinePay(1);
        merchantModuleOperationInfoDTO.setMinSelfThreshold(BigDecimal.ZERO);

        // 最终 operationInfo 数据
        businessInfoEditRequestDTO.setOperationInfo(merchantModuleOperationInfoDTO);
        return businessInfoEditRequestDTO;
    }
}
