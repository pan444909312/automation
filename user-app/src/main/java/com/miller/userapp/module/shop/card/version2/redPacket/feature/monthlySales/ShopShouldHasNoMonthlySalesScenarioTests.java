package com.miller.userapp.module.shop.card.version2.redPacket.feature.monthlySales;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.address.CityFunctionConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.CityFunctionConfigMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01JE88B2J6482ZXB6ZFYBXQX73", scenarioName = "商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-月售-首页-商卡二期：月售 - 月售展示开关禁用"
        , developmentTime = 10, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-月售-首页-商卡二期：月售 - 月售展示开关禁用")
public class ShopShouldHasNoMonthlySalesScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
//        开启地址配置-城市功能管理-九江市-商卡月售开关
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        CityFunctionConfigMapper cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
        cityFunctionConfigMapper.update(null, new LambdaUpdateWrapper<CityFunctionConfigEntity>().eq(CityFunctionConfigEntity::getCityId,508).eq(CityFunctionConfigEntity::getType,7).set(CityFunctionConfigEntity::getStatus,0)
        );

    }
    @DisplayName("商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-月售-首页-商卡二期：月售 - 月售展示开关禁用")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopList(ShopListRequestdto);
        ShopIndexVO shopIndexVO  = ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        assertThat(shopIndexVO.getShopMonthlySales()).isNull();

    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
         // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
