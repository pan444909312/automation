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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JDR9SSRH9HJCMCQQ8RNXB6F9", scenarioName = "智能营销链路-天降弹窗-（基础金额开关开+手动领取成功+3张神券）-领取成功"
        , developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
public class CollectBatchScenarioTests {
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
        Long redPacketId_1=888893280L;
        Long redPacketId_2=888893278L;
        Long redPacketId_3=888893276L;
        new UserActivityDeleteSql().deleteCollectRecords(userId);
        new UserCdkeyDeleteSql().deleteUserCdkey(userId,redPacketId_1);
        new UserCdkeyDeleteSql().deleteUserCdkey(userId,redPacketId_2);
        new UserCdkeyDeleteSql().deleteUserCdkey(userId,redPacketId_3);

    }
    @DisplayName("智能营销链路-天降弹窗-（基础金额开关开+手动领取模式+3张神券）-领取成功")
    @MethodSource("provideCollectData")
    @ParameterizedTest
    void shouldCollectPopupdata(CollectBatchRequestDTO collectBatchRequestDTO){
        PropertiesUtils propertiesUtils=new PropertiesUtils();
        String userId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.activity.user01.userId");
        CollectBatchResponseDTO collectBatchResponseDTO = CollectBatchFlow.collectBatchFlow(collectBatchRequestDTO);
//        校验用户成功领取红包1,且red_packet_scopeType=1
        UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
        UserCdKeyEntity  UserCdKeyEntity= userCdkeyInfoSql.selectUserCdkeyInfo(userId,888893276L);
        assertThat(UserCdKeyEntity.getRedPacketScopeType()).isEqualTo(1);
    }
    static Stream<Arguments> provideCollectData(){
        CollectBatchRequestDTO collectBatchRequest = new CollectBatchRequestDTO();
        collectBatchRequest.setCity("九江市");
        RedPacketGroupCollectBatchAllReq.CollectDetail collectDetail_1 = new RedPacketGroupCollectBatchAllReq.CollectDetail();
        collectDetail_1.setRedPacketGroupId(0L);
        collectDetail_1.setRedPacketActivityType(1);
        collectDetail_1.setRedPacketPlatform("HP");
        collectDetail_1.setRedPacketId(888893280L);
        RedPacketGroupCollectBatchAllReq.CollectDetail collectDetail_2=new RedPacketGroupCollectBatchAllReq.CollectDetail();
        collectDetail_2.setRedPacketGroupId(0L);
        collectDetail_2.setRedPacketActivityType(1);
        collectDetail_2.setRedPacketPlatform("HP");
        collectDetail_2.setRedPacketId(888893278L);
        RedPacketGroupCollectBatchAllReq.CollectDetail collectDetail_3=new RedPacketGroupCollectBatchAllReq.CollectDetail();
        collectDetail_3.setRedPacketGroupId(0L);
        collectDetail_3.setRedPacketActivityType(1);
        collectDetail_3.setRedPacketPlatform("HP");
        collectDetail_3.setRedPacketId(888893276L);
        List<RedPacketGroupCollectBatchAllReq.CollectDetail> list =new ArrayList<RedPacketGroupCollectBatchAllReq.CollectDetail>();
        list.add(collectDetail_1);
        list.add(collectDetail_2);
        list.add(collectDetail_3);
        collectBatchRequest.setCollects(list);
        return Stream.of(Arguments.of(collectBatchRequest));
    }}


