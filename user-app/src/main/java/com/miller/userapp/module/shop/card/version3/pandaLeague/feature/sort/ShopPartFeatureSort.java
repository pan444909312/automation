package com.miller.userapp.module.shop.card.version3.pandaLeague.feature.sort;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungrypanda.app.server.common.enums.index.shopFeature.ShopFeatureEnum;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.dto.TypeMapping;
import com.miller.userapp.mapper.base.SysAppConfigMapper;
import com.miller.userapp.mapper.shop.DataShopUserOrderMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.RedisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
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
@Scenario(scenarioID = "01M2N3P4Q5R6S7T8U9V0W1X3D1",
        scenarioName = "普通店铺配送商卡-熊猫联盟频道-SKYX01_营销标_排序_当商家满足部份标签时，取当前可展示的标签作排序处理",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopPartFeatureSort {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.02.shopId"));
    private DataShopUserOrderMapper dataShopUserOrderMapper;

    RedisService redisInstance = RedisUtils.getRedisInstance();
    private BigInteger userId = BigInteger.valueOf(13999900002L);
    private SysAppConfigMapper sysAppConfigMapper ;


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
        sysAppConfigMapper = sqlSession.getMapper(SysAppConfigMapper.class);

        redisInstance.set("RECENT_ORDER_USER:" + shopId, userId);
    }

    @AfterAll
    void afterAll() {
        redisInstance.delete("RECENT_ORDER_USER:" + shopId);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-熊猫联盟频道-SKYX01_营销标_排序_当商家满足部份标签时，取当前可展示的标签作排序处理")
    void shouldExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) throws JsonProcessingException {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        List<ShopFeatureVO> shopFeatureList = shopIndexVO.getShopFeatureList();


        ObjectMapper mapper = new ObjectMapper();
        SysAppConfigEntity sysAppConfigEntity = sysAppConfigMapper.selectOne(new QueryWrapper<SysAppConfigEntity>().eq("config_key", "CARD_RECOMMEND_TAG_SORT"));
        String configValue = sysAppConfigEntity.getConfigValue();


        List<TypeMapping> mappings = mapper.readValue(configValue,
                mapper.getTypeFactory().constructCollectionType(List.class, TypeMapping.class));

        // 构建 type 到 sort 的映射
        Map<String, Integer> sortMap2 = mappings.stream()
                .collect(Collectors.toMap(TypeMapping::getType, TypeMapping::getSort));

        List<ShopFeatureVO> collect = shopFeatureList.stream().sorted(Comparator.comparing(
                item -> sortMap2.getOrDefault(ShopFeatureEnum.matchType(item.getType()).getName(), 0)
        )).toList();

        assertThat(shopFeatureList).isEqualTo(collect);
    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setTabType((byte) 1);
        shopListRequestDTO.setRedPacketList(new ArrayList<>());

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
