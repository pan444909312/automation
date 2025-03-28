package com.miller.userapp.module.shop.card.version2.home.baseinfo.status;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.common.enums.ShopStatusEnum;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.BaseShopIndexVO;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.manage.merchant.business.config.time.status.flow.BusinessInfoUpdateStatusFlow;
import com.miller.erp.moudle.manage.merchant.business.config.time.status.request.BusinessInfoUpdateStopOrderRequestDTO;
import com.miller.erp.moudle.manage.merchant.business.config.time.status.response.BusinessInfoUpdateStopOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.home.baseinfo.ShopShouldHasLabelScenarioTests;
import com.miller.userapp.module.shop.card.version2.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JKSWF87HHXZMJ276T2V6BQXT", scenarioName = "商卡(中文)_普通店铺配送商卡_基础信息_店铺营业状态_首页-商卡二期:店铺营业状态-暂停接单"
        ,author = "yancancan@hungrypandagroup.com" ,developmentTime = 10, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopStatusShouldStopOrderScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId.stop.order"));
    private static ShopSearchMiddleMapper shopSearchMiddleMapper;
    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
//        设置店铺为暂停接单状态
        ERPLoginFlow.loginByDefaultUser();
        BusinessInfoUpdateStopOrderRequestDTO businessInfoUpdateStopOrderRequestDTO = new BusinessInfoUpdateStopOrderRequestDTO();
        businessInfoUpdateStopOrderRequestDTO.setShopId(shopId);
        businessInfoUpdateStopOrderRequestDTO.setStopOrderMinutes("3600");
        businessInfoUpdateStopOrderRequestDTO.setStopOrderToClose(false);
        BusinessInfoUpdateStopOrderResponseDTO businessInfoUpdateStopOrderResponseDTO = BusinessInfoUpdateStatusFlow.businessInfoUpdateStopOrder(businessInfoUpdateStopOrderRequestDTO);
//        执行搜索索引定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));

    }
    @MethodSource("shopStatusDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_基础信息_店铺营业状态_首页-商卡二期:店铺营业状态-暂停接单")
    void showLabel(ShopListRequestDTO shopListRequestDTO) {
        // Given

        // When
        var shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        assert shopList != null;
        var interfaceResponse = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst()
                // 获取接口返回的字段
                .map(BaseShopIndexVO::getShopStatus).orElseThrow();

        // Then. 校验接口返回的字段与数据库字段匹配,
        var databaseResponse = shopSearchMiddleMapper.selectOne(
                        // 查询条件，店铺ID
                        new LambdaQueryWrapper<ShopSearchMiddleEntity>().eq(ShopSearchMiddleEntity::getShopId, shopId))
                // 获取数据库字段值
                .getShopStatus();

        assertThat(interfaceResponse).isEqualTo(ShopStatusEnum.OPEN.getCode());
        assertThat(databaseResponse).isFalse();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> shopStatusDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
