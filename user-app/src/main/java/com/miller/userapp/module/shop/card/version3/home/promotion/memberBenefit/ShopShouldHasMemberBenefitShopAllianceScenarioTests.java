package com.miller.userapp.module.shop.card.version3.home.promotion.memberBenefit;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.entity.member.MemberPacketEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.member.MemberPacketMapper;
import com.miller.userapp.module.shop.card.version3.home.promotion.memberBenefit.flow.ShopListFlowNoLogin;
import com.miller.userapp.module.shop.card.version3.home.promotion.memberBenefit.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.home.promotion.memberBenefit.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


@Scenario(scenarioID = "01J46NM6T46BDDJFHRFM4RN9YX", scenarioName = "普通店铺配送商卡-SKYX01_优惠标签_会员权益_首页-商卡二期：会员权益32-店铺联盟券",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5, manualTestTime = 15)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasMemberBenefitShopAllianceScenarioTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
     private final Long memberCityID = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.memberCityId"));
     private final Long packageId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shop.redPacketId")) ;

     //     用户未登录，运费减免关闭，配置店铺联盟红包，展示店铺联盟红包

    @BeforeAll
     void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        MemberCityMapper shopMemberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
//        修改会员城市表，修改会员城市表is_open_delivery_discount字段为0 运费减免关闭
//        update member_city set is_open_delivery_discount=0 where member_city_id=1111378;
         shopMemberCityMapper.update(null, new LambdaUpdateWrapper<MemberCityEntity>()
                .eq(MemberCityEntity::getMemberCityId, memberCityID)
                 .set(MemberCityEntity::getIsOpenDeliveryDiscount, 0));

//         update member_packet set is_del=0 where member_city_id=1111378 and packet_id=888890186;
         MemberPacketMapper memberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
         memberPacketMapper.update(null, new LambdaUpdateWrapper<MemberPacketEntity>()
                .eq(MemberPacketEntity::getPacketId, packageId)
                .eq(MemberPacketEntity::getMemberCityId, memberCityID)
                .set(MemberPacketEntity::getIsDel, 0));

        //执行定时定时任务：店铺更新定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));

    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_优惠标签_会员权益_首页-商卡二期：会员权益32-店铺联盟券")
    void memberBenefitShopAllianCoupon(ShopListRequestDTO shopListRequestDTO) {


//        请求首页店铺数据
        ShopListResponseDTO shopList = ShopListFlowNoLogin.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，
        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType()).findFirst().get();
        assert memberPacket.getShowContent().equals("¥15无门槛");

    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

