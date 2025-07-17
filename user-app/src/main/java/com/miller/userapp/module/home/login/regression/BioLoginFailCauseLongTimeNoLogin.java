package com.miller.userapp.module.home.login.regression;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.hungrypanda.common.deeplink.utils.MD5Utils;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.user.UserLogMapper;
import com.miller.userapp.util.RequestUtils;
import com.miller.service.util.SignGenerateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DDC",
        scenarioName = "生物认证失败-长时间未登录",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("/api/app/user/bio/login")
@Slf4j
public class BioLoginFailCauseLongTimeNoLogin {

    /**
     * 接口_生物认证登陆
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/bio/login";

    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();


    private UserLogMapper userLogMapper = sqlSession.getMapper(UserLogMapper.class);

    private String deviceId = "auto-test-device-id";
    private String tel = "13999900005";
    private String userId = "1398717264";

    private Long timeStamp = System.currentTimeMillis();

    // xxl :user-app-server.bio.auth. = hpcd6ed83c
    private String bioAuth = "hpcd6ed83c";

    private String signAuthKey = "hP*L8pp65_#1flvjk342589fdgjl34m";

    private UserLogEntity userLog;

    private Long createTime = 0L;

    private String macId = MD5Utils.string2MD5(bioAuth + deviceId + userId);



    @BeforeAll
    void beforeAll() {
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        RequestUtils.getHeaders().remove("cityName");
        RequestUtils.getHeaders().put("uniquetoken", deviceId);
        RequestUtils.getHeaders().put("_ts", timeStamp);

        // 构造查询条件
        QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id",deviceId);
        queryWrapper.eq("macId",macId);
        queryWrapper.orderByDesc("create_time");
//        queryWrapper.last("limit 1");
//        userLog = userLogMapper.selectOne(queryWrapper);
        List<UserLogEntity> userLogEntities = userLogMapper.selectList(queryWrapper);
        userLog = userLogEntities.get(0);
        createTime = userLog.getCreateTime();
//        userLog.setCreateTime(System.currentTimeMillis() - 3600L * 24 * 1000 * 100 * 2);
        userLogMapper.update(new UpdateWrapper<UserLogEntity>()
                .set("create_time", System.currentTimeMillis() - 3600L * 24 * 1000 * 100 * 2)
                .eq("device_id",deviceId)
                .eq("macId",macId));
//        userLogMapper.updateById(userLog);
    }

    @AfterAll
    void afterAll() {
        userLog.setCreateTime(createTime);
        userLogMapper.updateById(userLog);
    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("生物认证失败-长时间未登录")
    void testCase() {

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


        assertThat(result.getResultCode()).isEqualTo(2073);
        assertThat(result.getResult()).isNull();
        assertThat(result.getError()).isEqualTo("由于您长时间未登录，为了保护您的账户安全，请您重新登录");
    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        return Stream.of(Arguments.of());
    }


}
