package com.miller.userapp.module.shop.card.version2.redPacket.promotion.firstOrder;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.user.UserLabelEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.ShopNewUserLabelMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
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

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JE84BAG72WBQ99GSV62N8VK0", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-红包适用商家列表-优惠标签-新人首单标签-首页-商卡二期：新人首单标签35-新人人群1"
        , author = "shandongdong@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-红包适用商家列表-优惠标签-新人首单标签-首页-商卡二期：新人首单标签35-新人人群2")
public class ShopShouldHasFirstOrderTagCrowdSecondScenarioTests {
    //    测试数据：店铺04，营销标签类型：35
    private final Long shopId = Long.parseLong("160288176");
    private final Integer type=35;

    @BeforeAll
    static void beforeAll() {
//        人群2为登陆新人账号，且当前可领取新人权益
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String passWord =propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.for.shop.card.version2.first.order.user.password");
        String userName = propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.for.shop.card.version2.first.order.user.account2");
        String loginType = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type");
        String callingCode = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode");
        String distinctId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.distinctId");
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.userId2");
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode(callingCode);
        user.setAccount(userName);
        user.setPassword(MD5Util.string2MD5(passWord));
        user.setType(Integer.valueOf(loginType));
        user.setDistinctId(distinctId);
        UserLoginFlow.loginAndPutToken(user);
//        测试数据预处理，将user_label表数据label_id设置为2
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopNewUserLabelMapper shopNewUserLabelMapper = sqlSession.getMapper(ShopNewUserLabelMapper.class);
        shopNewUserLabelMapper.update(null, new LambdaUpdateWrapper<UserLabelEntity>().eq(UserLabelEntity::getDeviceId,distinctId).eq(UserLabelEntity::getUserId,userId).set(UserLabelEntity::getLabelId,2)
        );
    }

    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-红包适用商家列表-优惠标签-新人首单标签-首页-商卡二期：新人首单标签35-新人人群2")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasFirstOrderTagCrowdSecond(ShopListRequestDTO ShopListRequestdto){
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
        
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
