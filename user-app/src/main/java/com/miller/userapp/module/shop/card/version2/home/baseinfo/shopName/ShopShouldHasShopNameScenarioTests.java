package com.miller.userapp.module.shop.card.version2.home.baseinfo.shopName;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.BaseShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商卡(中文)_普通店铺配送商卡_基础信息_店铺名称_首页-商卡二期:店铺名称
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/07/28 12:17:39
 */
@Scenario(scenarioID = "01J3VJ3JM9NZNW9BH5JEBWCN2F",
        scenarioName = "商卡(中文)_普通店铺配送商卡_基础信息_店铺名称_首页-商卡二期:店铺名称",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 3 * 60, maintenanceTime = 0, manualTestTime = 30)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasShopNameScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private static ShopSearchMiddleMapper shopSearchMiddleMapper;

    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("shopNameDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_基础信息_店铺名称_首页-商卡二期:店铺名称")
    void showLabel(ShopListRequestDTO shopListRequestDTO) {
        // Given

        // When
        var shopList = ShopListFlow.getShopList(shopListRequestDTO);

        var interfaceResponse = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst()
                // 获取接口的字段值
                .map(BaseShopIndexVO::getShopName).orElseThrow();

        // Then. 校验接口返回的字段与数据库字段匹配，hp_shop_search_middle.shop_name  = shop.shop_name
        var databaseResponse = shopSearchMiddleMapper.selectOne(
                        // 查询条件，店铺ID
                        new LambdaQueryWrapper<ShopSearchMiddleEntity>().eq(ShopSearchMiddleEntity::getShopId, shopId))
                // 获取数据库字段值
                .getShopName();

        assertThat(interfaceResponse).isEqualTo(databaseResponse);
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> shopNameDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
