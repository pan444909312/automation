package com.miller.userapp.module.shop.card.version3.searchRecommend.baseInfo.shopTag;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.searchRecommend.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.searchRecommend.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.searchRecommend.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;

import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01K9RWTX9S9QKGYT6AKTVP5937",
        scenarioName = "搜索推荐列表商卡-SKYX01_基础信息_店前标签：百万补贴_搜索列表-商卡二期：百万补贴",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasSubsidyTag {
    private final Long shopId = 911598059L;

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
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);

    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索推荐列表商卡-SKYX01_基础信息_店前标签：百万补贴_搜索列表-商卡二期：百万补贴")
    void memberBenefitDeliveryDsicount(ShopListRequestDTO shopListRequestDTO) {

        // 经纬度为沈阳
        RequestUtils.getHeaders().put("latitude","41.80478");
        RequestUtils.getHeaders().put("longitude","123.43297");

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();


        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

        assertThat(shopSearchMiddleEntity.getActivityTagType()).isEqualTo(1);

        assertThat(shopIndexVO.getActivityTag()).isNotNull();
        assertThat(shopIndexVO.getActivityTag()).isNotEqualTo("");
        assertThat(shopIndexVO.getActivityTagType()).isEqualTo(1);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setKeywords("推荐");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        ArrayList<Long> shopIdList = new ArrayList<>();
        shopIdList.add(45367036L);
        shopIdList.add(930937488L);
        shopListRequestDTO.setShopIdList(shopIdList);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

