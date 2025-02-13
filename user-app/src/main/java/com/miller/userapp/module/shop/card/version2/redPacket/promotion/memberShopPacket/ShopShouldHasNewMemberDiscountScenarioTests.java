package com.miller.userapp.module.shop.card.version2.redPacket.promotion.memberShopPacket;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.entity.member.MemberPacketEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.member.MemberPacketMapper;
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

import java.util.stream.Stream;

@Scenario(scenarioID = "01JE84BAG72WBQ99GSV62N8VKC",
        scenarioName = "普通店铺配送商卡-红包适用商家列表_优惠标签_新会员优惠标签_首页-商卡二期：新会员优惠标签37",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 10, manualTestTime = 20)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNewMemberDiscountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private final Long memberCityID = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.memberCityId"));
    private final Long packageId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shop.redPacketId"));
    UserLoginRequestDTO userLoginRequestDTO;

    @BeforeAll
    void BeforeAll() {

        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        MemberCityMapper shopMemberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
//        修改会员城市表，修改会员城市表is_open_delivery_discount字段为1 运费减免开启
//        update member_city set is_open_delivery_discount=0 where member_city_id=1111378;
        shopMemberCityMapper.update(null, new LambdaUpdateWrapper<MemberCityEntity>()
                .eq(MemberCityEntity::getMemberCityId, memberCityID)
                .set(MemberCityEntity::getIsOpenDeliveryDiscount, 1));
        //         update member_packet set is_del=0 where member_city_id=1111378 and packet_id=888890186;
//        开启会员店铺红包
        MemberPacketMapper memberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
        memberPacketMapper.update(null, new LambdaUpdateWrapper<MemberPacketEntity>()
                .eq(MemberPacketEntity::getPacketId, packageId)
                .eq(MemberPacketEntity::getMemberCityId, memberCityID)
                .set(MemberPacketEntity::getIsDel, 0));
        //执行定时定时任务：店铺更新定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));

//        会员用户登录 user:17048460001  默认登录数据为实验组
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.member.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.member.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);


    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-红包适用商家列表_优惠标签_新会员优惠标签_首页-商卡二期：新会员优惠标签37")
    void memberBenefitDeliveryDiscount(ShopListRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，
        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.MEMBER_SHOP_PACKET.getType()).findFirst().get();
        System.out.println("会员红包" + memberPacket);
        assert memberPacket.getShowContentList().get(0).equals("¥15无门槛");
        assert memberPacket.getShowContentList().get(1).equals("免运费");


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

