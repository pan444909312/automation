package com.miller.userapp.module.shop.card.version3.search.sideInfo.deliveryDistance;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.utils.NumberUtil;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.base.SysAppConfigMapper;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;
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
@Scenario(scenarioID = "01K9VH77WP0C58RKEXC7J548FE",
        scenarioName = "商卡(中文)_搜索列表商卡-SKYX01_辅助信息_配送距离_搜索列表-商卡二期：配送距离 - 取实际距离*距离权重",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasShopRealDeliveryDistanceScenarioTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.shopId"));
    UserLoginRequestDTO userLoginRequestDTO;
    private ShopMapper shopMapper;
    private SysAppConfigMapper sysAppConfigMapper;
    private final String configKey = "UAS:DISTANCE_CONFIG";
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
        sysAppConfigMapper = sqlSession.getMapper(SysAppConfigMapper.class);


    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 地球平均半径（单位：米）
        final double R = 6371000;

        // 将经纬度转换为弧度
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine 公式
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;

        // 考虑实际道路情况的修正系数
        return distance * 1.4;
    }

    @MethodSource("DataProvider")
    @ParameterizedTest
    @DisplayName("搜索列表商卡-SKYX01_辅助信息_配送距离_搜索列表-商卡二期：配送距离 - 取实际距离*距离权重 ")
    void shouldShowPandLeagueFullSubCouponLabel(ShopListRequestDTO shopListRequestDTO) {

        RequestUtils.getHeaders().put("longitude", "115.95410");
        RequestUtils.getHeaders().put("latitude", "29.66058");
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        String distance= shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get().getDistance();
        //根据店铺id，查询店铺经纬度
        ShopEntity shopEntity = shopMapper.selectOne(new QueryWrapper<ShopEntity>().eq("shop_id", shopId));
        double longt2 = Double.parseDouble(shopEntity.getLongitude());
        double lat2 = Double.parseDouble(shopEntity.getLatitude());
        double longt1 = Double.parseDouble("115.95410");
        double lat1 = Double.parseDouble("29.66058");
        double distance1 = calculateDistance(lat1, longt1, lat2, longt2);
        //查询配置里，大于minMeter再加上addMeter,单位m
        SysAppConfigEntity sysAppConfigEntity = sysAppConfigMapper.selectOne(new QueryWrapper<SysAppConfigEntity>().eq("config_key",configKey));
        String configValue = sysAppConfigEntity.getConfigValue();
        JSONObject jsonObject = JSONObject.parseObject(configValue);
        Double minMeter = jsonObject.getDouble("minMeter");
        Double addMeter = jsonObject.getDouble("addMeter");
        //判断distance1是否大于minMeter，如果大于则加上addMeter；反之则不加
        if(sysAppConfigEntity != null){
            if (distance1 >= minMeter){
                distance1 +=addMeter;
            }
        }
        distance1 /= 1000.0D;
        if (distance.contains("km")){
            assertThat(distance).isEqualTo(NumberUtil.NumberFormat(distance1, "#0.00")+"km");
        }
        else{
            assertThat(distance).isEqualTo(NumberUtil.NumberFormat(distance1*0.621, "#0.00")+"mile");

        }


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> DataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setKeywords("商卡测试");

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
  