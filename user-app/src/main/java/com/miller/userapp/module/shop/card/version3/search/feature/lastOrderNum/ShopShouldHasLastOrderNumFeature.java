package com.miller.userapp.module.shop.card.version3.search.feature.lastOrderNum;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.consants.IndexListConstants;
import com.hungrypanda.app.server.common.enums.index.shopFeature.ShopFeatureEnum;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.entity.data.KanbanPreviewDaysEntity;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.base.SysAppConfigMapper;
import com.miller.userapp.mapper.shop.KanbanPreviewDaysMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01K0V7PH8ZT17GZDKXCQGMNKC4",
        scenarioName = "搜索列表商卡-SKYX01_营销标_下单人数标签_满足条件，返回下单人数标签",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasLastOrderNumFeature {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

    private KanbanPreviewDaysMapper kanbanPreviewDaysMapper;

    private SysAppConfigMapper configMapper;


    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        kanbanPreviewDaysMapper = sqlSession.getMapper(KanbanPreviewDaysMapper.class);
        configMapper = sqlSession.getMapper(SysAppConfigMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索列表商卡-SKYX01_营销标_人下单人数标签_满足条件，返回下单人数标签")
    void shouldExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopFeatureVO shopFeatureVO = shopIndexVO.getShopFeatureList().stream().
                filter(item -> item.getType().equals(ShopFeatureEnum.LAST_DAY_ORDER.getType())).findFirst().orElse(null);

        KanbanPreviewDaysEntity kanbanPreviewDaysEntity = kanbanPreviewDaysMapper.selectOne(new QueryWrapper<KanbanPreviewDaysEntity>().eq("shop_id", shopId));
        SysAppConfigEntity sysAppConfigEntity = configMapper.selectOne(new QueryWrapper<SysAppConfigEntity>().eq("config_key", "USA:SHOP_LAST_DAY_ORDER_NUM"));
        Long shopOrderNum = (long) (kanbanPreviewDaysEntity.getOrderNum() * Double.parseDouble(sysAppConfigEntity.getConfigValue()));


        assertThat(shopOrderNum > 50).isTrue();
        assertThat(shopFeatureVO.getShowContent()).isEqualTo(String.format(IndexListConstants.LAST_DAY_ORDER, shopOrderNum));
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
