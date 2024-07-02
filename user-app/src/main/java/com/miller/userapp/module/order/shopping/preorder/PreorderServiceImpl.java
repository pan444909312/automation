package com.miller.userapp.module.order.shopping.preorder;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hungrypanda.app.server.dto.shop.ShopDTO;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.vo.shop.ShopPeakTimeJsonVO;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.userapp.mapper.shop.ShopMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PreorderServiceImpl {
    private SqlSession sqlSession;
    public PreorderServiceImpl(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    private ShopMapper getShopMapper(){
        return sqlSession.getMapper(ShopMapper.class);
    }

    public ShopEntity getShopDTO(){
        ShopMapper shopMapper = getShopMapper();
        QueryWrapper<ShopEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ShopEntity> lambda = queryWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopId);
        ShopEntity shop = shopMapper.selectOne(queryWrapper);
        return shop;
    }

    /**
     * 测试站按照服务器时区计算-即杭州
     * @return
     */
    public int getMakeTimeByShop(){
        ShopEntity shop = getShopDTO();
        int makeTime = shop.getMakeTime();
        if (StringUtils.isBlank(shop.getPeakTime())) {
            return shop.getOffPeakTime() == null ? 0 : shop.getOffPeakTime();
        }
        List<ShopPeakTimeJsonVO> listJson = JSON.parseArray(shop.getPeakTime(), ShopPeakTimeJsonVO.class);
        if (CollectionUtils.isEmpty(listJson)) {
            return shop.getOffPeakTime() == null ? 0 : shop.getOffPeakTime();
        }
        boolean isWorkDay = true;
        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        if (dayOfWeek.equals(DayOfWeek.SATURDAY ) || dayOfWeek.equals(DayOfWeek.SUNDAY)){
            isWorkDay = false;
        }
        LocalTime localTime = localDateTime.toLocalTime();
        for (ShopPeakTimeJsonVO jsonVO : listJson){
            if (isWorkDay && "工作日".equals(jsonVO.getDayType())){
             LocalTime startTime = LocalTime.parse(jsonVO.getStartTime());
             LocalTime endTime = LocalTime.parse(jsonVO.getEndTime());
             if(inTime(localTime,startTime,endTime)){
                 return jsonVO.getTime();
             }
            } else if (!isWorkDay && "周末".equals(jsonVO.getDayType())) {
                LocalTime startTime = LocalTime.parse(jsonVO.getStartTime());
                LocalTime endTime = LocalTime.parse(jsonVO.getEndTime());
                if(inTime(localTime,startTime,endTime)){
                    return jsonVO.getTime();
                }
            }
        }

        return  makeTime;
    }
    private boolean inTime(LocalTime nowTime,LocalTime startTime, LocalTime endTime){
        if(nowTime.isAfter(startTime) && nowTime.isBefore(endTime)){
            return true;
        }
        return false;
    }
}
