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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费税新规
 * 计算规则：（商品售价-商家承担优惠）* 商品消费税
 */
public class NewTaxRuleTaxServiceImpl implements TaxService {

    @Override
    public  TaxPriceDTO computeTaxPrice(CalculateOrderBasicData ov, OrderCountInfoEx countInfo){
        if (countInfo.getTotalPrice() <= 0) {
            return new TaxPriceDTO(0, 0);
        }
        List<ProductCart> productObjectList = ov.getProductCartList();
        CdKeyModel cdKeyModel = ov.getCdKeyModel();
        AtomicInteger taxPrice = new AtomicInteger();
        AtomicInteger merTaxPrice = new AtomicInteger();

       //商家承担优惠金额=商家承担的满减+商家承担的平台首单+商家承担的店铺首单+商家承担的红包+商家承商家承担的代金券+商家承担的自取折扣
        int merSave = ov.getFullSubMerchant()
                + ((!ov.isFirstOrder() || ov.getFirstDiscountMerchant() == null) ? 0 : ov.getFirstDiscountMerchant())
                + ((cdKeyModel == null || cdKeyModel.getMerchantBearPrice() == null) ? 0 : cdKeyModel.getMerchantBearPrice())
                + ((Objects.isNull(ov.getVoucherEntity()) || Objects.isNull(ov.getVoucherEntity().getMerContributionAmount())) ? 0 : ov.getVoucherEntity().getMerContributionAmount())
                + countInfo.getMerShopFirstDiscount() + ov.getUserTakeShopDiscountAmount();

        int tempTotal = countInfo.getTotalPrice();
        int tempMerTotal = countInfo.getMerTotalPrice();

        productObjectList.forEach(o -> {
            if (StringUtils.isNotBlank(o.getTaxRate()) && !"0".equals(o.getTaxRate())) {
                //商品售价占比=商品售价(包含加料费用)/商品售价汇总totalPrice
                double percent = (double) o.getPrice() / tempTotal;
                //（商品售价-（商家承担优惠金额 * 商品售价占比))*商品消费税率   即   （商品售价-商家承担优惠）* 商品消费税
                int amount = new BigDecimal(o.getPrice() - merSave * percent).multiply(new BigDecimal(o.getTaxRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).intValue();
                taxPrice.addAndGet(amount);

                int merAmount = 0;
                if (tempMerTotal > 0) {
                    double merPercent = (double) o.getMerPrice() / tempMerTotal;
                    merAmount = new BigDecimal(o.getMerPrice() - merSave * merPercent).multiply(new BigDecimal(o.getTaxRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).intValue();
                }
                merTaxPrice.addAndGet(merAmount);
                o.setTaxPrice(merAmount);
            }
        });

        return new TaxPriceDTO(taxPrice.intValue(), merTaxPrice.intValue());
    }
}
