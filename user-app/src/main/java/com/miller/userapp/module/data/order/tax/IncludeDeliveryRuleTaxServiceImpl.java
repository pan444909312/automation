package com.miller.userapp.module.data.order.tax;


import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.utils.CollectionUtil;
import com.hungrypanda.app.server.dto.order.OrderCountInfoDTO;
import com.hungrypanda.app.server.dto.order.OrderVirtual;
import com.hungrypanda.app.server.dto.order.TaxPriceDTO;
import com.hungrypanda.app.server.dto.redpacket.CdKeyModel;
import com.hungrypanda.app.server.vo.order.OrderDetailProductVO;
import com.hungrypanda.app.server.vo.order.req.TaxFeeOrderGFODTO;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 开启配送费计入消费税
 * 计算规则：（商品售价+商品按比例分摊到的配送费）* 商品消费税
 */

public class IncludeDeliveryRuleTaxServiceImpl implements TaxService {

    @Override
    public  TaxPriceDTO computeTaxPrice(CalculateOrderBasicData ov, OrderCountInfoEx countInfo) {
        if (countInfo.getTotalPrice() <= 0) {
            return new TaxPriceDTO(0, 0);
        }
        List<ProductCart> productObjectList = ov.getProductCartList();
        AtomicInteger taxPrice = new AtomicInteger();
        AtomicInteger merTaxPrice = new AtomicInteger();

        // 开启了计入配送费
        int tempTotal = countInfo.getTotalPrice();
        int tempMerTotal = countInfo.getMerTotalPrice();
        int tempDeliveryFee = countInfo.getDeliveryPriceAddDeliveryChargeAmount();

        productObjectList.forEach(o -> {
            if (StringUtils.isNotBlank(o.getTaxRate()) && !"0".equals(o.getTaxRate())) {
                //商品售价占比=商品售价(包含加料费用)/商品售价汇总totalPrice
                double percent = (double) o.getPrice() / tempTotal;
                //（商品售价+（配送费 * 商品售价占比))*商品消费税率  即  （商品售价+商品按比例分摊到的配送费）* 商品消费税
                int amount = new BigDecimal(o.getPrice() + tempDeliveryFee * percent).multiply(new BigDecimal(o.getTaxRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).intValue();
                taxPrice.addAndGet(amount);

                int merAmount = 0;
                if (tempMerTotal > 0) {
                    double merPercent = (double) o.getMerPrice() / tempMerTotal;
                    merAmount = new BigDecimal(o.getMerPrice() + tempDeliveryFee * merPercent).multiply(new BigDecimal(o.getTaxRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).intValue();
                }
                merTaxPrice.addAndGet(merAmount);
                o.setTaxPrice(merAmount);
            }
        });

        return new TaxPriceDTO(taxPrice.intValue(), merTaxPrice.intValue());
    }

}
