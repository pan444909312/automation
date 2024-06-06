package com.miller.bdm.app.privateShop.formula;

import com.miller.bdm.app.privateShop.formula.flow.GetFormulaListFlow;
import com.miller.bdm.app.privateShop.formula.request.GetFormulaListRequestDTO;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.common.base.Result;
import com.panda.erp.server.api.vo.voucher.VoucherFormulaSimpleInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * bdm-移动端私海池-普通订单佣金结算公式下拉列表
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-移动端私海池-普通订单佣金结算公式下拉列表")
public class GetTemplatesTests {
    @MethodSource("com.miller.bdm.app.privateShop.formula.provider.GetFormulaListDataProvider#GetTemplatesList")
    @ParameterizedTest
    @DisplayName("bdm-移动端私海池-普通订单佣金结算公式下拉列表")
    void QueryPrivateShopList(GetFormulaListRequestDTO GetFormulaListRequestDTO) {
        Result<List<VoucherFormulaSimpleInfo>> getFormulaListRequestDTO = GetFormulaListFlow.getPageList(GetFormulaListRequestDTO);
        assertThat(getFormulaListRequestDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
//        assertThat(getFormulaListRequestDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        assertThat(getFormulaListRequestDTO.getData()).isNotNull();
    }

}
