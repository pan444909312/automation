package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.hungrypanda.app.server.entity.order.OrderDiscountEntity;
import com.hungrypanda.app.server.entity.shop.FullSubEntity;
import com.hungrypanda.common.enums.discount.DiscountTypeEnum;
import com.miller.userapp.module.data.order.db.FullSubSql;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import com.miller.userapp.module.data.order.entity.SuperValueExchangeEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FullSubDiscountHandler extends AbstractDiscountHandler{
    private SqlSession sqlSession;
    private final FullSubSql fullSubSql;
    public FullSubDiscountHandler(SqlSession sqlSession){
        this.sqlSession = sqlSession;
        this.fullSubSql = new FullSubSql(this.sqlSession);
    }

    /**
     * 获取满减值
     * @param calculateOrderBasicData 基础数据
     * @param superValueExchange 超值换购商品
     * @return 满减值
     */
    public int getFullSubDiscountValue(CalculateOrderBasicData calculateOrderBasicData, SuperValueExchangeEntity superValueExchange, OrderCountInfoEx countInfo){
        ShopDTO shop = calculateOrderBasicData.getShop();
        if(Objects.isNull(shop) || shop.checkIsMeal()) return 0;
        if(calculateOrderBasicData.isHasDiscountPromote() || calculateOrderBasicData.isHasNewDiscountPromote())
            //单品折扣与满减互斥
            return  0;
        Integer totalPrice = calculateOrderBasicData.getTotalPrice();
        if (Objects.nonNull(superValueExchange) && superValueExchange.isHasSuperValueExchange()){
            //满减金额计算不带超值换购商品
            totalPrice = Math.max(0,totalPrice - superValueExchange.getSuperValueExchangePrice());
        }
        Map<String,Object> header = calculateOrderBasicData.getHeader();

        List<FullSubEntity> list = getFullSubByShopId(shop.getShopId(),Objects.nonNull(header)?header.get("language").toString():"");
        FullSubEntity fullSubEntity = getFullSubByTotalPrice(list,totalPrice);
        if(Objects.nonNull(fullSubEntity) && fullSubEntity.getFullSubId() != 0){
            Integer sub = fullSubEntity.getSub();
            countInfo.setSubDiscount(sub);
            calculateOrderBasicData.setFullSubMerchant(fullSubEntity.getFullSubMerchant());
            calculateOrderBasicData.setFullSubPlatform(fullSubEntity.getFullSubPlatform());
//            OrderDiscountEntity orderDiscountEntity = buildOrderDiscountEntity(DiscountTypeEnum.FULL_SUB_DISCOUNT, sub);
            return  sub;
        }
        return 0;
    }
    private FullSubEntity getFullSubByTotalPrice(List<FullSubEntity> list, Integer amount) {
        FullSubEntity result = null;
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        int tmp = 0;
        for (FullSubEntity fs : list) {
            if (amount >= fs.getFull() && fs.getSub() > tmp) {
                tmp = fs.getSub();
                result = fs;
            }
        }
        return result;
    }
    private List<FullSubEntity>  getFullSubByShopId(Long shopId,String languageCode){
        if(languageCode.isEmpty()){
            return getFullSubByShopIdByCN(shopId);
        }
        String languageCodeU = languageCode.toUpperCase();
        return fullSubSql.getFullSubEntity(shopId,languageCodeU);
    }
    private List<FullSubEntity>  getFullSubByShopIdByCN(Long shopId){
        return fullSubSql.getFullSubEntity(shopId,"CN");
    }
}
