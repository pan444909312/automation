package com.miller.userapp.moduleEnAu.shop.card.version3.home.promotion.voucher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.common.utils.PriceUtil;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.entity.voucher.VoucherInfoEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.VoucherMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/7/31 11:42
 */
@Scenario(scenarioID = "01K0RBVKDDSN56ZRXPDD79BZ7V", scenarioName = "商卡(中文)_普通店铺配送商卡-SKYX01_优惠标签_代金券_首页-商卡二期：代金券31",
        author = "yancancan@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasVoucherScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private VoucherMapper voucherMapper;

    //消费券活动sn
    private String voucherSn = new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.voucherSn");

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
        voucherMapper = sqlSession.getMapper(VoucherMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_优惠标签_代金券_首页-商卡二期：代金券31")
    void shouldExistVoucher(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopPromoteVO shopPromoteVoucherVO = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_VOUCHER.getType()).findFirst().get();
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));
        String saleFixPrice = PriceUtil.getFixedPrice(shopSearchMiddleEntity.getFirstVoucherSalePrice(), shopSearchMiddleEntity.getCountry());
        String goodsFixPrice = PriceUtil.getFixedPrice(shopSearchMiddleEntity.getFirstVoucherGoodsPrice(), shopSearchMiddleEntity.getCountry());

        VoucherInfoEntity voucher = voucherMapper.selectOne(new QueryWrapper<VoucherInfoEntity>().eq("voucher_sn", voucherSn));

        assert voucher.getGoodsPrice().equals(shopSearchMiddleEntity.getFirstVoucherGoodsPrice());
        assert voucher.getSalePrice().equals(shopSearchMiddleEntity.getFirstVoucherSalePrice());
        assert shopPromoteVoucherVO.getType() == ShopPromoteEnum.INDEX_VOUCHER.getType();
        assert shopPromoteVoucherVO.getShowContent().equals(saleFixPrice + "抢" + goodsFixPrice + "代金券");
        assert shopPromoteVoucherVO.getTagType().equals(2);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
