package com.miller.bdm.app.privateShop.sign.provider;

import com.miller.bdm.app.privateShop.add.response.AddPrivateShopResponseDTO;
import com.miller.bdm.app.privateShop.sign.request.SignRequestDTO;
import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * bdm-发起签约
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@SuppressWarnings(value = "unused")
public class SignDataProvider {
    static Stream<Arguments> sign() {
        List<Integer> langList = new ArrayList<>();
        langList.add(1);
        SignRequestDTO signRequestDTO = new SignRequestDTO();
        signRequestDTO.setCommissionMode(1);
        signRequestDTO.setHpCommissionTax(BigDecimal.valueOf(33));
        signRequestDTO.setMerchantCommissionTax(BigDecimal.valueOf(33));
        signRequestDTO.setUserCommissionTax(BigDecimal.valueOf(33));
        signRequestDTO.setBdmEversignTemplatesId(BusinessConstantOfERP.eversignTemplatesId);
        signRequestDTO.setKpEmail(BusinessConstantOfERP.Email);
        signRequestDTO.setKpName(BusinessConstantOfERP.KPName);
        signRequestDTO.setKpStateCode(BusinessConstantOfERP.Code);
        signRequestDTO.setKpMobile(BusinessConstantOfERP.Iphone);
        signRequestDTO.setKpPosition(1);
        signRequestDTO.setLangList(langList);
        signRequestDTO.setCcStatus(0);
        signRequestDTO.setFlowType(0);
        Long bdmOpportunityShopId= Long.valueOf(CacheUtils.get(BusinessConstantOfERP.PRIVATE_SHOP_ID_KEY).toString());
        signRequestDTO.setBdmOpportunityShopId(bdmOpportunityShopId);


        return Stream.of(
                arguments(signRequestDTO)
        );
    }

}
