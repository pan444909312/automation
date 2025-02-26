package com.miller.userapp.module.game.gameInfo;

import com.hungrypanda.app.server.entity.activity.game.ActivityGamePrizesEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.game.ActivityGamePrizesMapper;
import com.miller.userapp.module.game.flow.GameInfoFlow;
import com.miller.userapp.module.game.request.GameRequestDTO;
import com.miller.userapp.module.game.response.GameInfoResponseDTO;
import com.miller.userapp.module.game.utils.GameUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2025/2/20 15:11
 */
@Scenario(scenarioID = "01JMH35YHD3RYH4ARZ2SGZJ062",
        scenarioName = "抽奖游戏_游戏信息-红包过期，活动无效",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15,author = "panjuxiang@hungrypandagroup.com"
)
@EnvTag.Test
@DisplayName("抽奖游戏")
public class GameInfoRedPacketExpire {
    private final String gameSn = new PropertiesUtils().getProperty(this.getClass(), "user.app.game.sn01");
    private final String prizesId = new PropertiesUtils().getProperty(this.getClass(), "user.app.game.prizesId.redPacket.expire");

    private UserLoginRequestDTO userLoginRequestDTO;

    private ActivityGamePrizesMapper activityGamePrizesMapper;


    @BeforeAll
    void beforeAll() {
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.game.oldUser.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.game.user.common.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        activityGamePrizesMapper = sqlSession.getMapper(ActivityGamePrizesMapper.class);
        // 初始化奖品概率
        GameUtils.initGamePrizeOdds(gameSn);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("游戏信息-红包过期，活动无效")
    void shouldExistFastFoodFeature(GameRequestDTO gameRequestDTO) {
        ActivityGamePrizesEntity activityGamePrizesEntity = new ActivityGamePrizesEntity();
        activityGamePrizesEntity.setId(Long.valueOf(prizesId));
        activityGamePrizesEntity.setUserTypeOdds(100);
        activityGamePrizesMapper.updateById(activityGamePrizesEntity);

        GameInfoResponseDTO gameInfoResponseDTO = GameInfoFlow.gameInfo(gameRequestDTO);
        Integer state = gameInfoResponseDTO.getResult().getState();
        String drawInfo = gameInfoResponseDTO.getResult().getDrawInfo();

        // 需求修改 该标签删除
        assertThat(gameInfoResponseDTO.getResult().getSn()).isEqualTo(gameSn);
        assertThat(state).isEqualTo(0);
        assertThat(drawInfo).isEqualTo("来晚了，活动已结束");

    }


    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {
        GameRequestDTO gameRequestDTO = new GameRequestDTO();
        // 可以不用传参数
        gameRequestDTO.setGameSn(gameSn);

        return Stream.of(Arguments.of(gameRequestDTO));
    }
}
