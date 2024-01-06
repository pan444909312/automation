package com.miller.erp.manage.merchant.edit.businessinfo.provider;

import com.miller.erp.manage.merchant.edit.businessinfo.request.BusinessInfoEditRequestDTO;
import com.panda.merchant.server.api.constant.MerchantEnum;
import com.panda.merchant.server.api.dto.info.PhoneInfo;
import com.panda.merchant.server.api.dto.merchant.module.ImageModuleDTO;
import com.panda.merchant.server.api.dto.merchant.module.MerchantModuleOperationInfoDTO;
import com.panda.merchant.server.api.dto.merchant.module.TaxTypeModuleDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_编辑商家经营信息
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/27 16:10:12
 */
@SuppressWarnings(value = "unused")
public class BusinessInfoEditDataProvider {
    static Stream<Arguments> businessInfoEdit() {
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
        // 配送方式 0.商家自己送 1.第三方配送
        merchantModuleOperationInfoDTO.setDeliveryType(1);
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


        return Stream.of(
                arguments(businessInfoEditRequestDTO)
        );
    }
}
