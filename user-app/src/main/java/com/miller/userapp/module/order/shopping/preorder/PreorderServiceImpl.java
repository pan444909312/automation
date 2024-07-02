package com.miller.userapp.module.order.shopping.preorder;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hungrypanda.app.server.config.ApiConfig;
import com.hungrypanda.app.server.dto.delivery.DeliveryTimeDTO;
import com.hungrypanda.app.server.entity.delivery.DeliveryTimeConfigDataEntity;
import com.hungrypanda.app.server.entity.delivery.DeliveryTimeConfigDataShopEntity;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.vo.shop.ShopPeakTimeJsonVO;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.userapp.mapper.shop.DeliveryTimeConfigDataMapper;
import com.miller.userapp.mapper.shop.ShopDeliveryTimeDataConfigMapper;
import com.miller.userapp.mapper.shop.ShopMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PreorderServiceImpl {
    @Value("${user.app.server.metric.unit:km}")
    private String metricUnit;
    private SqlSession sqlSession;
    public PreorderServiceImpl(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }
    private ShopMapper getShopMapper(){
        return sqlSession.getMapper(ShopMapper.class);
    }
    private ShopDeliveryTimeDataConfigMapper getShopDeliveryTimeDAtaConfigMapper(){
        return sqlSession.getMapper(ShopDeliveryTimeDataConfigMapper.class);
    }
    private DeliveryTimeConfigDataMapper getDeliveryTimeConfigDataMapper(){
        return  sqlSession.getMapper(DeliveryTimeConfigDataMapper.class);
    }

    public ShopEntity getShopDTO(){
        ShopMapper shopMapper = getShopMapper();
        QueryWrapper<ShopEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<ShopEntity> lambda = queryWrapper.lambda();
        lambda.eq(ShopEntity::getShopId, TestCaseDataForMerchantConstant.shopId);
        ShopEntity shop = shopMapper.selectOne(queryWrapper);
        return shop;
    }
    public DeliveryTimeConfigDataShopEntity getShopDeliveryTimeDataConfig(){
        ShopDeliveryTimeDataConfigMapper shopDeliveryTimeDataConfigMapper = getShopDeliveryTimeDAtaConfigMapper();
        QueryWrapper<DeliveryTimeConfigDataShopEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<DeliveryTimeConfigDataShopEntity> lambda = queryWrapper.lambda();
        lambda.eq(DeliveryTimeConfigDataShopEntity::getShopId, TestCaseDataForMerchantConstant.shopId);
        lambda.eq(DeliveryTimeConfigDataShopEntity::getIsDel,0);
        DeliveryTimeConfigDataShopEntity deliveryTimeConfigDataShopEntity = shopDeliveryTimeDataConfigMapper.selectOne(queryWrapper);
        return deliveryTimeConfigDataShopEntity;
    }
    public DeliveryTimeConfigDataEntity getDeliveryTimeConfigData(BigDecimal distance,String nowTime){
        DeliveryTimeConfigDataShopEntity deliveryTimeConfigDataShopEntity = getShopDeliveryTimeDataConfig();
        if (Objects.isNull(deliveryTimeConfigDataShopEntity)) return  null;
        DeliveryTimeConfigDataMapper deliveryTimeConfigDataMapper = getDeliveryTimeConfigDataMapper();
        QueryWrapper<DeliveryTimeConfigDataEntity> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<DeliveryTimeConfigDataEntity> lambda = queryWrapper.lambda();
        lambda.eq(DeliveryTimeConfigDataEntity::getConfigId, deliveryTimeConfigDataShopEntity.getConfigId());
        lambda.eq(DeliveryTimeConfigDataEntity::getIsDel,0);
//        lambda.le(DeliveryTimeConfigDataEntity::getDistanceStart,distance);
//        lambda.ge(DeliveryTimeConfigDataEntity::getDistanceEnd,distance);
        List<DeliveryTimeConfigDataEntity> deliveryTimeConfigDataEntityList = deliveryTimeConfigDataMapper.selectList(queryWrapper);
        Optional<DeliveryTimeConfigDataEntity> deliveryTimeConfigDataEntity = deliveryTimeConfigDataEntityList.stream().filter(a->
            distance.compareTo(a.getDistanceStart()) >= 0 && distance.compareTo(a.getDistanceEnd()) < 0 &&
                    nowTime.compareTo(a.getTimeStart()) >= 0 && nowTime.compareTo(a.getTimeEnd()) < 0

        ).findFirst();
        if (!deliveryTimeConfigDataEntity.isPresent()){
            deliveryTimeConfigDataEntity =  deliveryTimeConfigDataEntityList.stream().filter(a->
                            nowTime.compareTo(a.getTimeStart()) >= 0 && nowTime.compareTo(a.getTimeEnd()) < 0
            ).max(Comparator.comparing(DeliveryTimeConfigDataEntity :: getDistanceStart));


        }
        return deliveryTimeConfigDataEntity.orElse(null);
    }
    public DeliveryTimeDTO getDeliveryTime(Double distance){
        String nowTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        BigDecimal distanceNew = getDistanceForUnit(BigDecimal.valueOf(distance));
        DeliveryTimeConfigDataEntity deliveryTimeConfigDataEntity = getDeliveryTimeConfigData(distanceNew,nowTime);
        int makeTime = getMakeTimeByShop();
        if(Objects.isNull(deliveryTimeConfigDataEntity)){
            return new DeliveryTimeDTO(0,0,makeTime,null);
        }else {
            return new DeliveryTimeDTO(deliveryTimeConfigDataEntity.getNeedTimeStart(),deliveryTimeConfigDataEntity.getNeedTimeEnd(),makeTime,null);
        }
    }
    public  BigDecimal getDistanceForUnit(BigDecimal distance){
        if (Objects.isNull(distance)){
            return BigDecimal.ZERO;
        }
        if ("km".equalsIgnoreCase(metricUnit)) {
            return distance.divide(new BigDecimal(1000), 2, RoundingMode.HALF_UP);
        } else {
            return distance.divide(new BigDecimal(1609.344), 2, RoundingMode.HALF_UP);
        }
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
                //时间戳格式需要“HH:mm" ，不能是9:50这种
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
