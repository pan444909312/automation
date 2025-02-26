package com.miller.userapp.module.game.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.activity.game.ActivityGameEntity;
import com.hungrypanda.app.server.entity.activity.game.ActivityGamePrizesEntity;
import com.miller.userapp.mapper.game.ActivityGameMapper;
import com.miller.userapp.mapper.game.ActivityGamePrizesMapper;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author panjuxiang
 * @since 2025/2/20 15:24
 */
public class GameUtils {
    private static ActivityGameMapper activityGameMapper;
    private static ActivityGamePrizesMapper activityGamePrizesMapper;

    static {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        activityGameMapper = sqlSession.getMapper(ActivityGameMapper.class);
        activityGamePrizesMapper = sqlSession.getMapper(ActivityGamePrizesMapper.class);

    }


    /**
     * 根据gameSn查gameId
     *
     * @param gameSn
     * @return
     */
    public static Long getGameIdByGameSn(String gameSn) {
        ActivityGameEntity activityGameEntity = activityGameMapper.selectOne(
                new LambdaQueryWrapper<ActivityGameEntity>().eq(ActivityGameEntity::getSn, gameSn));
        if (activityGameEntity == null) {
            return 0L;
        }
        return activityGameEntity.getId();
    }

    /**
     * 将对应游戏的所有奖品概率初始化为0，仅对游戏奖品（不更新幸运值奖品）
     *
     * @param gameSn gameSn
     */
    public static void initGamePrizeOdds(String gameSn) {
        Long gameId = getGameIdByGameSn(gameSn);

        initGamePrizeOdds(gameId);

    }

    /**
     * 将对应游戏的所有奖品概率初始化为0，仅对游戏奖品（不更新幸运值奖品）
     *
     * @param gameId gameId
     */
    public static void initGamePrizeOdds(long gameId) {

        UpdateWrapper<ActivityGamePrizesEntity> updateWrapper = new UpdateWrapper<ActivityGamePrizesEntity>()
                .eq("game_id", gameId)
                .eq("prizes_belong", 1)
                .eq("is_del",0);

        ActivityGamePrizesEntity activityGamePrizesEntity = new ActivityGamePrizesEntity();
        activityGamePrizesEntity.setUserTypeOdds(0);
        int update = activityGamePrizesMapper.update(activityGamePrizesEntity, updateWrapper);


    }


}
