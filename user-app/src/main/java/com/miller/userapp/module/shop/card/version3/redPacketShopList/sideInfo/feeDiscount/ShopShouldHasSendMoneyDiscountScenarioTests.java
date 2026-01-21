package com.miller.userapp.module.shop.card.version3.redPacketShopList.sideInfo.feeDiscount;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.address.CityFunctionConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.CityFunctionConfigMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.response.ShopListResponseDTO;
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
@Scenario(scenarioID = "01KE9JG2QWDYJ9ERQ4HSYH8C5C", scenarioName = "用户-品类频道店铺流-商卡(中文)-普通店铺配送商卡-SKYX01-辅助信息-运费减免优惠-品类频道-商卡二期：运费减免优惠"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-品类频道店铺流-商卡(中文)-普通店铺配送商卡-SKYX01-辅助信息-运费减免优惠-品类频道-商卡二期：运费减免优惠")
public class ShopShouldHasSendMoneyDiscountScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        //        开启地址配置-城市功能管理-九江市-商卡配送费优惠开关
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        CityFunctionConfigMapper cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
        cityFunctionConfigMapper.update(null, new LambdaUpdateWrapper<CityFunctionConfigEntity>().eq(CityFunctionConfigEntity::getCityId,508).eq(CityFunctionConfigEntity::getType,9).set(CityFunctionConfigEntity::getStatus,1)
        );
    }

    @DisplayName("用户-品类频道店铺流-商卡(中文)-普通店铺配送商卡-SKYX01-辅助信息-运费减免优惠-品类频道-商卡二期：运费减免优惠")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyDiscountInfo(ShopListRequestDTO ShopListRequestdto) {
        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        Long sendMoneyDiscount = ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map(ShopIndexVO::getSendMoneyDiscount).orElseThrow();
        assertThat(sendMoneyDiscount).isEqualTo(100);
        //    DataProvider改为在测试用例文件里写,提供测试数据
    }
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setCityName("九江市");
        shopListRequestDTO.setShopCategoryIds("[3896,3914,5486]");// 开发代码Bug，没有对 null 进行判断，应该默认给false的 // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
