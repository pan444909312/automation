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
import com.miller.userapp.module.data.activity.UserActivityDeleteSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JDR9SSRH9HJCMCQQ8RNXB6F9", scenarioName = "智能营销链路-天降弹窗-（基础金额开关开+手动领取模式+3张神券）"
        , developmentTime = 120, maintenanceTime = 0, manualTestTime = 15)
public class GroupPopupScenarioTests {
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

//        清除用户已领取的天降数据
        new UserActivityDeleteSql().deleteCollectRecords(userId);
    }
    @DisplayName("智能营销链路-天降弹窗-（基础金额开关开+手动领取模式+3张神券）")
    @MethodSource("popupDataProvider")
    @ParameterizedTest
    void shouldReturnGroupPopup(UserPopupRequestDTO userPopupRequestDTO){
//        获取天降结果
        UserPopupResponseDTO userPopupResponseDTO= UserPopupFlow.getGroupPopupResult(userPopupRequestDTO);
        RedPacketGroupPopupInfoVO redPacketGroupPopupInfoVO=userPopupResponseDTO.getResult().getRedPacketGroupPopupInfo();
//        校验天降弹窗结果不为空
        assertThat(redPacketGroupPopupInfoVO).isNotNull();
        ArrayList<Long> redPacketIdList=new ArrayList<>();
        redPacketIdList.add(888893276L);
        redPacketIdList.add(888893278L);
        redPacketIdList.add(888893280L);
//        获取天降弹窗神券红包结果
        RedPacketGroupPopupInfoVO.RedPacketSimpleVO redPacketIdFirstVO=redPacketGroupPopupInfoVO.getRedPacketList().stream().filter(a-> a.getRedPacketId().equals(redPacketIdList.get(0))).findFirst().orElse(null);
        assertThat(redPacketIdFirstVO).isNotNull();
        RedPacketGroupPopupInfoVO.RedPacketSimpleVO redPacketIdSecondVO=redPacketGroupPopupInfoVO.getRedPacketList().stream().filter(a-> a.getRedPacketId().equals(redPacketIdList.get(1))).findFirst().orElse(null);
        assertThat(redPacketIdSecondVO).isNotNull();
        RedPacketGroupPopupInfoVO.RedPacketSimpleVO redPacketIdThirdVO=redPacketGroupPopupInfoVO.getRedPacketList().stream().filter(a-> a.getRedPacketId().equals(redPacketIdList.get(2))).findFirst().orElse(null);
        assertThat(redPacketIdThirdVO).isNotNull();
//        校验获取到的神券基础金额是否正确
        assertThat(redPacketIdFirstVO.getRedPacketPrice()).isEqualTo(400);
        assertThat(redPacketIdSecondVO.getRedPacketPrice()).isEqualTo(400);
        assertThat(redPacketIdThirdVO.getRedPacketPrice()).isEqualTo(500);
//         校验获取到的神券膨胀后门槛是否正确
        assertThat(redPacketIdFirstVO.getThresholdPrice()).isEqualTo(2900);
        assertThat(redPacketIdSecondVO.getThresholdPrice()).isEqualTo(2600);
        assertThat(redPacketIdThirdVO.getThresholdPrice()).isEqualTo(3800);
//        校验弹窗内神券排序结果
        assertThat(redPacketGroupPopupInfoVO.getRedPacketList().get(0).getRedPacketId()).isEqualTo(888893280L);
        assertThat(redPacketGroupPopupInfoVO.getRedPacketList().get(1).getRedPacketId()).isEqualTo(888893278L);
        assertThat(redPacketGroupPopupInfoVO.getRedPacketList().get(2).getRedPacketId()).isEqualTo(888893276L);
//      开始领取
    }
    private static Stream<Arguments> popupDataProvider(){
        UserPopupRequestDTO popupRequestDTO = new UserPopupRequestDTO();
        popupRequestDTO.setShowEntry(0);
        popupRequestDTO.setShowModuleAd(0);
        return Stream.of(Arguments.of(popupRequestDTO));
    }
}
