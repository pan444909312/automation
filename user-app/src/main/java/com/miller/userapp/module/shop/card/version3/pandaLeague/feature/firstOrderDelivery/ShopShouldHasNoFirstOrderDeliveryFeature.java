package com.miller.userapp.module.shop.card.version3.pandaLeague.feature.firstOrderDelivery;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.PandaTestDBHelpful;
import com.miller.userapp.util.RedisUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/8/28 17:56
 */
@Scenario(scenarioID = "01M2N3P4Q5R6S7T8U9V0W1X3C4",
        scenarioName = "普通店铺配送商卡-熊猫联盟频道-SKYX01_营销标_首单优先送_首页-商卡二期：首单优先送 - 不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 25, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoFirstOrderDeliveryFeature {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private UserLoginRequestDTO userLoginRequestDTO ;
    private RedisService redisInstance = RedisUtils.getRedisInstance();




    @BeforeAll
    void beforeAll() {
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));


        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
//        redisInstance.set("FIRST_ORDER_DELIVERY_CONFIG","0");
        // 熊猫联盟系统配置直接查的数据库，没查redis（首页店铺流查redis）
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("update hp_sys_app_config set config_value = 0 where config_key='FIRST_ORDER_DELIVERY_CONFIG'");
    }

    @AfterAll
    void afterAll(){
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("update hp_sys_app_config set config_value = 'all' where config_key='FIRST_ORDER_DELIVERY_CONFIG'");
//        redisInstance.set("FIRST_ORDER_DELIVERY_CONFIG","all");
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-熊猫联盟频道-SKYX01_营销标_首单优先送_首页-商卡二期：首单优先送 - 不展示")
    void shouldNotExistFirstOrderDeliveryFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();


        assertThat(shopIndexVO.getFirstOrderDelivery()).isNull();
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
