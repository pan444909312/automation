package com.miller.userapp.module.shop.card.version2.home.feature.firstOrderDelivery;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.SysAppConfigMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/8/28 17:56
 */
@Scenario(scenarioID = "01J6CDZ1N0GMN5RGTDRYNKE8SX",
        scenarioName = "商卡(中文)_普通店铺配送商卡_营销标_首单优先送_首页-商卡二期：首单优先送 - 不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoFirstOrderDeliveryFeature {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private UserLoginRequestDTO userLoginRequestDTO ;
    private SysAppConfigMapper sysAppConfigMapper;


    @BeforeAll
    void beforeAll() {
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        sysAppConfigMapper = sqlSession.getMapper(SysAppConfigMapper.class);
        SysAppConfigEntity sysAppConfigEntity = new SysAppConfigEntity();
        sysAppConfigEntity.setConfigValue("0");
        UpdateWrapper<SysAppConfigEntity> updateWrapper = new UpdateWrapper<SysAppConfigEntity>().eq("config_key", "FIRST_ORDER_DELIVERY_CONFIG");
        sysAppConfigMapper.update(sysAppConfigEntity,updateWrapper);
    }

    @AfterAll
    void afterAll(){
        SysAppConfigEntity sysAppConfigEntity = new SysAppConfigEntity();
        sysAppConfigEntity.setConfigValue("all");
        UpdateWrapper<SysAppConfigEntity> updateWrapper = new UpdateWrapper<SysAppConfigEntity>().eq("config_key", "FIRST_ORDER_DELIVERY_CONFIG");
        sysAppConfigMapper.update(sysAppConfigEntity,updateWrapper);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_营销标_首单优先送_首页-商卡二期：首单优先送 - 不展示")
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

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
