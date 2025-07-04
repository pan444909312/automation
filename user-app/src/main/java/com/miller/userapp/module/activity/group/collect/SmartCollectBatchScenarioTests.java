package com.miller.userapp.module.activity.group.collect;

import com.hungrypanda.app.server.api.req.popup.RedPacketGroupCollectBatchAllReq;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.activity.group.collect.flow.CollectBatchFlow;
import com.miller.userapp.module.activity.group.collect.request.CollectBatchRequestDTO;
import com.miller.userapp.module.activity.group.collect.respnose.CollectBatchResponseDTO;
import com.miller.userapp.module.data.activity.UserActivityDeleteSql;
import com.miller.userapp.module.data.activity.UserCdkeyDeleteSql;
import com.miller.userapp.module.data.activity.UserCdkeyInfoSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;


@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JDR9SSRH9HJCMCQQ8RNXB6F9", scenarioName = "智能营销链路-天降弹窗-（基础金额开关开+手动领取成功+3张神券）-领取成功"
        ,author = "yancancan@hungrypandagroup.com", developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
public class SmartCollectBatchScenarioTests {
    //    智能营销3张券基础配置
    private static final List<SmartCollectBatchScenarioTests.RedPacketConfig> RED_PACKET_CONFIGS = Arrays.asList(
            new SmartCollectBatchScenarioTests.RedPacketConfig(888893278L, 100, 2700),
            new SmartCollectBatchScenarioTests.RedPacketConfig(888893280L, 100, 3200),
            new SmartCollectBatchScenarioTests.RedPacketConfig(888893276L, 300, 2200)
    );
    @BeforeAll
    public static void beforeAll(){
        //        登陆指定测试账号
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String passWord =propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.activity.user03.password");
        String userName = propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.activity.user03.account");
        String loginType = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type");
        String callingCode = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode");
        String distinctId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.distinctId");
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.activity.user03.userId");
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode(callingCode);
        user.setAccount(userName);
        user.setPassword(MD5Util.string2MD5(passWord));
        user.setType(Integer.valueOf(loginType));
        user.setDistinctId(distinctId);
        UserLoginFlow.loginAndPutToken(user);
//        删除用户领取记录以及账户里对应的神券
        for (SmartCollectBatchScenarioTests.RedPacketConfig redPacketConfig : RED_PACKET_CONFIGS) {
            new UserCdkeyDeleteSql().deleteUserCdkey(userId, redPacketConfig.getId());
        }
        new UserActivityDeleteSql().deleteCollectRecords(userId);

    }
    @DisplayName("智能营销链路-天降弹窗-（基础金额开关开+手动领取模式+3张神券）-领取成功")
    @MethodSource("provideCollectData")
    @ParameterizedTest
    void shouldCollectPopupdata(CollectBatchRequestDTO collectBatchRequestDTO){
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.activity.user01.userId");
        CollectBatchResponseDTO collectBatchResponseDTO = CollectBatchFlow.collectBatchFlow(collectBatchRequestDTO);
        // 验证用户领取状态
        verifyUserReceiveStatus();
    }

    /**
     * @return 智能营销领取红包接口数据
     */
    static Stream<Arguments> provideCollectData(){
        CollectBatchRequestDTO collectBatchRequest = new CollectBatchRequestDTO();
        collectBatchRequest.setCity("九江市");
        List<RedPacketGroupCollectBatchAllReq.CollectDetail> list =new ArrayList<RedPacketGroupCollectBatchAllReq.CollectDetail>();
        for (SmartCollectBatchScenarioTests.RedPacketConfig redPacketConfig : RED_PACKET_CONFIGS) {
            RedPacketGroupCollectBatchAllReq.CollectDetail collectDetail = new RedPacketGroupCollectBatchAllReq.CollectDetail();
            collectDetail.setRedPacketGroupId(0L);
            collectDetail.setRedPacketActivityType(1);
            collectDetail.setRedPacketPlatform("HP");
            collectDetail.setRedPacketId(redPacketConfig.getId());
            list.add(collectDetail);
        }
        collectBatchRequest.setCollects(list);
        return Stream.of(Arguments.of(collectBatchRequest));
    }
    private void verifyUserReceiveStatus() {
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.activity.user03.userId");
        UserCdkeyInfoSql cdkeyInfoSql = new UserCdkeyInfoSql();
        for (SmartCollectBatchScenarioTests.RedPacketConfig config : RED_PACKET_CONFIGS) {
            UserCdKeyEntity cdKey = cdkeyInfoSql.selectUserCdkeyInfo(userId, config.getId());
            Assertions.assertThat(cdKey.getRedPacketScopeType())
                    .as("红包%d领取状态校验", config.getId())
                    .isEqualTo(1);
        }
    }

    @lombok.Value
    static class RedPacketConfig {
        Long id;
        int baseAmount;
        int thresholdAmount;
    }
}


