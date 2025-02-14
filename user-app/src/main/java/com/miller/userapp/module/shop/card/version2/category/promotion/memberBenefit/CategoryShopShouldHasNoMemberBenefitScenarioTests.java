package com.miller.userapp.module.shop.card.version2.category.promotion.memberBenefit;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.entity.member.MemberPacketEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.member.MemberPacketMapper;
import com.miller.userapp.module.shop.card.version2.category.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.category.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.response.ShopListResponseDTO;
import com.miller.userapp.module.shop.card.version2.home.baseinfo.ShopShouldHasLabelScenarioTests;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JDKG14ZSYWKP8B9HE7QN9XQN",
        scenarioName = "普通店铺配送商卡-品类频道_优惠标签_会员权益_首页-商卡二期：会员权益32-不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class CategoryShopShouldHasNoMemberBenefitScenarioTests {
        private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    //     用户未登录，会员店铺配置了运费减免，展示运费减免红包
        private final Long memberCityID = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.memberCityId"));
        private final Long packageId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shop.redPacketId")) ;
        private static MemberCityMapper shopMemberCityMapper ;
        private static MemberPacketMapper memberPacketMapper;

    @BeforeAll
    void beforeAll() {
          var myheaders = new HashMap<String, Object>();
            myheaders.put("Content-Type", "application/json");
            RequestUtils.setHeaders(myheaders);
         SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        MemberCityMapper shopMemberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
        MemberPacketMapper memberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
//        删除配置  会员运费减免+店铺联盟  则不展示
//        修改会员运费减免 状态为关闭
//        update member_city set is_open_delivery_discount=0 where member_city_id=1111378;
         shopMemberCityMapper.update(null, new LambdaUpdateWrapper<MemberCityEntity>()
                .eq(MemberCityEntity::getMemberCityId, memberCityID)
                 .set(MemberCityEntity::getIsOpenDeliveryDiscount, 0));

         //        修改会员权益店铺联盟券的=已删除
//        update member_packet set is_del=1 where packet_id=888890186 and member_city_id=1111378;
        memberPacketMapper.update(null, new LambdaUpdateWrapper<MemberPacketEntity>()
                .eq(MemberPacketEntity::getPacketId, packageId)
                .eq(MemberPacketEntity::getMemberCityId, memberCityID)
                .set(MemberPacketEntity::getIsDel, 1));

//       执行定时定时任务-店铺数据更新
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));

    }
    @AfterAll
    void afterAll() {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        MemberCityMapper shopMemberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
        MemberPacketMapper memberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
//        还原数据  会员运费减免+店铺联盟  均开启
      shopMemberCityMapper.update(null, new LambdaUpdateWrapper<MemberCityEntity>()
                .eq(MemberCityEntity::getMemberCityId, memberCityID)
                 .set(MemberCityEntity::getIsOpenDeliveryDiscount, 1));

       memberPacketMapper.update(null, new LambdaUpdateWrapper<MemberPacketEntity>()
                .eq(MemberPacketEntity::getPacketId, packageId)
                .eq(MemberPacketEntity::getMemberCityId, memberCityID)
                .set(MemberPacketEntity::getIsDel, 0));

//       执行定时定时任务-店铺数据更新
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));


    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-品类频道_优惠标签_会员权益_首页-商卡二期：会员权益32-不展示")
//    删除配置  会员运费减免+店铺联盟  则不展示
    void memberBenefitShopAllianCoupon(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();


        //断言-的ShopPromoteList列表， 没有会员红包权益
        assertThat(shopIndexVO.getShopPromoteList().stream().noneMatch(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType())).isTrue();


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

