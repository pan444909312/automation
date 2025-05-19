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
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
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
@Scenario(scenarioID = "01JDR9SSRH9HJCMCQQ8RNXB6F9", scenarioName = "智能营销链路-天降弹窗-（基础金额开关开+手动领取模式+3张神券）-弹窗"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
public class GroupPopupSmartCollectPopScenarioTests {

//    智能营销3张券基础配置
    private static final List<RedPacketConfig> RED_PACKET_CONFIGS = Arrays.asList(
            new RedPacketConfig(888893278L, 100, 2700),
            new RedPacketConfig(888893280L, 100, 3200),
            new RedPacketConfig(888893276L, 300, 2200)
    );
    @BeforeAll
    public static void beforeAll() {
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
//        清除用户已领取的天降数据
        new UserActivityDeleteSql().deleteCollectRecords(userId);
//        修改九江市领取模式为直塞
        new AcitivityCityUpdateSql().setCityRuleAuto("九江市",1);
    }
    @DisplayName("智能营销链路-天降弹窗-（基础金额开关开+直塞模式+3张神券）-弹窗")
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
        assertThat(redPackets).hasSize(3)
                .as("应该有3张神券");

        // 验证每个红包的配置
        for (RedPacketConfig config : RED_PACKET_CONFIGS) {
            RedPacketGroupPopupInfoVO.RedPacketSimpleVO redPacket = findRedPacketById(redPackets, config.getId());
            verifyRedPacketConfig(redPacket, config);
        }
//        // 验证排序
//        verifyRedPacketOrder(redPackets);
    }

    /**
     * @param redPackets 验证弹窗内红包排序
     */
    private void verifyRedPacketOrder(List<RedPacketGroupPopupInfoVO.RedPacketSimpleVO> redPackets) {
        assertThat(redPackets.get(0).getRedPacketId()).isEqualTo(888893278L);
        assertThat(redPackets.get(1).getRedPacketId()).isEqualTo(888893280L);
        assertThat(redPackets.get(2).getRedPacketId()).isEqualTo(888893276L);
    }

    private RedPacketGroupPopupInfoVO.RedPacketSimpleVO findRedPacketById(
            List<RedPacketGroupPopupInfoVO.RedPacketSimpleVO> redPackets,
            Long id
    ) {
        return redPackets.stream()
                .filter(packet -> packet.getRedPacketId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssertionError("未找到ID为" + id + "的红包"));
    }
    private void verifyRedPacketConfig(
            RedPacketGroupPopupInfoVO.RedPacketSimpleVO redPacket,
            GroupPopupSmartCollectPopScenarioTests.RedPacketConfig config
    ) {
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
