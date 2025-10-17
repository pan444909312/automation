package com.miller.userapp.module.shop.card.version3.userPack.baseInfo.status;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.common.enums.ShopStatusEnum;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.BaseShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@Scenario(scenarioID = "01K7JWZ6K5KT6A6ZF9DMT7D6Y8",
        scenarioName = "普通店铺配送商卡-自取频道-SKYX01_基础信息_店铺营业状态_自取频道:店铺营业状态-忙碌",
        author = "panjuxiang@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopStatusIsBusyScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.03.shopId"));
    private static ShopSearchMiddleMapper shopSearchMiddleMapper;

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);

    }
    @AfterAll
    void afterAll() {
        UpdateWrapper<ShopSearchMiddleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("shop_id", shopId);
        updateWrapper.set("new_order_prepare_extend_time",0);

        shopSearchMiddleMapper.update(updateWrapper);

    }

    @MethodSource("shopStatusDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-自取频道-SKYX01_基础信息_店铺营业状态_自取频道:店铺营业状态-忙碌")
    void showLabel(ShopListRequestDTO shopListRequestDTO) {

        UpdateWrapper<ShopSearchMiddleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("shop_id", shopId);
        updateWrapper.set("new_order_prepare_extend_time",60);

        shopSearchMiddleMapper.update(updateWrapper);

        var shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        assert shopList != null;
        var interfaceResponse = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst()
                // 获取接口返回的字段
                .map(BaseShopIndexVO::getShopStatus).orElseThrow();

        // Then. 校验接口返回的字段与数据库字段匹配,
        var databaseResponse = shopSearchMiddleMapper.selectOne(
                        // 查询条件，店铺ID
                        new LambdaQueryWrapper<ShopSearchMiddleEntity>().eq(ShopSearchMiddleEntity::getShopId, shopId))
                // 获取数据库字段值
                .getShopStatus();

        assertThat(interfaceResponse).isEqualTo(ShopStatusEnum.OPEN.getCode());
        assertThat(databaseResponse).isFalse();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> shopStatusDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 自取频道店铺流必须传经纬度
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setLongitude("115.954100");
        shopListRequestDTO.setLatitude("29.660580");
        shopListRequestDTO.setIsNeedMarketCategory(1);
        shopListRequestDTO.setMarketCategoryId(0); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
