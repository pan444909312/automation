package com.miller.userapp.module.shop.card.version2.promotion.firstOrder;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.user.UserLabelEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.ShopNewUserLabelMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.promotion.firstOrder.flow.ShopListFlowLogin;
import com.miller.userapp.module.shop.card.version2.promotion.firstOrder.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.promotion.firstOrder.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
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
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-优惠标签-新人首单标签-展示")
public class ShopShouldHasFirstOrderTagCrowdOneTests {
    //    测试数据：店铺04，营销标签类型：35
    private final Long shopId = Long.parseLong("160288176");
    private static com.miller.userapp.mapper.shop.ShopNewUserLabelMapper ShopNewUserLabelMapper;
    private final Integer type=35;
    @BeforeAll
    static void beforeAll() {
//        人群1为登陆新人账号，且当前不可领取新人权益
        String passWord ="123456";
        String userName = "15100000021";
        String loginType = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type");
        String callingCode = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode");
        String distinctId = "d88a89d4913c70bd";
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode(callingCode);
        user.setAccount(userName);
        user.setPassword(MD5Util.string2MD5(passWord));
        user.setType(Integer.valueOf(loginType));
        user.setDistinctId(distinctId);
        UserLoginFlow.loginAndPutToken(user);
//        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopNewUserLabelMapper = sqlSession.getMapper(ShopNewUserLabelMapper.class);
    }

    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-优惠标签-新人首单标签-首页-商卡二期：新人首单标签35-新人人群1")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasFirstOrderTagCrowdThird(ShopListRequestDTO ShopListRequestdto){
        HashMap<String, Object> map = new HashMap<>();
        String deviceId = "d88a89d4913c70bd";
        map.put("device_id", deviceId);
        List<UserLabelEntity> resultList = ShopNewUserLabelMapper.selectByMap(map);
        System.out.println("label表查询结果："+resultList);
//        测试数据预处理，将user_label表数据label_id设置为3
        UpdateWrapper<UserLabelEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("device_id", deviceId).set("label_id",1);
        ShopNewUserLabelMapper.update(null,updateWrapper);
        ShopListResponseDTO ShopListResponsedto= ShopListFlowLogin.getShopList(ShopListRequestdto);
        List<ShopPromoteVO> shopPromoteList =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        List <ShopPromoteVO> shopPromoteTypeList=shopPromoteList.stream().filter(item -> item.getType().equals(type)).toList();
        assertThat(shopPromoteTypeList.size()).isEqualTo(1);
        String showContent=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getShowContent).orElseThrow();
        assertThat(showContent).isEqualTo("无门槛减¥9");
    }
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
