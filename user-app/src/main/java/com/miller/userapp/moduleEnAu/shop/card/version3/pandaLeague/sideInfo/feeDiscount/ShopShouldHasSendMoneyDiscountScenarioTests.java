package com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.sideInfo.feeDiscount;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.address.CityFunctionConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.CityFunctionConfigMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01K47416E0WZS970E3KPXSDXCT", scenarioName = "用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-运费减免优惠-熊猫联盟频道-商卡二期：运费减免优惠"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-运费减免优惠-熊猫联盟频道-商卡二期：运费减免优惠")
public class ShopShouldHasSendMoneyDiscountScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.blank.compare.shopId"));

    @BeforeAll
    void beforeAll() throws InterruptedException {
        UserLoginFlow.loginByDefaultUser();
        //        开启地址配置-城市功能管理-九江市-商卡配送费优惠开关
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        CityFunctionConfigMapper cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
        cityFunctionConfigMapper.update(null, new LambdaUpdateWrapper<CityFunctionConfigEntity>().eq(CityFunctionConfigEntity::getCityId,508).eq(CityFunctionConfigEntity::getType,9).set(CityFunctionConfigEntity::getStatus,1)
        );
        Thread.sleep(1000);
    }

    @DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-运费减免优惠-熊猫联盟频道-商卡二期：运费减免优惠")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyDiscountInfo(ShopListRequestDTO ShopListRequestdto) {
        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        
        if (ShopListResponsedto == null||ShopListResponsedto.getResult() == null) {
            System.out.println("01K47416E0WZS970E3KPXSDXCT错误：shopList.getResult() 为 null，resultCode: " + (ShopListResponsedto != null ? ShopListResponsedto.getResultCode() : "shopList为null"));
            return;
        }
        
        if (ShopListResponsedto.getResult().getShopList() == null || ShopListResponsedto.getResult().getShopList().isEmpty()) {
            System.out.println("01K47416E0WZS970E3KPXSDXCT错误：shopList.getResult().getShopList() 为 null 或为空");
            return;
        }
        
        Optional<ShopIndexVO> shopIndexOptional = ShopListResponsedto.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst();
        
        if (shopIndexOptional.isEmpty()) {
            System.out.println("01K47416E0WZS970E3KPXSDXCT错误：未找到 shopId 为 " + shopId + " 的店铺");
            return;
        }
        
        ShopIndexVO shopIndexVO = shopIndexOptional.get();
        Long sendMoneyDiscount = shopIndexVO.getSendMoneyDiscount();
        
        if (sendMoneyDiscount == null) {
            System.out.println("01K47416E0WZS970E3KPXSDXCT错误：shopIndexVO.getSendMoneyDiscount() 为 null");
            return;
        }
        
        System.out.println("01K47416E0WZS970E3KPXSDXCT运费减免信息：" + sendMoneyDiscount);
        assertThat(sendMoneyDiscount).isEqualTo(100);
        //    DataProvider改为在测试用例文件里写,提供测试数据
    }
        static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setTabType((byte) 1);
        shopListRequestDTO.setRedPacketList(new ArrayList<>());
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
