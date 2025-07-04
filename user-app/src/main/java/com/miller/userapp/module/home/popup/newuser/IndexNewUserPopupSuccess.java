package com.miller.userapp.module.home.popup.newuser;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.hungrypanda.app.server.entity.redpacket.UserNewRedPacketRecordEntity;
import com.hungrypanda.app.server.entity.user.UserBenefitRedPacketRecord;
import com.hungrypanda.app.server.vo.index.PopupVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
import com.miller.userapp.mapper.user.UserBenefitRedPacketRecordMapper;
import com.miller.userapp.mapper.user.UserNewRedPacketRecordMapper;
import com.miller.userapp.module.activity.newuserpopup.request.NewUserPopupRequestDTO;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.util.RequestUtils;
import com.miller.userapp.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD2",
        scenarioName = "首页新人弹窗下发成功(已登录新人)",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 90, maintenanceTime = 0, manualTestTime = 30)
@EnvTag.Test
@DisplayName("/api/user/popup")
@Slf4j
public class IndexNewUserPopupSuccess {

    /**
     * 接口_首页弹窗接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/popup";

    private UserLoginRequestDTO userLoginRequestDTO;

    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
    private UserBenefitRedPacketRecordMapper userBenefitRedPacketRecordMapper = sqlSession.getMapper(UserBenefitRedPacketRecordMapper.class);

    private UserNewRedPacketRecordMapper userNewRedPacketRecordMapper = sqlSession.getMapper(UserNewRedPacketRecordMapper.class);

    private UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);
    private boolean userStatus = false;

    private String deviceId = "201429405301e060";
    private String userId = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user04.account.id");

    @BeforeAll
    void beforeAll() {
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user04.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user04.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        RequestUtils.getHeaders().remove("cityName");
        RequestUtils.getHeaders().put("uniquetoken", deviceId);

        //重置用户新人状态
        resetNewUserStatus();

    }

    @AfterAll
    void afterAll() {
        // 清除用户红包
        userCdKeyMapper.delete(new QueryWrapper<UserCdKeyEntity>().eq("user_id",userId));
    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("首页新人弹窗下发成功(已登录新人)")
    void testCase(NewUserPopupRequestDTO newUserPopupRequestDTO) {

        Result result = HttpUtils.sendGetRequestReturnJavaObject(uri,
                RequestUtils.putBodyOfForm(newUserPopupRequestDTO),
                RequestUtils.getHeaders(),
                null,
                Result.class);


        PopupVO popupVO = JSON.parseObject(result.getResult().toString(), PopupVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(popupVO.getType()).isEqualTo(4);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {

        NewUserPopupRequestDTO newUserPopupRequestDTO = new NewUserPopupRequestDTO();
        newUserPopupRequestDTO.setShowModuleAd(0);
        newUserPopupRequestDTO.setShowEntry(0);
        newUserPopupRequestDTO.setType(0);
        return Stream.of(Arguments.of(newUserPopupRequestDTO));
    }

    private void resetNewUserStatus() {
        //获取用户新人状态
        userStatus = UserUtils.isNewUser(userId);
        if (!userStatus) {
            UserUtils.updateUserNewUserStatus(userId, 0);
        }

        if (userNewRedPacketRecordMapper.selectList(new QueryWrapper<UserNewRedPacketRecordEntity>().eq("device_id", deviceId)) != null) {
            userNewRedPacketRecordMapper.delete(new QueryWrapper<UserNewRedPacketRecordEntity>().eq("device_id", deviceId));
        }

        if (userBenefitRedPacketRecordMapper.selectList(new QueryWrapper<UserBenefitRedPacketRecord>().eq("user_id", userId)) != null) {
            userBenefitRedPacketRecordMapper.delete(new QueryWrapper<UserBenefitRedPacketRecord>().eq("user_id", userId));
        }

    }


}
