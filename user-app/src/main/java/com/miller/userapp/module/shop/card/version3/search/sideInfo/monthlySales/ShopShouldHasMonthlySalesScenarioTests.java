package com.miller.userapp.module.shop.card.version3.search.sideInfo.monthlySales;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01K9VH77WP0C58RKEXC7J548FA", scenarioName = "用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-辅助信息-月售-搜索列表-商卡二期：月售"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-辅助信息-月售-搜索列表-商卡二期：月售")
public class ShopShouldHasMonthlySalesScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
////        开启地址配置-城市功能管理-九江市-商卡月售开关
//        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
//        CityFunctionConfigMapper cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
//        cityFunctionConfigMapper.update(null, new LambdaUpdateWrapper<CityFunctionConfigEntity>().eq(CityFunctionConfigEntity::getCityId,508).eq(CityFunctionConfigEntity::getType,7).set(CityFunctionConfigEntity::getStatus,1)
//        );
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("线程中断异常", e);
//        }

    }
    @DisplayName("用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-辅助信息-月售-搜索列表-商卡二期：月售")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        String monthlySales =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopMonthlySales).orElseThrow();
        assertThat(monthlySales).isEqualTo("月售6000+");

    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setKeywords("商卡测试");
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
