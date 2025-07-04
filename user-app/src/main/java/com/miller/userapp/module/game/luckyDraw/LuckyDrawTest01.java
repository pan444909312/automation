package com.miller.userapp.module.game.luckyDraw;

import com.miller.userapp.module.game.utils.GameUtils;
import org.junit.jupiter.api.Test;

/**
 * @author panjuxiang
 * @since 2025/2/20 15:38
 */
public class LuckyDrawTest01 {

    @Test
    public void test(){
        System.out.println(GameUtils.getGameIdByGameSn("4TuuyGqDa2tw"));

        GameUtils.initGamePrizeOdds(664);
    }
}
