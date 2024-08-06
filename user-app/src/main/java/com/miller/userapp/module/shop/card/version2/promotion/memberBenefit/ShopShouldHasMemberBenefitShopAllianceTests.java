package com.miller.userapp.module.shop.card.version2.promotion.memberBenefit;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
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
public class ShopShouldHasMemberBenefitShopAllianceTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
//     用户未登录，会员店铺配置了运费减免，展示运费减免红包
       private static MemberCityMapper MemberCityMapper;

    @BeforeAll
    static void beforeAll() {
         SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        MemberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
    }
    @AfterAll
    static void afterAll() {
//        测试完成后，运费减免开启。
         UpdateWrapper<MemberCityEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_city_id", 1111378).set("is_open_delivery_discount",1);
        MemberCityMapper.update(null,updateWrapper);
    }
    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_优惠标签_会员权益_首页-商卡二期：会员权益32-店铺联盟券")
    void memberBenefitShopAllianCoupon(ShopListRequestDTO shopListRequestDTO) {
//        修改会员城市表，修改会员城市表is_open_delivery_discount字段为0 运费减免优先级>店铺联盟，因此现关闭会员减免
         UpdateWrapper<MemberCityEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_city_id", 1111378).set("is_open_delivery_discount",0);
        MemberCityMapper.update(null,updateWrapper);

//        请求首页店铺数据
        ShopListResponseDTO shopList = ShopListFlowNoLogin.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，
        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType()).findFirst().get();
        assert memberPacket.getShowContent().equals("¥15会员红包");
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

