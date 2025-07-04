package com.miller.userapp.module.home.login.regression;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.vo.user.UserTokenSimpleVO;
import com.hungrypanda.common.deeplink.utils.MD5Utils;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.util.RequestUtils;
import com.miller.userapp.util.SignGenerateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD9",
        scenarioName = "生物认证登录成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 180, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/app/user/bio/login")
@Slf4j
public class BioLoginSuccess {

    /**
     * 接口_生物认证登陆
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/bio/login";

    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();


//    private UserLogMapper userLogMapper = sqlSession.getMapper(UserLogMapper.class);

    private String deviceId = new PropertiesUtils().getProperty(UserLoginTests.class, "user.app.login.device.id");
    private String tel = "13999900005";
    private String userId = "1398717264";

    private Long timeStamp = System.currentTimeMillis();

    // xxl :user-app-server.bio.auth. = hpcd6ed83c
    private String bioAuth = "hpcd6ed83c";

    private String signAuthKey = "hP*L8pp65_#1flvjk342589fdgjl34m";


    @BeforeAll
    void beforeAll() {
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        RequestUtils.getHeaders().remove("cityName");
        RequestUtils.getHeaders().put("uniquetoken", deviceId);
        RequestUtils.getHeaders().put("_ts", timeStamp);


    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("生物认证登录成功")
    void testCase() {

        // 构造查询条件
//        QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        queryWrapper.orderByDesc("create_time");
//        queryWrapper.last("limit 1");
        // 登录过用户 获取该用户的macId， 但是被删除会有问题
//        UserLogEntity userLog = userLogMapper.selectOne(queryWrapper);
//        String macId = userLog.getMacId();
//        userBioLoginReq.setBioAuthToken(macId);


        // bioAuthToken 加密规则
        String macId = MD5Utils.string2MD5(bioAuth + deviceId + userId);



        //  处理验签  还有问题
//        JSONObject requestJsonObject = JSONObject.parseObject(JSON.toJSONString(userBioLoginReq));

        String req = "{\"areaCode\":\"86\",\"account\":\"" + tel + "\",\"cityName\":\"杭州市\",\"bioAuthToken\":\"" + macId + "\"}";

        JSONObject requestJsonObject = JSONObject.parseObject(req);
        requestJsonObject.put("authorization", "");
        requestJsonObject.put("_ts", timeStamp);

        String sign = SignGenerateUtil.getSign(requestJsonObject, signAuthKey);

        RequestUtils.getHeaders().put("_sign", sign);


        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri,
                null,
                RequestUtils.getHeaders(),
                req, null,
                Result.class);

        UserTokenSimpleVO userTokenSimpleVO = JSON.parseObject(result.getResult().toString(), UserTokenSimpleVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(userTokenSimpleVO.getAccessToken()).isNotNull();
        assertThat(userTokenSimpleVO.getRefresh_token()).isNotNull();
        assertThat(userTokenSimpleVO.getUserName()).isEqualTo(tel);
        assertThat(userTokenSimpleVO.getBioAuthToken()).isEqualTo(macId);

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        return Stream.of(Arguments.of());
    }


}
