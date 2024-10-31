package com.miller.userapp.module.shop.card.version2.feature.deliveryDistance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.utils.NumberUtil;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author heyuan
 * @version 1.0
 * @since 2024/9/26 10:57
 */
@Scenario(scenarioID = "01J8MFQJYPKS8X8R4MENRKGDFX",
        scenarioName = "商卡(中文)_普通店铺配送商卡_辅助信息_配送距离_首页-商卡二期：配送距离 - 取实际距离*距离权重",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasShopRealDeliveryDistanceScenarioTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.02.shopId"));
    UserLoginRequestDTO userLoginRequestDTO;
    private ShopMapper shopMapper;


    @BeforeAll
    void beforeAll() {
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);


    }

    @MethodSource("DataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_辅助信息_配送距离_首页-商卡二期：配送距离 - 取实际距离*距离权重 ")
    void shouldShowPandLeagueFullSubCouponLabel(ShopListRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        String distance= shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get().getDistance();
        ShopEntity shopEntity = shopMapper.selectOne(new QueryWrapper<ShopEntity>().eq("shop_id", shopId));
        double longt2 = Double.parseDouble(shopEntity.getLongitude());
        double lat2 = Double.parseDouble(shopEntity.getLatitude());
        double longt1 = Double.parseDouble(BusinessConstant.longitude);
        double lat1 = Double.parseDouble(BusinessConstant.latitude);
        double x = (longt2 - longt1) * 3.141592653589793D * 6371229.0D * Math.cos((lat1 + lat2) / 2.0D * 3.141592653589793D / 180.0D) / 180.0D;
        double y = (lat2 - lat1) * 3.141592653589793D * 6371229.0D / 180.0D;
        double distance1 = Math.hypot(x, y)*1.4;
        distance1 /= 1000.0D;

        assertThat(distance).isEqualTo(NumberUtil.NumberFormat(distance1, "#0.00")+"km");



    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> DataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
  