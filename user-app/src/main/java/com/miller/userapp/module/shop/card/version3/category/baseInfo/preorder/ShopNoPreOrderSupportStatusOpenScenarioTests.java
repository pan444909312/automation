package com.miller.userapp.module.shop.card.version3.category.baseInfo.preorder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.BaseShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.category.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.category.request.ShopListRequestDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@Scenario(scenarioID = "01K0V4Y0P1WBDWRW11WDHBSXWW", scenarioName = "普通店铺配送商卡-SKYX01_基础信息_店铺可预约状态_首页-商卡二期：店铺可预约状态 - 营业不可预约"
        , author = "panjuxiang@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopNoPreOrderSupportStatusOpenScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.open.no.pre"));
    private static ShopSearchMiddleMapper shopSearchMiddleMapper;

    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("shopStatusDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_基础信息_店铺可预约状态_首页-商卡二期：店铺可预约状态 - 营业不可预约")
    void showLabel(ShopListRequestDTO shopListRequestDTO) {
        // Given

        // When
        var shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        assert shopList != null;
        var preorderClosedSupport = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst()
                // 获取接口返回的字段
                .map(BaseShopIndexVO::getPreorderClosedSupport).orElseThrow();

        // Then. 校验接口返回的字段与数据库字段匹配,
        var databaseResponse = shopSearchMiddleMapper.selectOne(
                        // 查询条件，店铺ID
                        new LambdaQueryWrapper<ShopSearchMiddleEntity>().eq(ShopSearchMiddleEntity::getShopId, shopId))
                // 获取数据库字段值
                .getPreorderClosedSupport();

        assertThat(preorderClosedSupport).isEqualTo(0);
        assertThat(databaseResponse).isFalse();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> shopStatusDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
