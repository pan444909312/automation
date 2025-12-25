package com.miller.userapp.moduleEnAu.shop.card.version3.home.promotion.fullSub;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.entity.shop.FullSubEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.FullSubMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/7/30 17:47
 */
@Scenario(scenarioID = "01K0R3KGKM3055H0KV6N31F9J8", scenarioName = "商卡(中文)_普通店铺配送商卡-SKYX01_优惠标签_满减_首页-商卡二期：满减29",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@TestFramework
@DisplayName("普通店铺配送商卡-SKYX01_优惠标签_满减_首页-商卡二期：满减29")
public class ShopShouldHasFullSubScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private FullSubMapper fullSubMapper;

    //单品折扣使用渠道为外卖的活动sn
//    private String discountSn = "5452LGZTXY";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
        fullSubMapper = sqlSession.getMapper(FullSubMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_优惠标签_满减_首页-商卡二期：满减29")
    void shouldExistFullSub(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        List<ShopPromoteVO> shopPromoteVOS = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_FULL_SUB.getType()).toList();

        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));
        // 满减活动信息
        String fullSubInfo = shopSearchMiddleEntity.getFullSubInfo();
        // 满减活动标记
        Boolean fullSubTag = shopSearchMiddleEntity.getFullSubTag();

        ObjectMapper mapper = new ObjectMapper();

        List<FullSubEntity> fullSubEntityList = fullSubMapper.selectList(new QueryWrapper<FullSubEntity>().eq("shop_id", shopId));

        try {
            List<Map<String, Object>> list = mapper.readValue(fullSubInfo, List.class);
            // 如果有问题，则再手动转换每个Object为Map
//            List<Map<String, Object>> fullSubInfoList = list.stream()
//                    .map(obj -> (Map<String, Object>) obj)
//                    .collect(Collectors.toList());

            assert fullSubTag;
            for (int i = 0; i < shopPromoteVOS.size(); i++) {
//                assert shopPromoteVOS.get(i).getType() == ShopPromoteEnum.INDEX_FULL_SUB.getType();
                assert shopPromoteVOS.get(i).getShowContent().equals(list.get(i).get("fullSubName"));
                assert list.get(i).get("fullSubId").toString().equals(fullSubEntityList.get(i).getFullSubId().toString());
                assert list.get(i).get("full").toString().equals(fullSubEntityList.get(i).getFull().toString());
                assert list.get(i).get("sub").toString().equals(fullSubEntityList.get(i).getSub().toString());
                //额外校验标签类型
                assert shopPromoteVOS.get(i).getTagType().equals(2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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
