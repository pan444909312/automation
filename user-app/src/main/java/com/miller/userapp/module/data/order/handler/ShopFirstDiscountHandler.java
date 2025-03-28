package com.miller.userapp.module.data.order.handler;

import com.hungrypanda.app.server.common.enums.LanguageEnum;
import com.hungrypanda.app.server.common.utils.PriceUtil;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.hungrypanda.app.server.entity.shop.ShopFirstDiscountConfigEntity;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.hungrypanda.app.server.entity.user.UserShopOrderNumEntity;
import com.hungrypanda.common.enums.discount.DiscountTypeEnum;
import com.hungrypanda.common.enums.user.NewUserStatusEnum;
import com.miller.userapp.module.data.order.db.ShopFirstDiscountConfigSql;
import com.miller.userapp.module.data.order.db.UserShopOrderNumSql;
import com.miller.userapp.module.data.order.entity.CalculateOrderBasicData;
import com.miller.userapp.module.data.order.entity.OrderCountInfoEx;
import com.miller.userapp.module.data.user.db.UserSql;
import org.apache.ibatis.session.SqlSession;

import java.util.Objects;

public class ShopFirstDiscountHandler extends AbstractDiscountHandler{
    private SqlSession sqlSession;
    private UserShopOrderNumSql userShopOrderNumSql;
    private ShopFirstDiscountConfigSql shopFirstDiscountConfigSql;
    private UserSql userSql;
    public ShopFirstDiscountHandler(SqlSession sqlSession){
        this.sqlSession = sqlSession;
        userShopOrderNumSql = new UserShopOrderNumSql(this.sqlSession);
        shopFirstDiscountConfigSql = new ShopFirstDiscountConfigSql(this.sqlSession);
        userSql = new UserSql(this.sqlSession);
    }
    public void getShopFirstDiscount(CalculateOrderBasicData ov, OrderCountInfoEx countInfo){
        String language = ov.getHeader().get("language").toString();
        String userId = ov.getHeader().get("userId").toString();
        UserEntity user = userSql.getUser(userId);
        if(!language.equalsIgnoreCase(LanguageEnum.CN.getKey()) || ov.isHasDiscountPromote()){
            //英文版，或商品使用了新人折扣价，则不能享用新人首单优惠
            return;
        }
        ShopDTO shop = ov.getShop();
        if(shop.checkIsMeal()) return;
        UserShopOrderNumEntity userShopOrderNumEntity = userShopOrderNumSql.getUserShopOrderNumEntity(userId,shop.getShopId());
        if(Objects.nonNull(userShopOrderNumEntity) && userShopOrderNumEntity.getOrderNum() > 0){
            return;
        }
        //店铺首单
        ShopFirstDiscountConfigEntity shopFirstDiscountConfigEntity = shopFirstDiscountConfigSql.getShopFirstDiscountConfigEntity(shop.getShopId());
        //如果是平台新人，则需要比较平台首单与新人首单哪个优惠更大
        if (NewUserStatusEnum.NEW.getCode().equals(user.getNewUserStatus())) {
            //平台首单
            Integer disCount = shop.getDiscounts();
            if (Objects.isNull(shopFirstDiscountConfigEntity) && (Objects.isNull(disCount) || disCount <= 0)) {
                //如果没有配置店铺首单与平台首单，则结束
                return;
            }
            if ((Objects.nonNull(shopFirstDiscountConfigEntity) && Objects.nonNull(shopFirstDiscountConfigEntity.getShopFirstDiscount()) && shopFirstDiscountConfigEntity.getShopFirstDiscount() < disCount)
                    || (Objects.isNull(shopFirstDiscountConfigEntity) && disCount > 0)) {
                //比较平台首单与店铺首单，如果店铺首单优惠比平台首单小，或没有配置店铺首单配置了平台首单，则使用平台首单
                ov.setFirstOrder(true);
                ov.setHasNewDiscount(true);
                ov.setFirstDiscounts(shop.getDiscounts());// 首单优惠金额
                ov.setFirstDiscountMerchant(shop.getFirstDiscountMerchant());
                ov.setFirstDiscountPlatform(shop.getFirstDiscountPlatform());
                countInfo.setFirstDiscount(ov.getFirstDiscounts());
//                buildOrderDiscountEntity(DiscountTypeEnum.FIRST_PLATFORM_DISCOUNT, shop.getDiscounts());
                return;
            }
        }

        if (Objects.isNull(shopFirstDiscountConfigEntity)) {
            //没有配置店铺首单，直接结束
            return;
        }
        //店铺新人或店铺首单优惠大于平台优惠，直接获取店铺首单优惠
        ov.setHasNewDiscount(true);
        ov.setShopFirstDiscount(shopFirstDiscountConfigEntity.getShopFirstDiscount());
        ov.setShopFirstDiscountStr(PriceUtil.getFixedPriceStr(shopFirstDiscountConfigEntity.getShopFirstDiscount(), shop.getCountry()));
        ov.setShopFirstDiscountConfigId(shopFirstDiscountConfigEntity.getId());
        countInfo.setMerShopFirstDiscount(shopFirstDiscountConfigEntity.getShopBearAmount());
        countInfo.setShopFirstDiscount(shopFirstDiscountConfigEntity.getShopFirstDiscount());
//        buildOrderDiscountEntity(DiscountTypeEnum.FIRST_SHOP_DISCOUNT, shopFirstDiscountConfigEntity.getShopFirstDiscount());

    }
}
