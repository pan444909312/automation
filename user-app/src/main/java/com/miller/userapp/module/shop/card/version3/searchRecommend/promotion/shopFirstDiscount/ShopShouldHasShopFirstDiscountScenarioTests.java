package com.miller.userapp.module.shop.card.version3.searchRecommend.promotion.shopFirstDiscount;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.entity.shop.ShopFirstDiscountConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.ShopFirstDiscountMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.searchRecommend.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.searchRecommend.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.searchRecommend.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;

import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

/**
 * @author panjuxiang
 * @since 2024/7/31 14:00
 */
@Scenario(scenarioID = "01KJ6V2PD176CZRX8RXKA727V1", scenarioName = "商卡(中文)_搜索推荐列表商卡-SKYX01_优惠标签_门店新客_搜索列表-商卡二期：门店新客24",
        author = "yancancan@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasShopFirstDiscountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private ShopFirstDiscountMapper shopFirstDiscountMapper;

    //门店新客活动id
    private final Long id = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shop.first.discount.id"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
        shopFirstDiscountMapper = sqlSession.getMapper(ShopFirstDiscountMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索推荐列表商卡-SKYX01_优惠标签_门店新客_搜索列表-商卡二期：门店新客24")
    void shouldExistShopFirstDiscount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopPromoteVO shopPromoteVO = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_SHOP_FIRST_DISCOUNT.getType()).findFirst().get();
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));
        ShopFirstDiscountConfigEntity shopFirstDiscountConfigEntity = shopFirstDiscountMapper.selectOne(new QueryWrapper<ShopFirstDiscountConfigEntity>().eq("shop_id", shopId).eq("status",1));

        assert shopFirstDiscountConfigEntity.getId().equals(id);
        assert shopFirstDiscountConfigEntity.getShopFirstDiscount().equals(shopSearchMiddleEntity.getShopFirstDiscount());
        assert shopPromoteVO.getType() == ShopPromoteEnum.INDEX_SHOP_FIRST_DISCOUNT.getType();
        //新商卡不返回contry了，改为直接拼¥
        assertThat(shopPromoteVO.getShowContent()).isEqualTo(String.format("新客减¥%s", (double) shopSearchMiddleEntity.getShopFirstDiscount() / 100));
        assert shopPromoteVO.getTagType() == 2;
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setKeywords("推荐");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        ArrayList<Long> shopIdList = new ArrayList<>();
        shopIdList.add(45367036L);
        shopIdList.add(930937488L);
        shopListRequestDTO.setShopIdList(shopIdList);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
