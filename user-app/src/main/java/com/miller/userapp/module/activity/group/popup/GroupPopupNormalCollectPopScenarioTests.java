package com.miller.userapp.module.activity.group.popup;

import com.hungrypanda.app.server.vo.index.RedPacketGroupPopupInfoVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.activity.group.popup.flow.UserPopupFlow;
import com.miller.userapp.module.activity.group.popup.request.UserPopupRequestDTO;
import com.miller.userapp.module.activity.group.popup.responose.UserPopupResponseDTO;
import com.miller.userapp.module.data.activity.AcitivityCityUpdateSql;
import com.miller.userapp.module.data.activity.UserActivityDeleteSql;
import com.miller.userapp.module.data.activity.UserCdkeyDeleteSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JV6RCKS63YM5GE0Y9WC3CFP1", scenarioName = "普通天降链路-天降弹窗-智能营销对照组-手动领取-普通活动-弹窗"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
public class GroupPopupNormalCollectPopScenarioTests {

//    普通活动红包配置
    private static final List<RedPacketConfig> RED_PACKET_CONFIGS = Arrays.asList(
            new RedPacketConfig(888895242L, 600, 1000)
    );
    @BeforeAll
    public static void beforeAll() {
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
//        清除用户已领取的天降数据&红包信息
        new UserActivityDeleteSql().deleteCollectRecords(userId);
        for (GroupPopupNormalCollectPopScenarioTests.RedPacketConfig redPacketConfig : RED_PACKET_CONFIGS) {
            new UserCdkeyDeleteSql().deleteUserCdkey(userId, redPacketConfig.getId());
        }
//        修改九江市领取模式为手动领取
        new AcitivityCityUpdateSql().setCityRuleAuto("九江市",1);
//        修改heades实验：去掉ZNYX
         String testGroup = "I_R_TEST_GROUP,I_R_TEST_GROUP,SUPERMARKET_SCENES_TEST_GROUP," +
                "S_H_R_L_TEST_GROUP_7,22,23,31,32,NUMBER_MASKING_00,33,34,35,40,39,45,52,54," +
                "HPF,TESTE02,FASTD01,YSDCS02,YYYZM01,IST01,HYBQ01,SKEQ01,XRJ01,TJBQ01,JSYZQ02,HYXBQ01," +
                "TJTCX01,YBXS02,CCPRO01,ZDFQ01,SKXRB01,ABT02,XRTC01,QYTCD01,SMSS02,XMLM01,RRREC02," +
                "CCT01,ZFBMM01,SSJLY01,DFF01,SPSS01,MRBX01";
        RequestUtils.getHeaders().put("testGroup",testGroup);


    }
    @DisplayName("普通天降链路-天降弹窗-智能营销对照组-手动领取-普通活动-弹窗")
    @MethodSource("popupDataProvider")
    @ParameterizedTest
    void shouldReturnGroupPopup(UserPopupRequestDTO userPopupRequestDTO){
//        获取天降结果
        UserPopupResponseDTO userPopupResponseDTO= UserPopupFlow.getGroupPopupResult(userPopupRequestDTO);
        RedPacketGroupPopupInfoVO redPacketGroupPopupInfoVO=userPopupResponseDTO.getResult().getRedPacketGroupPopupInfo();
        // 验证红包信息
        verifyRedPackets(redPacketGroupPopupInfoVO);
    }
    private void verifyRedPackets(RedPacketGroupPopupInfoVO popupInfo) {
        List<RedPacketGroupPopupInfoVO.RedPacketSimpleVO> redPackets = popupInfo.getRedPacketList();

        // 验证红包数量
        assertThat(redPackets).hasSize(1)
                .as("应该有1张普通红包");

        // 验证每个红包的配置
        for (RedPacketConfig config : RED_PACKET_CONFIGS) {
            RedPacketGroupPopupInfoVO.RedPacketSimpleVO redPacket = findRedPacketById(redPackets, config.getId());
            verifyRedPacketConfig(redPacket, config);
        }
    }
    private RedPacketGroupPopupInfoVO.RedPacketSimpleVO findRedPacketById(List<RedPacketGroupPopupInfoVO.RedPacketSimpleVO> redPackets, Long id) {
        return redPackets.stream()
                .filter(packet -> packet.getRedPacketId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssertionError("未找到ID为" + id + "的红包"));
    }
    private void verifyRedPacketConfig(RedPacketGroupPopupInfoVO.RedPacketSimpleVO redPacket, RedPacketConfig config) {
        assertThat(redPacket.getRedPacketPrice())
                .as("红包%d基础金额校验", config.getId())
                .isEqualTo(config.getBaseAmount());

        assertThat(redPacket.getThresholdPrice())
                .as("红包%d门槛金额校验", config.getId())
                .isEqualTo(config.getThresholdAmount());
    }
    private static Stream<Arguments> popupDataProvider(){
        UserPopupRequestDTO popupRequestDTO = new UserPopupRequestDTO();
        popupRequestDTO.setShowEntry(0);
        popupRequestDTO.setShowModuleAd(0);
        return Stream.of(Arguments.of(popupRequestDTO));
    }
    @lombok.Value
    public static class RedPacketConfig {
        Long id;
        int baseAmount;
        int thresholdAmount;
    }
}
