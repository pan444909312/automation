package com.miller.userapp.module.shop.card.version3.home.baseInfo.shopLabel;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.entity.shop.BdmExclusiveShopEntity;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.vo.index.BaseShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.BdmExclusiveShopMapper;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@Scenario(scenarioID = "01K0V5BE403VTH2MTGTJA437K9",
        scenarioName = "普通店铺配送商卡-SKYX01_基础信息_店铺角标_首页-商卡二期:独家店铺开启独家角标,会展示独家角标",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasExclusiveLabelScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.03.shopId"));
    private static ShopSearchMiddleMapper shopSearchMiddleMapper;

    private static ShopMapper shopMapper;
    private static BdmExclusiveShopMapper bdmExclusiveShopMapper;


    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);
        bdmExclusiveShopMapper = sqlSession.getMapper(BdmExclusiveShopMapper.class);

        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_基础信息_店铺角标_首页-商卡二期:独家店铺开启独家角标,会展示独家角标")
    void showLabel(ShopListRequestDTO shopListRequestDTO) {
        // Given

        // When
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);

        String interfaceResponse = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst()
                // 获取接口返回的字段
                .map(BaseShopIndexVO::getNewShopLabelUrl).orElseThrow();

        // Then. 校验接口返回的字段与数据库字段匹配, JSON.result.shopList[x].newShopLabelUrl = hp_shop_search_middle.new_channel_label_url（大于8.15版本返回字段为 new_channel_label_url）
        String databaseResponse = shopSearchMiddleMapper.selectOne(
                        // 查询条件，店铺ID
                        new LambdaQueryWrapper<ShopSearchMiddleEntity>().eq(ShopSearchMiddleEntity::getShopId, shopId))
                // 获取数据库字段值
                .getNewChannelLabelUrl();
        interfaceResponse = interfaceResponse.substring(interfaceResponse.lastIndexOf("/") + 1);
        databaseResponse = databaseResponse.substring(databaseResponse.lastIndexOf("/") + 1);

        ShopEntity shopEntity = shopMapper.selectOne(new QueryWrapper<ShopEntity>().eq("shop_id", shopId));

        Long count = bdmExclusiveShopMapper.selectCount(new QueryWrapper<BdmExclusiveShopEntity>().eq("shop_id", shopId));

        assertThat(count > 0).isTrue();

        assertThat(interfaceResponse).isEqualTo(databaseResponse);
        assertThat(interfaceResponse).isNotNull();
        assertThat(shopEntity.getShopBorderUrl()).isNotNull();
        assertThat(shopEntity.getShopLabelId()).isEqualTo(42);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
