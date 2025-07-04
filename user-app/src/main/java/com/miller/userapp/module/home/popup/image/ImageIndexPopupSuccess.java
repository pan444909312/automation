package com.miller.userapp.module.home.popup.image;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.vo.index.PopupVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD1",
        scenarioName = "首页图片弹窗下发成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 90, maintenanceTime = 0, manualTestTime = 30)
@EnvTag.Test
@DisplayName("/api/user/popup")
@Slf4j
public class ImageIndexPopupSuccess {

    /**
     * 接口_首页弹窗接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/popup";

    private UserLoginRequestDTO userLoginRequestDTO;

    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();

    private boolean userStatus = false;

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


        //排除新人弹窗干扰
        excludeNewUserPopUp();

    }

    @AfterAll
    void afterAll() {
        //恢复数据
        UserUtils.updateUserNewUserStatus(userId, userStatus ? 0 : 1);
    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("首页图片弹窗下发成功")
    void testCase(NewUserPopupRequestDTO newUserPopupRequestDTO) {

        Result result = HttpUtils.sendGetRequestReturnJavaObject(uri,
                RequestUtils.putBodyOfForm(newUserPopupRequestDTO),
                RequestUtils.getHeaders(),
                null,
                Result.class);


        PopupVO popupVO = JSON.parseObject(result.getResult().toString(), PopupVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(popupVO.getType()).isEqualTo(2);
        assertThat(popupVO.getId()).isEqualTo(729);
        assertThat(popupVO.getUrl()).isEqualTo("https://new-platform-test.hungrypanda.cn/");

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

    private void excludeNewUserPopUp() {
        //获取用户新人状态
        userStatus = UserUtils.isNewUser(userId);
        if (userStatus) {
            UserUtils.updateUserNewUserStatus(userId, 1);
        }
    }


}
