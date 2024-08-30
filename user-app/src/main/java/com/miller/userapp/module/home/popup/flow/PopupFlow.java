package com.miller.userapp.module.home.popup.flow;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.entity.popup.ActivityPopupRedPacketGroupRedPacketCollect;
import com.hungrypanda.app.server.entity.popup.reissue.ActivityPopupReissueRecordEntity;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.mapper.redpacket.ActivityPopupRedPacketGroupRedPacketCollectMapper;
import com.miller.userapp.mapper.redpacket.ActivityPopupReissueRecordMapper;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * 流程_天降弹窗
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/08/29 18:59:46
 */
@Slf4j
public class PopupFlow {

    public static void main(String[] args) {
        // cleanSkyWindowData(1398664550, "九江市");
    }

    /**
     * 天降弹窗前置条件初始化
     * <p>
     *     <ul>
     *         <li>Step1: 删除用户红包列表</li>
     *         <li>Step2: 删除用户领取记录表</li>
     *         <li>Step3: 删除疲劳度记录和红包记录用于重新触发补发直塞红包</li>
     *         <li>Step4: 删除单日弹窗弹出次数</li>
     *         <li>Step5: 删除最后一次弹窗弹出的时间</li>
     *         <li>Step6: 删除天降首次弹窗的联盟红包和神券红包结果 ，用于给联盟频道使用的红包</li>
     *     </ul>
     * </p>
     *
     * @param userId   用户id
     * @param cityName 城市名称
     */
    public static void cleanSkyWindowData(Integer userId, String cityName) {
        // init db
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        UserCdKeyMapper userCdKeyMapper = sqlSession.getMapper(UserCdKeyMapper.class);
        ActivityPopupReissueRecordMapper activityPopupReissueRecordMapper = sqlSession.getMapper(ActivityPopupReissueRecordMapper.class);
        ActivityPopupRedPacketGroupRedPacketCollectMapper activityPopupRedPacketGroupRedPacketCollectMapper =
                sqlSession.getMapper(ActivityPopupRedPacketGroupRedPacketCollectMapper.class);

        // 删除用户红包列表
        userCdKeyMapper.delete(
                new LambdaQueryWrapper<UserCdKeyEntity>()
                        .eq(UserCdKeyEntity::getUserId, userId)
        );
        log.info("删除用户{}红包列表", userId);

        // 删除用户领取记录表
        activityPopupReissueRecordMapper.delete(
                new LambdaQueryWrapper<ActivityPopupReissueRecordEntity>()
                        .eq(ActivityPopupReissueRecordEntity::getUserId, userId)
        );
        log.info("删除用户{}领取记录表", userId);

        // 删除疲劳度记录和红包记录用于重新触发补发直塞红包
        activityPopupRedPacketGroupRedPacketCollectMapper.delete(
                new LambdaQueryWrapper<ActivityPopupRedPacketGroupRedPacketCollect>()
                        .eq(ActivityPopupRedPacketGroupRedPacketCollect::getUserId, userId)
        );
        log.info("删除用户{}疲劳度记录和红包记录用于重新触发补发直塞红包", userId);

        // 删除单日弹窗弹出次数记录。达到ERP后台设定的值之后则不会继续弹出，所以需要删除Redis缓存的值。RED_PACKET_GROUP_POP_RECORD_用户ID_九江市
        RedisUtils.getRedisInstance().delete("RED_PACKET_GROUP_POP_RECORD_" + userId + "_" + cityName);
        log.info("删除用户{}在{}单日弹窗弹出次数记录", userId, cityName);
        // 删除最后一次弹窗弹出的时间
        RedisUtils.getRedisInstance().delete("RED_PACKET_GROUP_LAST_POP_TIME_CITY_" + userId + "_" + cityName);
        log.info("删除用户{}在{}最后一次弹窗弹出的时间", userId, cityName);
        // 删除天降首次弹窗的联盟红包和神券红包结果 ，用于给联盟频道使用的红包，缓存
        RedisUtils.getRedisInstance().delete("popup.league.frp." + userId + "." + cityName);
        log.info("删除用户{}在{}天降首次弹窗的联盟红包和神券红包结果", userId, cityName);
    }
}