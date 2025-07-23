package com.miller.userapp.module.shop.card.version3.home.baseInfo.shopTag;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.member.MemberCityEntity;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01K0V5E94ADBN3YE5226AMGTQ2",
        scenarioName = "普通店铺配送商卡-SKYX01_基础信息_店前标签：VIP标签_首页-商卡二期：VIP标签",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasMemberTag {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.04.shopId"));
    private final Long memberCityID = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.memberCityId"));

    private MemberCityMapper memberCityMapper;

    private ShopSearchMiddleMapper shopSearchMiddleMapper;

    @BeforeAll
    void beforeAll() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5((new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.password"))));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        memberCityMapper = sqlSession.getMapper(MemberCityMapper.class);
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);

    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_基础信息_店前标签：VIP标签_首页-商卡二期：VIP标签")
    void memberBenefitDeliveryDsicount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        MemberCityEntity memberCityId = memberCityMapper.selectOne(new QueryWrapper<MemberCityEntity>().eq("member_city_id", memberCityID));

        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

        assertThat(shopSearchMiddleEntity.getFixedTagSts()).isEqualTo(Byte.valueOf("1"));
        assertThat(memberCityId.getFixedTagSts()).isEqualTo(1);

        assertThat(shopIndexVO.getActivityTag()).isEqualTo("https://static.hungrypanda.co/panda/174307129084271a7f5b22b5c4525bbf0374301153496.png");
        assertThat(shopIndexVO.getActivityTagType()).isEqualTo(1);

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

