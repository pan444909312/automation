package com.miller.userapp.module.shop.card.version3.searchRecommend.sideInfo.monthlySales;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.searchRecommend.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.searchRecommend.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.searchRecommend.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01KJ6V2PD3THERT8YEQDHNCAAE", scenarioName = "用户-搜索推荐列表店铺流-商卡(中文)-搜索推荐列表商卡-SKYX01-辅助信息-月售-搜索推荐列表-商卡二期：月售 - 月售展示开关禁用"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-搜索推荐列表店铺流-商卡(中文)-搜索推荐列表商卡-SKYX01-辅助信息-月售-搜索推荐列表-商卡二期：月售 - 月售展示开关禁用")
public class ShopShouldHasNoMonthlySalesScenarioTests {
    //    测试店铺
    private final Long shopId = 820422420L;

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
//        开启地址配置-城市功能管理-九江市-商卡月售开关
//        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
//        CityFunctionConfigMapper cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
//        cityFunctionConfigMapper.update(null, new LambdaUpdateWrapper<CityFunctionConfigEntity>().eq(CityFunctionConfigEntity::getCityId,508).eq(CityFunctionConfigEntity::getType,7).set(CityFunctionConfigEntity::getStatus,0)
//        );
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("线程中断异常", e);
//        }

    }
    @DisplayName("用户-搜索推荐列表店铺流-商卡(中文)-搜索推荐列表商卡-SKYX01-辅助信息-月售-搜索推荐列表-商卡二期：月售 - 月售展示开关禁用")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListRequestDTO ShopListRequestdto){
        // 沈阳经纬度
        RequestUtils.getHeaders().put("latitude", "41.80478");
        RequestUtils.getHeaders().put("longitude", "123.43297");

        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        ShopIndexVO shopIndexVO  = ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        assertThat(shopIndexVO.getShopMonthlySales()).isNull();

    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setKeywords("推荐");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        ArrayList<Long> shopIdList = new ArrayList<>();
        shopIdList.add(45367036L);
        shopIdList.add(930937488L);
        shopListRequestDTO.setShopIdList(shopIdList);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
