package com.miller.userapp.module.shop.card.version3.search.baseInfo.shopLogo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.entity.shop.BdmExclusiveShopEntity;
import com.hungrypanda.app.server.vo.index.BaseShopIndexVO;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.query.flow.QueryShopInfoFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.BdmExclusiveShopMapper;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.panda.merchant.server.api.dto.merchant.module.ImageModuleDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@Scenario(scenarioID = "01K9RWTX9S9QKGYT6AKTVP591Y",
        scenarioName = "搜索列表商卡-SKYX01_基础信息_店铺logo_搜索列表-商卡二期:独家店铺未勾选开启独家logo，该店铺设置了主图动图，不会展示动图",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoGifLogoCauseConfigIsClose {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.04.shopId"));
    private static ShopMapper shopMapper;
    private static ShopSearchMiddleMapper shopSearchMiddleMapper;

    private static BdmExclusiveShopMapper bdmExclusiveShopMapper;


    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        ERPLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
        bdmExclusiveShopMapper = sqlSession.getMapper(BdmExclusiveShopMapper.class);

    }

    @MethodSource("staticLogoDataProvider")
    @ParameterizedTest
    @DisplayName("搜索列表商卡-SKYX01_基础信息_店铺logo_搜索列表-商卡二期:独家店铺未勾选开启独家logo，该店铺设置了主图动图，不会展示动图")
    void shouldExistStaticLogo(ShopListRequestDTO shopListRequestDTO) {
        // Given

        // When
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        String shopLogoOfInterfaceResponse = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst()
                // 获取接口的字段值
                .map(BaseShopIndexVO::getShopLogo).orElseThrow();
        // 查询 ERP 后台的配置信息
        String shopLogoOfERPInterfaceResponse = QueryShopInfoFlow.queryShopInfoByShopId(shopId).getData().getOperationInfo().getImages()
                .stream().filter(item -> item.getFileSource().name().equalsIgnoreCase("SHOP_LOGO"))
                .findFirst()
                // 获取接口的字段值
                .map(ImageModuleDTO::getUrl).orElseThrow();

        // Then. 校验接口返回的字段与ERP后台配置接口返回的字段值相同: JSON.result.shopList[x].shopLogo
        var shopLogoOfInterfaceResponseFilaName = shopLogoOfInterfaceResponse.substring(shopLogoOfInterfaceResponse.lastIndexOf("/") + 1);
        var shopLogoOfERPInterfaceResponseFilaName = shopLogoOfERPInterfaceResponse.substring(shopLogoOfERPInterfaceResponse.lastIndexOf("/") + 1);
        assertThat(shopLogoOfInterfaceResponseFilaName).isNotNull().isEqualTo(shopLogoOfERPInterfaceResponseFilaName);

        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(
                // 查询条件，店铺ID
                new LambdaQueryWrapper<ShopSearchMiddleEntity>().eq(ShopSearchMiddleEntity::getShopId, shopId));

        QueryWrapper<BdmExclusiveShopEntity> queryWrapper = new QueryWrapper<BdmExclusiveShopEntity>()
                .select("status", "is_show_shop_logo_gif", "is_show_index_sort_value")
                .eq("shop_id", shopId);
        BdmExclusiveShopEntity bdmExclusiveShop = bdmExclusiveShopMapper.selectOne(queryWrapper);

        assertThat(bdmExclusiveShop.getStatus()).isEqualTo(1);
        assertThat(bdmExclusiveShop.getIsShowShopLogoGif()).isEqualTo(0);

        assertThat(shopSearchMiddleEntity.getShopLogoGif()).isEqualTo("");

        assertThat(shopLogoOfERPInterfaceResponse).isEqualTo(shopSearchMiddleEntity.getShopLogo());
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticLogoDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码 Bug，没有对 null 进行判断，应该默认给 false

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
