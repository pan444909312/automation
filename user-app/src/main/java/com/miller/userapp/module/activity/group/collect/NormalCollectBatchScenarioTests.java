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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JV6RCKS63YM5GE0Y9WC3CFP5", scenarioName = "普通天降链路-天降弹窗-智能营销对照组-手动领取-普通活动-手动领取"
        ,author = "yancancan@hungrypandagroup.com", developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
public class NormalCollectBatchScenarioTests {
    //    普通活动红包配置
    private static final List<NormalCollectBatchScenarioTests.RedPacketConfig> RED_PACKET_CONFIGS = Arrays.asList(
            new NormalCollectBatchScenarioTests.RedPacketConfig(888895242L, 600, 1000)
    );
    @BeforeAll
    public static void beforeAll(){
        //        登陆指定测试账号
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String passWord =propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.activity.user01.password");
        String userName = propertiesUtils.getProperty(UserLoginFlow.class,"user.app.account.activity.user01.account");
        String loginType = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type");
        String callingCode = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode");
        String distinctId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.distinctId");
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.activity.user01.userId");
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode(callingCode);
        user.setAccount(userName);
        user.setPassword(MD5Util.string2MD5(passWord));
        user.setType(Integer.valueOf(loginType));
        user.setDistinctId(distinctId);
        UserLoginFlow.loginAndPutToken(user);
        //        删除用户领取记录以及账户里对应的神券
        new UserActivityDeleteSql().deleteCollectRecords(userId);
        for (NormalCollectBatchScenarioTests.RedPacketConfig redPacketConfig : RED_PACKET_CONFIGS) {
            new UserCdkeyDeleteSql().deleteUserCdkey(userId, redPacketConfig.getId());
        }
    }
    @DisplayName("普通天降链路-天降弹窗-智能营销对照组-手动领取-普通活动-手动领取")
    @MethodSource("provideCollectData")
    @ParameterizedTest
    void shouldCollectPopupdata(CollectBatchRequestDTO collectBatchRequestDTO){
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        CollectBatchResponseDTO collectBatchResponseDTO = CollectBatchFlow.collectBatchFlow(collectBatchRequestDTO);
//          // 验证用户领取状态
        verifyUserReceiveStatus();
    }
    private void verifyUserReceiveStatus() {
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.activity.user01.userId");
        UserCdkeyInfoSql cdkeyInfoSql = new UserCdkeyInfoSql();
        for (NormalCollectBatchScenarioTests.RedPacketConfig config : RED_PACKET_CONFIGS) {
            UserCdKeyEntity cdKey = cdkeyInfoSql.selectUserCdkeyInfo(userId, config.getId());
            Assertions.assertThat(cdKey.getRedPacketScopeType())
                    .as("红包%d领取状态校验", config.getId())
                    .isEqualTo(0);
        }
    }

    static Stream<Arguments> provideCollectData(){
        CollectBatchRequestDTO collectBatchRequest = new CollectBatchRequestDTO();
        collectBatchRequest.setCity("九江市");
        RedPacketGroupCollectBatchAllReq.CollectDetail collectDetail_1 = new RedPacketGroupCollectBatchAllReq.CollectDetail();
        collectDetail_1.setRedPacketGroupId(2177L);
        collectDetail_1.setRedPacketActivityType(1);
        collectDetail_1.setRedPacketPlatform("HP");
        collectDetail_1.setRedPacketId(RED_PACKET_CONFIGS.get(0).getId());
        List<RedPacketGroupCollectBatchAllReq.CollectDetail> list =new ArrayList<RedPacketGroupCollectBatchAllReq.CollectDetail>();
        list.add(collectDetail_1);
        collectBatchRequest.setCollects(list);
        return Stream.of(Arguments.of(collectBatchRequest));
    }
    @lombok.Value
    static class RedPacketConfig {
        Long id;
        int baseAmount;
        int thresholdAmount;
    }
}



