package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.returnedVisitor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.address.CityFunctionConfigEntity;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.ShopFeatureTypeConstant;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.CityFunctionConfigMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
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
 * @since 2024/8/23 9:24
 */
@Scenario(scenarioID = "01J5WMVHCECNCSWBP13V103YFT",
        scenarioName = "商卡(中文)_普通店铺配送商卡_营销标_标签3_回头客_首页-商卡二期：回头客 - 回头客展示开关禁用",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoReturnedVisitorFeature {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.02.shopId"));
    private final String cityId = new PropertiesUtils().getProperty(this.getClass(), "user.app.auto.test.city.id");
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private CityFunctionConfigMapper cityFunctionConfigMapper;

    @BeforeAll
    void beforeAll(){
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
        cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
        UpdateWrapper<CityFunctionConfigEntity> cityFunctionConfigEntityUpdateWrapper = new UpdateWrapper<>();
        cityFunctionConfigEntityUpdateWrapper.eq("type",8).eq("city_id",cityId);
        CityFunctionConfigEntity cityFunctionConfigEntity = new CityFunctionConfigEntity();
        //关闭城市回头客开关
        cityFunctionConfigEntity.setStatus(0);
        cityFunctionConfigMapper.update(cityFunctionConfigEntity,cityFunctionConfigEntityUpdateWrapper);

    }

    @AfterAll
    void afterAll(){
        UpdateWrapper<CityFunctionConfigEntity> cityFunctionConfigEntityUpdateWrapper = new UpdateWrapper<>();
        cityFunctionConfigEntityUpdateWrapper.eq("type",8).eq("city_id",cityId);
        CityFunctionConfigEntity cityFunctionConfigEntity = new CityFunctionConfigEntity();
        //开启城市回头客开关
        cityFunctionConfigEntity.setStatus(1);
        cityFunctionConfigMapper.update(cityFunctionConfigEntity,cityFunctionConfigEntityUpdateWrapper);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_营销标_标签3_回头客_首页-商卡二期：回头客 - 回头客展示开关禁用")
    void shouldNotExistReturnedVisitorFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

        //遍历店铺的ShopFeatureList列表，如果没有type=3的营销标签类型则返回true
        boolean flag = shopIndexVO.getShopFeatureList().stream().noneMatch(item -> item.getType().equals(ShopFeatureTypeConstant.RETURNED_VISITOR));

        assertThat(flag).isTrue();
        assertThat(shopIndexVO.getRepeatCustomer()).isEqualTo(shopSearchMiddleEntity.getRepeatCustomer());


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
