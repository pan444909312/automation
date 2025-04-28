package com.miller.userapp.module.shop.card.version2.home.baseinfo.shopLogo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.BaseShopIndexVO;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.query.flow.QueryShopInfoFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.response.ShopListResponseDTO;
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

/**
 * 商卡(中文)_普通店铺配送商卡_基础信息_店铺logo_静图_首页-商卡二期:店铺logo-静图
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/06/24 17:17:39
 */
@Scenario(scenarioID = "01J3VJ3JMWY8V3ME1TKXE6V7E7",
        scenarioName = "商卡(中文)_普通店铺配送商卡_基础信息_店铺logo_静图_首页-商卡二期:店铺logo-静图",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasStaticLogoScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private static ShopMapper shopMapper;
    private static ShopSearchMiddleMapper shopSearchMiddleMapper;

    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        ERPLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        shopMapper = sqlSession.getMapper(ShopMapper.class);
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("staticLogoDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_基础信息_店铺logo_静图_首页-商卡二期:店铺logo-静图")
    void shouldExistStaticLogo(ShopListRequestDTO shopListRequestDTO) {
        // Given

        // When
        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
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

        // 校验数据库字段: hp_shop_search_middle.shop_logo = shop.shop_logo
        var databaseResponseOfShopLogo = shopMapper.selectById(shopId).getShopLogo();
        var databaseResponseOfSearchMiddleShopLogo = shopSearchMiddleMapper.selectOne(
                // 查询条件，店铺ID
                new LambdaQueryWrapper<ShopSearchMiddleEntity>().eq(ShopSearchMiddleEntity::getShopId, shopId))
                // 获取数据库的字段值
                .getShopLogo();
        assertThat(databaseResponseOfShopLogo).isEqualTo(databaseResponseOfSearchMiddleShopLogo);
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
