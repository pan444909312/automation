package com.miller.userapp.module.shop.card.version2.promotion.memberBenefit;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.entity.member.MemberPacketEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.member.MemberPacketMapper;
import com.miller.userapp.module.shop.card.version2.baseinfo.ShopShouldHasLabelScenarioTests;
import com.miller.userapp.module.shop.card.version2.promotion.memberBenefit.flow.ShopListFlowNoLogin;
import com.miller.userapp.module.shop.card.version2.promotion.memberBenefit.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.promotion.memberBenefit.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasNoMemberBenefitTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
//     用户未登录，会员店铺配置了运费减免，展示运费减免红包
        private static final Long  memberCityID = Long.parseLong("1111378");;
        private static final Long packageId = Long.parseLong("888890186") ;
       private static MemberCityMapper ShopMemberCityMapper ;
       private static MemberPacketMapper MemberPacketMapper ;

    @BeforeAll
    static void beforeAll() {
         SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopMemberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
        MemberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
//        修改会员城市表，修改会员城市表is_open_delivery_discount字段为0 运费减免优先级>店铺联盟，因此现关闭会员减免
//        update member_city set is_open_delivery_discount=0 where   member_city_id=1111378;
         UpdateWrapper<MemberCityEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_city_id", memberCityID).set("is_open_delivery_discount",0);
        ShopMemberCityMapper.update(null,updateWrapper);
//        修改会员权益店铺联盟券的=已删除
//        update member_packet set is_del=1 where packet_id=888890186 and member_city_id=1111378;
        UpdateWrapper<MemberPacketEntity> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("packet_id", packageId).eq("member_city_id", memberCityID).set("is_del",1);
        MemberPacketMapper.update(null,updateWrapper2);
//       执行定时定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.index.update.id"));

    }
    @AfterAll
    static void afterAll() {
//        测试完成后，运费减免开启。
        UpdateWrapper<MemberCityEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_city_id", memberCityID).set("is_open_delivery_discount",1);
        ShopMemberCityMapper.update(null,updateWrapper);


//        修改会员权益店铺联盟券的=正常（未删除）
         UpdateWrapper<MemberPacketEntity> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.eq("packet_id", packageId).eq("member_city_id", memberCityID).set("is_del",1);
        MemberPacketMapper.update(null,updateWrapper2);


    }
    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_优惠标签_会员权益_首页-商卡二期：会员权益32-无会员权益")
//    删除配置  会员运费减免+店铺联盟  则不展示
    void memberBenefitShopAllianCoupon(ShopListRequestDTO shopListRequestDTO) {
//
//        获取首页店铺列表数据
        ShopListResponseDTO shopList = ShopListFlowNoLogin.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();


        //遍历店铺的ShopPromoteList列表， 没有会员红包权益
        boolean memberPacket= shopIndexVO.getShopPromoteList().stream().
                noneMatch(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType());
        assert memberPacket;
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

