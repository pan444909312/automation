package com.miller.userapp.module.shop.card.version2.category.promotion.firstOrder;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.user.UserLabelEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.ShopNewUserLabelMapper;
import com.miller.userapp.module.data.device.db.DeviceAutoRenewSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.category.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.category.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JC2PD4HEBHJEV7WBR9M4N87M", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-品类频道-优惠标签-新人首单标签-品类频道-商卡二期：新人首单标签35-新人人群1"
        , developmentTime = 60, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-品类频道-优惠标签-新人首单标签-品类频道-商卡二期：新人首单标签35-新人人群3")
public class ShopShouldHasFirstOrderTagCrowdThirdScenarioTests {
//    测试数据：店铺04，营销标签类型：35
    private final Long shopId = Long.parseLong("160288176");
    private final Integer type=35;

    @BeforeAll
    static void beforeAll() {
//        人群3为未登录，无需登陆
        // 这里需要测试未登录的情况，所以 RequestUtils.setHeaders(header)
        var myheaders = new HashMap<String, Object>();
        String deviceId="d88a89d4913c70bd";
        myheaders.put("Content-Type", "application/json");
        myheaders.put("uniquetoken", deviceId);
        RequestUtils.setHeaders(myheaders);
//        UserLoginFlow.loginByDefaultUser();
        //        测试数据预处理，将user_label表数据label_id设置为3
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String distinctId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.distinctId");
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopNewUserLabelMapper shopNewUserLabelMapper = sqlSession.getMapper(ShopNewUserLabelMapper.class);
        shopNewUserLabelMapper.update(null, new LambdaUpdateWrapper<UserLabelEntity>().eq(UserLabelEntity::getDeviceId,distinctId).set(UserLabelEntity::getLabelId,3)
        );
//        清除设备对应的活动数据
        DeviceAutoRenewSql deviceAutoRenewSql = new DeviceAutoRenewSql();
        deviceAutoRenewSql.deviceAutoRenew(distinctId);
    }

    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-品类频道-优惠标签-新人首单标签-品类频道-商卡二期：新人首单标签35-新人人群3")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasFirstOrderTagCrowdThird(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponseDto= ShopListFlow.getShopList(ShopListRequestdto);
        List<ShopPromoteVO> shopPromoteList =ShopListResponseDto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        List <ShopPromoteVO> shopPromoteTypeList=shopPromoteList.stream().filter(item -> item.getType().equals(type)).toList();
        assertThat(shopPromoteTypeList.size()).isEqualTo(1);
        String showContent=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getShowContent).orElseThrow();
        assertThat(showContent).isEqualTo("无门槛减¥8");
    }
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
