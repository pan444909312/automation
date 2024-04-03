package com.miller.bdm.app.private_shop.add.provider;

import com.miller.bdm.app.private_shop.add.request.AddPrivateShopRequestDTO;
import com.miller.bdm.app.shop_category.response.ShopCategoryResponseDTO;
import com.miller.bdm.app.shop_city.response.ShopCityResponseDTO;
import com.miller.bdm.app.shop_kp_role.response.ShopKPRoleResponseDTO;
import com.miller.bdm.app.shop_tag.response.ShopTagResponseDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-移动端私海池-私海池商家-新增
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class AddPrivateShopDataProvider {
    static Stream<Arguments> AddPrivateShopList() {
        Date date = new Date();
        AddPrivateShopRequestDTO addPrivateShopRequestDTO = new AddPrivateShopRequestDTO();
        Long businessCategoryId = CacheUtils.get(BusinessConstantOfERP.SHOP_CATEGORY_KEY, ShopCategoryResponseDTO.class).getData().get(0).getRespVOs().get(0).getBusinessCategoryId();
        Long cityId = CacheUtils.get(BusinessConstantOfERP.SHOP_CITY_KEY, ShopCityResponseDTO.class).getData().get(0).getCityId();
        String cityName = CacheUtils.get(BusinessConstantOfERP.SHOP_CITY_KEY, ShopCityResponseDTO.class).getData().get(0).getCityName();
        int KpRolecode = CacheUtils.get(BusinessConstantOfERP.SHOP_KP_ROLE_KEY, ShopKPRoleResponseDTO.class).getData().get(0).getCode();
        Long tagId = CacheUtils.get(BusinessConstantOfERP.SHOP_TAG_KEY, ShopTagResponseDTO.class).getData().get(0).getTagId();
        String tagName = CacheUtils.get(BusinessConstantOfERP.SHOP_TAG_KEY, ShopTagResponseDTO.class).getData().get(0).getTagName();
        Integer status = CacheUtils.get(BusinessConstantOfERP.SHOP_TAG_KEY, ShopTagResponseDTO.class).getData().get(0).getStatus();
        addPrivateShopRequestDTO.setAddress(cityName);
        addPrivateShopRequestDTO.setBusinessCategoryId(businessCategoryId);
        addPrivateShopRequestDTO.setShopName(BusinessConstantOfERP.PrivateShopName+date.getTime());
        addPrivateShopRequestDTO.setCityId(cityId);
        addPrivateShopRequestDTO.setCityName(cityName);
        addPrivateShopRequestDTO.setCountryCallingCode(BusinessConstantOfERP.Code);
        addPrivateShopRequestDTO.setCustomerServiceNumber(BusinessConstantOfERP.Iphone);
        addPrivateShopRequestDTO.setIgnoreSimilarShops(true);
        addPrivateShopRequestDTO.setKpCountryCallingCode(BusinessConstantOfERP.Code);
        addPrivateShopRequestDTO.setKpEmail(BusinessConstantOfERP.Email);
        addPrivateShopRequestDTO.setKpName(BusinessConstantOfERP.KPName);
        addPrivateShopRequestDTO.setKpPhoneNumber(BusinessConstantOfERP.Iphone);
        addPrivateShopRequestDTO.setKpPosition(KpRolecode);
        List list = new ArrayList();
        list.add(BusinessConstantOfERP.Lang);
        addPrivateShopRequestDTO.setLangList(list);
        addPrivateShopRequestDTO.setLatitude(BigDecimal.valueOf(BusinessConstantOfERP.Latitude));
        addPrivateShopRequestDTO.setLongitude(BigDecimal.valueOf(BusinessConstantOfERP.Longitude));

        return Stream.of(
                arguments(addPrivateShopRequestDTO)
        );
    }

}
