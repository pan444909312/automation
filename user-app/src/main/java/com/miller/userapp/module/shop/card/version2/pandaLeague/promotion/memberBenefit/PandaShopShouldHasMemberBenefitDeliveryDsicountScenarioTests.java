package com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.memberBenefit;

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
import com.miller.userapp.module.shop.card.version2.pandaLeague.dataProvider.PandaLeagueDataProvider;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListPandaLeagueFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

@Scenario(scenarioID = "01JD75MYXD7VTR4ASR8CACTM2D",
        scenarioName = "普通店铺配送商卡-熊猫联盟频道_会员权益_首页-商卡二期：会员权益32-会员运费免减",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class PandaShopShouldHasMemberBenefitDeliveryDsicountScenarioTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
     private final Long memberCityID = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.memberCityId"));
     private final Long packageId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shop.redPacketId")) ;

     //     用户未登录，会员店铺配置了运费减免，展示运费减免红包
    @BeforeAll
    void beforeAll() {
           var myheaders = new HashMap<String, Object>();
            myheaders.put("Content-Type", "application/json");
            RequestUtils.setHeaders(myheaders);
        //        运费减免开启。
             SqlSession sqlSession = DBUtils.getDBOfPandaTest();
            MemberCityMapper shopMemberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
              shopMemberCityMapper.update(null, new LambdaUpdateWrapper<MemberCityEntity>()
                .eq(MemberCityEntity::getMemberCityId, memberCityID)
                 .set(MemberCityEntity::getIsOpenDeliveryDiscount, 1));

//                update member_packet set is_del=0 where member_city_id=1111378 and packet_id=888890186;
         MemberPacketMapper memberPacketMapper = sqlSession.getMapper(MemberPacketMapper.class);
         memberPacketMapper.update(null, new LambdaUpdateWrapper<MemberPacketEntity>()
                .eq(MemberPacketEntity::getPacketId, packageId)
                .eq(MemberPacketEntity::getMemberCityId, memberCityID)
                .set(MemberPacketEntity::getIsDel, 1));

                   //执行定时定时任务：店铺更新定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));

    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-熊猫联盟频道_优惠标签_会员权益_首页-商卡二期：会员权益32-会员运费免减")
    void memberBenefitDeliveryDsicount(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {
          ShopListResponseDTO shopList = ShopListPandaLeagueFlow.getShopList(shopListPandaLeagueRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，
        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType()).findFirst().get();
        assert memberPacket.getShowContent().equals("免运费");



    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
             return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));

    }
}

