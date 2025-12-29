package com.miller.userapp.moduleEnAu.shop.card.version3.home.sideInfo.shopScore;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01K0P3K4NY5Y6HPZ6KQN73FS7F",
        scenarioName = "商卡(中文)_普通店铺配送商卡-SKYX01_辅助信息_店铺评分_首页-商卡二期：店铺评分-无数据",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoShopScoreIsTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId.stop.order"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
//        ShopSearchMiddleMapper shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
//update evaluation_score=100 where shopid=xxxx  没有则不展示
//        shopSearchMiddleMapper.update(null, new LambdaUpdateWrapper<ShopSearchMiddleEntity>()
//                .eq(ShopSearchMiddleEntity::getShopId, shopId)
//                .set(ShopSearchMiddleEntity::getPraiseAverage, 0)
//                .set(ShopSearchMiddleEntity::getShowShopEvaluation, 0)
//                .set(ShopSearchMiddleEntity::getPraiseAverageNew, 0));

    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("商卡(中文)_普通店铺配送商卡-SKYX01_辅助信息_店铺评分_首页-商卡二期：店铺评分-无数据")
    void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
//        这里分数还是会返回的，只是show字段为0
        assertThat(shopIndexVO.getPraiseAverage()).isEqualTo("5.0");
        assertThat(shopIndexVO.getShowShopEvaluation()).isEqualTo(0);
    }

    /*
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
