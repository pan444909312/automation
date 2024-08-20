package com.miller.userapp.module.order.create;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.common.enums.common.PlatformEnum;
import com.hungrypanda.common.enums.shop.ShopTypeEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.miller.erp.moudle.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.module.order.create.flow.CreateOrderFlow;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.LanguageEnum;
import com.panda.common.enums.PayTypeEnum;
import com.panda.common.enums.VoucherStatusEnum;
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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    // 商品价格，使用结算页的计算金额,动态获取。这个用例用于验证订单主流程，所以金额计算的内部计算因子逻辑，暂不放在这里处理
    private static Integer orderPrice;

    @BeforeAll
    static void beforeAll() {
        // ERP 登录
        ERPLoginFlow.loginByDefaultUser();
        // 修改配送方式为商家配送
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO =
                BusinessInfoEditFlow.businessInfoEdit(getBusinessInfoEditRequestDTO(DeliveryTypeEnum.shop.getCode()));
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);

        // 查询订单金额
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);

        orderPrice = SettlementFlow.queryOrderPriceFormSettlementPage(
                OrderReqTypeEnum.COMMON_ORDER.getType(),
                DeliveryTypeEnum.shop.getCode(),
                TestCaseDataForMerchantConstant.shopId,
                List.of(productCart));
    }


    @AfterAll
    static void afterAll() {
        // 修改配送方式为平台配送
        BusinessInfoEditResponseDTO businessInfoEditResponseDTO =
                BusinessInfoEditFlow.businessInfoEdit(getBusinessInfoEditRequestDTO(DeliveryTypeEnum.third_party.getCode()));
        assertThat(businessInfoEditResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);

    }

    @MethodSource("createOrderByMerchantDelivery")
    @ParameterizedTest
    @DisplayName("创建订单-正常流程_商家配送")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
    }

    @NotNull
    private static BusinessInfoEditRequestDTO getBusinessInfoEditRequestDTO(Integer deliveryType) {
        BusinessInfoEditRequestDTO businessInfoEditRequestDTO = new BusinessInfoEditRequestDTO();
        businessInfoEditRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);
        businessInfoEditRequestDTO.setShopType(ShopTypeEnum.GENERAL.getCode());
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

        merchantModuleOperationInfoDTO.setLang(List.of(LanguageEnum.CN.getKey(), LanguageEnum.EN.getKey()));

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

    /**
     * 创建订单数据提供者_商家配送
     */
    static Stream<Arguments> createOrderByMerchantDelivery() {
        CreateOrderRequestDTO createOrderByMerchantDelivery = new CreateOrderRequestDTO();
        createOrderByMerchantDelivery.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByMerchantDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.shop.getCode()));
        createOrderByMerchantDelivery.setDeliveryTime("尽快送达");
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值
        createOrderByMerchantDelivery.setFixedPrice(orderPrice);
        createOrderByMerchantDelivery.setIsOnlinePay(Boolean.TRUE);

        createOrderByMerchantDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByMerchantDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByMerchantDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByMerchantDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByMerchantDelivery.setUseVoucherTemplate(VoucherStatusEnum.WAIT_USE.getCode());
        createOrderByMerchantDelivery.setRemark("【自动化测试】创建订单");
        // 选择自取/商家配送时需要传联系电话
        createOrderByMerchantDelivery.setUserPhone("86 18711110002");
        createOrderByMerchantDelivery.setTablewareCount(1);
        createOrderByMerchantDelivery.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByMerchantDelivery.setVerify(String.valueOf(StatusEnum.NO.getType()));
        return Stream.of(Arguments.of(createOrderByMerchantDelivery));
    }
}
