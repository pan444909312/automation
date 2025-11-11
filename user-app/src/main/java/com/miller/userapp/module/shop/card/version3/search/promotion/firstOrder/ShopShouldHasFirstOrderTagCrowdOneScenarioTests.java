package com.miller.userapp.module.shop.card.version3.search.promotion.firstOrder;

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
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
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
@Scenario(scenarioID = "01K0RBVKDDSN56ZRXPDD79BZ7V", scenarioName = "用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-优惠标签-新人首单标签-搜索列表-商卡二期：新人首单标签35-新人人群1"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-优惠标签-新人首单标签-搜索列表-商卡二期：新人首单标签35-新人人群1")
public class ShopShouldHasFirstOrderTagCrowdOneScenarioTests {
    //    测试数据：店铺04，营销标签类型：35
    private final Long shopId = Long.parseLong("160288176");
//    private static com.miller.userapp.mapper.shop.ShopNewUserLabelMapper ShopNewUserLabelMapper;
    private final Integer type=35;
    private static PropertiesUtils propertiesUtils=new PropertiesUtils();
    private static final String distinctId=propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.distinctId4");

    @BeforeAll
    static void beforeAll() {
//        人群1为登陆新人账号，且当前不可领取新人权益，用户登陆数据如下
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String passWord =propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.for.shop.card.version2.first.order.user.password");
        String userName = propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.for.shop.card.version2.first.order.user.account1");
        String loginType = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type");
        String callingCode = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode");
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.userId1");
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode(callingCode);
        user.setAccount(userName);
        user.setPassword(MD5Util.string2MD5(passWord));
        user.setType(Integer.valueOf(loginType));
        user.setDistinctId(distinctId);
        UserLoginFlow.loginAndPutToken(user);
//        更新数据库，将user_label表数据label_id设置为1
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopNewUserLabelMapper shopNewUserLabelMapper = sqlSession.getMapper(ShopNewUserLabelMapper.class);
        shopNewUserLabelMapper.update(null, new LambdaUpdateWrapper<UserLabelEntity>().eq(UserLabelEntity::getDeviceId,distinctId).eq(UserLabelEntity::getUserId,userId).set(UserLabelEntity::getLabelId,1)
        );
    }

    @DisplayName("用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-优惠标签-新人首单标签-搜索列表-商卡二期：新人首单标签35-新人人群1")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasFirstOrderTagCrowdOne(ShopListRequestDTO ShopListRequestdto){
        RequestUtils.getHeaders().put("uniqueToken", distinctId);
        ShopListResponseDTO ShopListResponseDto= ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        List<ShopPromoteVO> shopPromoteList =ShopListResponseDto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        List <ShopPromoteVO> shopPromoteTypeList=shopPromoteList.stream().filter(item -> item.getType().equals(type)).toList();
        assertThat(shopPromoteTypeList.size()).isEqualTo(1);
        String showContent=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getShowContent).orElseThrow();
        assertThat(showContent).isEqualTo("无门槛减¥9");
        Integer tagType=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getTagType).orElseThrow();
        assertThat(tagType).isEqualTo(1);
    }
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
