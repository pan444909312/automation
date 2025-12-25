package com.miller.userapp.moduleEnAu.shop.card.version3.home.feature.sort;

import com.hungrypanda.app.server.common.enums.index.shopFeature.ShopFeatureEnum;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.DataShopUserOrderMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.util.RedisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CARD_RECOMMEND_TAG_SORT
 *
 * [
 *     {
 *         "type": "榜单",
 *         "sort": 1
 *     },
 *     {
 *         "type": "买过的店",
 *         "sort": 2
 *     },
 *     {
 *         "type": "高月售人气门店",
 *         "sort": 3
 *     },
 *     {
 *         "type": "最近24小时下单人数",
 *         "sort": 4
 *     },
 *     {
 *         "type": "回头客数量",
 *         "sort": 5
 *     },
 *     {
 *         "type": "推荐标签",
 *         "sort": 6
 *     },
 *     {
 *         "type": "人工营销标签",
 *         "sort": 7
 *     },
 *     {
 *         "type": "近期n人好评",
 *         "sort": 8
 *     },
 *     {
 *         "type": "千人收藏的好店",
 *         "sort": 9
 *     },
 *     {
 *         "type": "用户下单提醒",
 *         "sort": 10
 *     },
 *     {
 *         "type": "历史购买提醒",
 *         "sort": 11
 *     },
 *     {
 *         "type": "堂食同价",
 *         "sort": 12
 *     },
 *     {
 *         "type": "首单优先送",
 *         "sort": 13
 *     }
 * ]
 * @author panjuxiang
 * @since 2024/8/24 16:34
 */
@Scenario(scenarioID = "01K0V7PH8ZT17GZDKXCQGMNKCG",
        scenarioName = "普通店铺配送商卡-SKYX01_营销标_排序_当商家满足当前所有标签时，检查顺序",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopFeatureDefaultSort {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.03.shopId"));
    private DataShopUserOrderMapper dataShopUserOrderMapper;

    RedisService redisInstance = RedisUtils.getRedisInstance();
    private BigInteger userId = BigInteger.valueOf(13999900002L);


    @BeforeAll
    void beforeAll() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5((new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.password"))));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        dataShopUserOrderMapper = sqlSession.getMapper(DataShopUserOrderMapper.class);
        redisInstance.set("RECENT_ORDER_USER:" + shopId, userId);
    }

    @AfterAll
    void afterAll() {
        redisInstance.delete("RECENT_ORDER_USER:" + shopId);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_营销标_排序_当商家满足当前所有标签时，检查顺序")
    void shouldExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        List<ShopFeatureVO> shopFeatureList = shopIndexVO.getShopFeatureList();

        assertThat(shopFeatureList.get(0).getType()).isEqualTo(ShopFeatureEnum.SHOP_RANK.getType());
        assertThat(shopFeatureList.get(1).getType()).isEqualTo(ShopFeatureEnum.BOUGHT_RECENTLY.getType());
        assertThat(shopFeatureList.get(2).getType()).isEqualTo(ShopFeatureEnum.POPULAR_STORE.getType());
        assertThat(shopFeatureList.get(3).getType()).isEqualTo(ShopFeatureEnum.LAST_DAY_ORDER.getType());
        assertThat(shopFeatureList.get(4).getType()).isEqualTo(ShopFeatureEnum.REPEAT_CUSTOMER.getType());
        assertThat(shopFeatureList.get(5).getType()).isEqualTo(ShopFeatureEnum.SHOP_EVALUATION.getType());
        assertThat(shopFeatureList.get(6).getType()).isEqualTo(ShopFeatureEnum.EVALUATE_NUM.getType());
        assertThat(shopFeatureList.get(7).getType()).isEqualTo(ShopFeatureEnum.COLLECT_NUM.getType());
        assertThat(shopFeatureList.get(8).getType()).isEqualTo(ShopFeatureEnum.USER_ORDER_REMIND.getType());
        assertThat(shopFeatureList.get(9).getType()).isEqualTo(ShopFeatureEnum.DINE_IN_SAME.getType());
        assertThat(shopFeatureList.get(10).getType()).isEqualTo(ShopFeatureEnum.FIRST_ORDER_DELIVERY.getType());

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
