package com.miller.userapp.moduleEnAu.shop.card.version3.category.promotion.sortRule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.hungrypanda.app.server.entity.user.UserLabelEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.ShopNewUserLabelMapper;
import com.miller.userapp.module.data.activity.UserCdkeyInfoSql;
import com.miller.userapp.module.data.device.db.DeviceAutoRenewSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.category.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.category.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.category.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01K82X87SVGEYN42CVK5Q6ZAN5", scenarioName = "品类频道-商卡二期-SKYX实验组：红包类型标签-优先级（会员默认排序）",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("品类频道-商卡二期-SKYX实验组：红包类型标签-优先级（会员默认排序）")
public class ShopShouldRedPacketSortMemberTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.increase.shopId"));
    UserLoginRequestDTO userLoginRequestDTO;
    private final Long userId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.sort.member.userId"));
    UserCdKeyEntity userCdKeyEntity;
    private final String distinctId=new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.first.order.user.distinctId3");

    @BeforeAll
    void beforeAll() {
        //   用户登录
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.sort.member.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.sort.member.password")));
        userLoginRequestDTO.setDistinctId(distinctId);
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);

//        更新数据库，将user_label表数据label_id设置为1,使其出新人首单标签35
         SqlSession sqlSession = DBUtils.getDBOfPandaTest();
         ShopNewUserLabelMapper shopNewUserLabelMapper = sqlSession.getMapper(ShopNewUserLabelMapper.class);
         shopNewUserLabelMapper.update(null, new LambdaUpdateWrapper<UserLabelEntity>().eq(UserLabelEntity::getDeviceId,distinctId).eq(UserLabelEntity::getUserId,userId).set(UserLabelEntity::getLabelId,1)
         );
         //清除设备对应的活动数据
         DeviceAutoRenewSql deviceAutoRenewSql = new DeviceAutoRenewSql();
         deviceAutoRenewSql.deviceAutoRenew(distinctId);

         //神券信息修改:改为已膨胀10元
         UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
         userCdkeyInfoSql.updateRedPacketScopeTypeAndPrice(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId")),2,1000);

     }
     @AfterAll
     void afterAll(){
         //神券信息修改:改为待膨胀4元
         UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
         userCdkeyInfoSql.updateRedPacketScopeTypeAndPrice(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId")),1,400);

     }
     @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("品类频道-商卡二期-SKYX实验组：红包类型标签-优先级（会员默认排序）")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
         RequestUtils.getHeaders().put("uniqueToken", distinctId);
         ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
         
         
         // 查找所有红包标签
         List<ShopPromoteVO> shopPromoteVOList = shopIndexVO.getShopPromoteList().stream().
                  filter(item -> item.getTagType().equals(1)).collect(Collectors.toList());
         List<Integer>tags= shopPromoteVOList.stream().map(item -> item.getType()).collect(Collectors.toList());
         // 顺序：新人、会员红包\神券、店铺红包、
         assertThat( tags).isEqualTo(new ArrayList<Integer>(List.of(35, 37, 41, 42, 42) ));

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