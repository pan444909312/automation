package com.miller.userapp.module.data.order.tax;


import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.dto.order.OrderCountInfoDTO;
import com.hungrypanda.app.server.dto.order.OrderVirtual;
import com.hungrypanda.app.server.dto.order.TaxPriceDTO;
import com.hungrypanda.app.server.dto.redpacket.CdKeyModel;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认的兜底
 * 计算规则：商品售价 * 商品消费税率
 */
public class DefaultRuleTaxServiceImpl implements TaxService {

    @Override
    public TaxPriceDTO computeTaxPrice(CalculateOrderBasicData ov, OrderCountInfoEx countInfo) {
        if (countInfo.getTotalPrice() <= 0) {
            return new TaxPriceDTO(0, 0);
        }
        List<ProductCart> productObjectList = ov.getProductCartList();

        AtomicInteger taxPrice = new AtomicInteger();
        AtomicInteger merTaxPrice = new AtomicInteger();

        productObjectList.forEach(o -> {
            if (StringUtils.isNotBlank(o.getTaxRate()) && !"0".equals(o.getTaxRate())) {
                int amount = new BigDecimal(o.getPrice()).multiply(new BigDecimal(o.getTaxRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).intValue();
                taxPrice.addAndGet(amount);

                int merAmount = new BigDecimal(o.getMerPrice()).multiply(new BigDecimal(o.getTaxRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).intValue();
                merTaxPrice.addAndGet(merAmount);

                o.setTaxPrice(merAmount);
            }
        });

        return new TaxPriceDTO(taxPrice.intValue(), merTaxPrice.intValue());
    }

}
