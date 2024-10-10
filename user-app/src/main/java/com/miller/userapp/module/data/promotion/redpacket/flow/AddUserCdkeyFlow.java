package com.miller.userapp.module.data.promotion.redpacket.flow;

import com.alibaba.fastjson.JSONObject;
import com.hungrypanda.app.server.common.enums.redpacket.RedPacketValidTypeEnum;
import com.hungrypanda.app.server.entity.redpacket.RedPacketEntity;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.hungrypanda.app.server.entity.redpacket.UserCollectNumEntity;
import com.miller.userapp.module.data.promotion.redpacket.db.*;
import com.miller.userapp.module.data.promotion.redpacket.request.AddUserCdkeyRequestDTO;
import com.miller.userapp.module.data.user.db.UserSql;
import com.panda.promotion.server.api.constant.PromotionEnum;
import com.panda.promotion.server.api.constant.RedPacketPromotionEnum;
import com.panda.promotion.server.api.util.BaseExceptionUtil;
import com.panda.promotion.server.common.enums.StatusEnum;
import com.panda.promotion.server.common.enums.template.LanguageCodeEnum;
import com.panda.promotion.server.common.util.AppConfig;
import com.panda.promotion.server.common.util.DateUtil;
import com.panda.promotion.server.dal.dataobject.redpacket.LimitJsonDO;
import com.panda.promotion.server.dal.dataobject.redpacket.RedPacketAddressRelationDO;
import com.panda.promotion.server.dal.dataobject.redpacket.RedPacketTagRelationDO;
import com.panda.promotion.server.dal.dataobject.redpacket.UserCdkeyCollectSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AddUserCdkeyFlow {

    private  SqlSession sqlSession;
    private RedPacketSql redPacketSql;
    private UserCollectNumSql userCollectNumSql;
    private RedPacketTagRelationSql redPacketTagRelationSql;

    private RedPacketAddressRelationSql redPacketAddressRelationSql;
    private UserCdkeySql userCdkeySql;
    private UserSql userSql;
    private CityConfigSql cityConfigSql;

    Byte byteNull = null;

    public AddUserCdkeyFlow(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    public void initSql(){
        redPacketSql = new RedPacketSql(sqlSession);
        userCollectNumSql = new UserCollectNumSql(sqlSession);
        redPacketTagRelationSql = new RedPacketTagRelationSql(sqlSession);
        redPacketAddressRelationSql = new RedPacketAddressRelationSql(sqlSession);
        userCdkeySql = new UserCdkeySql(sqlSession);
        userSql = new UserSql(sqlSession);
        cityConfigSql = new CityConfigSql(sqlSession);
    }

    public void addCdKey(AddUserCdkeyRequestDTO req) {

        RedPacketEntity redPacket = redPacketSql.selectOneByRedPacketId(req.getRedPacketId());
        BaseExceptionUtil.checkTrue(Objects.nonNull(redPacket), "红包不存在");

        System.out.println(redPacket);

        UserCollectNumEntity userCollectNum = null;
        if (Objects.nonNull(req.getUserId()) && Objects.nonNull(req.getRedPacketId())){
            userCollectNum = userCollectNumSql.selectByUserIdAndRedPacketId(req.getUserId(), req.getRedPacketId());
        }

        List<RedPacketTagRelationDO> redPacketTagRelationEntityList = redPacketTagRelationSql.getTagIdByRedPacketId(redPacket.getRedPacketId());
        List<Long> tagIds = redPacketTagRelationEntityList.stream().map(RedPacketTagRelationDO::getTagId).collect(Collectors.toList());

        List<RedPacketAddressRelationDO> redPacketAddressRelationDOList = redPacketAddressRelationSql.getAddTagIdByRedPacketId(redPacket.getRedPacketId());
        List<Long> addTags = redPacketAddressRelationDOList.stream().map(RedPacketAddressRelationDO::getAddTagId).collect(Collectors.toList());

        // 发放红包到userCdkey
        UserCdKeyEntity userCdkey = new UserCdKeyEntity();

        userCdkey.setActivitySn(redPacket.getActivitySn());
        userCdkey.setCollectSource(Optional.ofNullable(req.getCollectSource()).orElse(RedPacketPromotionEnum.RedPacketCollectSourceEnum.NONE.getCode()));

        //设置状态
        if (RedPacketPromotionEnum.RedPacketCollectSourceEnum.MEMBER_VIRTUAL_SEND.matchCode(req.getCollectSource())) {
            userCdkey.setStatus(RedPacketPromotionEnum.CdKeyStatus.VIRTUALVALID.getCode().byteValue());
        } else {
            userCdkey.setStatus(RedPacketPromotionEnum.CdKeyStatus.VALID.getCode().byteValue());
        }

        // 设置时间
        setValidDaysStartEndTime(redPacket, userCdkey, req.getStartTime(), req.getEndTime());
        // 设置红包金额
        Integer redPacketPrice = redPacket.getRedPacketPrice();
        if (redPacketPrice != null && redPacketPrice > 0) {
            userCdkey.setRedPacketPrice(redPacketPrice);
        } else {
            userCdkey.setRedPacketPrice(NumberUtils.INTEGER_ZERO);
        }

        System.out.println(userSql.getUserRold0(req.getUserId()));
        userCdkey.setUserPhone(userSql.getUserRold0(req.getUserId()).getUserName());

        //加密字段 todo
 //       userCdkey.setUserPhoneMask(encryptionServerHelper.mobileEncryptStr(userCdkey.getUserPhone()));
        userCdkey.setRedPacketCode(StringUtils.isNotBlank(req.getRedPacketCode()) ? req.getRedPacketCode() : UUID.randomUUID().toString());
//        userCdkey.setLockStatus(req.getLockStatus().byteValue() == byteNull ? StatusEnum.NO.getCode().byteValue() : req.getLockStatus().byteValue());
        userCdkey.setCdKey(StringUtils.isBlank(req.getCdKey()) ? StringUtils.EMPTY : req.getCdKey());
        userCdkey.setUserId(req.getUserId());

        long nowTime = System.currentTimeMillis();
        userCdkey.setCreateTime(nowTime);
        userCdkey.setUpdateTime(nowTime);
        userCdkey.setIsDel(StatusEnum.NO.getCode().byteValue());
        userCdkey.setIsUsed(StatusEnum.NO.getCode().byteValue());
        userCdkey.setQrCode(StringUtils.EMPTY);
        userCdkey.setRedPacketTypeId(redPacket.getRedPacketTypeId() == null ? NumberUtils.LONG_ZERO : redPacket.getRedPacketTypeId().longValue());
        userCdkey.setIsExchange(NumberUtils.INTEGER_ONE.byteValue());
        userCdkey.setTypeName(RedPacketPromotionEnum.RedPacketEnum.getRedPacketName(Math.toIntExact(redPacket.getRedPacketTypeId())));
        userCdkey.setRedPacketName(StringUtils.isBlank(redPacket.getRedPacketName()) ? StringUtils.EMPTY : redPacket.getRedPacketName());
        userCdkey.setRedPacketNameEn(StringUtils.isBlank(redPacket.getRedPacketNameEn()) ? StringUtils.EMPTY : redPacket.getRedPacketNameEn());
        userCdkey.setRedPacketId(redPacket.getRedPacketId() == null ? NumberUtils.LONG_ZERO : redPacket.getRedPacketId());
        userCdkey.setCountry(StringUtils.isEmpty(redPacket.getCountry()) ? StringUtils.EMPTY : redPacket.getCountry());
        userCdkey.setCity(StringUtils.isEmpty(redPacket.getCity()) ? StringUtils.EMPTY : redPacket.getCity());
        userCdkey.setShopId(redPacket.getShopId() == null ? NumberUtils.LONG_ZERO : redPacket.getShopId());
        userCdkey.setMerchantBearPrice(redPacket.getMerchantBearPrice() == null ? NumberUtils.INTEGER_ZERO : redPacket.getMerchantBearPrice());
        userCdkey.setPlatformBearPrice(redPacket.getPlatformBearPrice() == null ? NumberUtils.INTEGER_ZERO : redPacket.getPlatformBearPrice());
        userCdkey.setShopBearPercent(redPacket.getShopBearPercent() == null ? BigDecimal.ZERO : redPacket.getShopBearPercent());
        userCdkey.setPlatformBearPercent(redPacket.getPlatformBearPercent() == null ? BigDecimal.ZERO : redPacket.getPlatformBearPercent());
        userCdkey.setDeliveryBearType(redPacket.getDeliveryBearType() == 0 ? NumberUtils.INTEGER_ZERO.byteValue() : redPacket.getDeliveryBearType().byteValue());
        userCdkey.setDiscountRate(redPacket.getDiscountRate() != null ? redPacket.getDiscountRate() : BigDecimal.ZERO);
        userCdkey.setDiscountLimit(redPacket.getDiscountLimit() != null ? redPacket.getDiscountLimit() : NumberUtils.INTEGER_ZERO);
        userCdkey.setLanguageCode(StringUtils.isNotBlank(redPacket.getLanguageCode()) ? redPacket.getLanguageCode() : LanguageCodeEnum.CN.getType());
        //实体类没有ValidTimeType todo
    //    userCdkey.setValidTimeType(Optional.ofNullable(redPacket.getValidTimeType()).orElse(NumberUtils.INTEGER_ZERO));
        userCdkey.setThresholdPrice(Optional.ofNullable(redPacket.getThresholdPrice()).orElse(NumberUtils.INTEGER_ZERO));
        userCdkey.setCrowd(StringUtils.isNotBlank(redPacket.getCrowd().toString()) ? Integer.valueOf(redPacket.getCrowd()) : NumberUtils.INTEGER_ONE);
        userCdkey.setUseChannel(Optional.ofNullable(redPacket.getUseChannel()).orElse(NumberUtils.INTEGER_ZERO));
        userCdkey.setDeliveryDistanceLowerLimit(Optional.ofNullable(redPacket.getDeliveryDistanceLowerLimit()).orElse(-1));
        userCdkey.setDeliveryDistanceUpperLimit(Optional.ofNullable(redPacket.getDeliveryDistanceUpperLimit()).orElse(-1));
        userCdkey.setIsOpenTimeLimit(Optional.ofNullable(redPacket.getIsOpneTimeLimit()).orElse(NumberUtils.INTEGER_ZERO));
        userCdkey.setOpenTimeLimitString(Optional.ofNullable(redPacket.getOpneTimeLimitString()).orElse(StringUtils.EMPTY));
        userCdkey.setIsTagLimit(Optional.ofNullable(redPacket.getIsTagLimit()).orElse(NumberUtils.INTEGER_ZERO));
        userCdkey.setMutexType(Optional.ofNullable(redPacket.getMutexType()).orElse(NumberUtils.INTEGER_ZERO));
        if (CollectionUtils.isNotEmpty(tagIds)) {
            userCdkey.setTagJsonList(JSONObject.toJSONString(tagIds));
        } else {
            userCdkey.setTagJsonList(StringUtils.EMPTY);
        }

        LimitJsonDO limitJsonDO = new LimitJsonDO();
        if (CollectionUtils.isNotEmpty(addTags)) {
            limitJsonDO.setAddTagIds(addTags);
        }
        if (StatusEnum.YES.getCode().equals(redPacket.getIsAllowAddPrice())) {
            limitJsonDO.setIsAllowAddPrice(redPacket.getIsAllowAddPrice());
        }
        userCdkey.setLimitJson(JSONObject.toJSONString(limitJsonDO));
        userCdkeySql.insert(userCdkey);

        // 记录来源活动 todo

        //领取后sendNumber加1
        redPacketSql.updateSendNum(req.getRedPacketId());

        //领取后在UserCollectNum表加1;
        if (Objects.nonNull(req.getUserId()) && Objects.nonNull(req.getRedPacketId())){
            handleUserCollectNum(userCollectNum, req.getUserId(),req.getRedPacketId());
        }

    }

    private String checkRedPacketStatus(long now, RedPacketEntity redPacket) {
        //红包状态判断,作废
        if (Objects.equals(RedPacketPromotionEnum.RedPacketStatus.ABANDON.getCode(), redPacket.getStatus())) {
            return "已失效";
        }
        //红包状态判断,删除
        if (!redPacket.getIsDel().equals(0)) {
            return "红包不可用";
        }
        //区分过期类型是有效期区间、还是有效天数
        if (Objects.equals(RedPacketValidTypeEnum.VALID_TIMES.getValidType(), redPacket.getValidTimeType())) {
            //只要未过期就可以领取
            if (redPacket.getEndTime() == null || now > redPacket.getEndTime()) {
                return "红包已过期";
            }
        } else if (Objects.equals(RedPacketValidTypeEnum.VALID_DAYS.getValidType(), redPacket.getValidTimeType())) {
            if (redPacket.getValidDays() == null || Objects.equals(NumberUtils.INTEGER_ZERO, redPacket.getValidDays())) {
                //有效期必须大于0
                return "红包已过期";
            }
        } else {
            //老数据、保持老逻辑
            if (now > redPacket.getEndTime()) {
                return "红包已过期";
            }

        }
        return "";
    }


    /**
     * 设置红包的时间
     */
    private void setValidDaysStartEndTime(RedPacketEntity redPacket, UserCdKeyEntity userCdkey, Long startTime, Long endTime) {
        if (startTime != null && startTime > 0 && endTime != null && endTime > 0) {
            userCdkey.setValidDays(NumberUtils.INTEGER_ZERO);
            userCdkey.setStartTime(startTime);
            userCdkey.setEndTime(endTime);
        } else {
            userCdkey.setValidDays(redPacket.getValidDays());
            setStartOrEndTime(userCdkey, redPacket);
        }
    }
    /**
     * 获取手机号
     */
    /*
    private String getUserPhone(String userPhone, Long userId) {
        if (StringUtils.isBlank(userPhone) && Objects.nonNull(userId)) {
            UserEntity userDO = userSql.getUser(userId);
            if (Objects.nonNull(userDO)) {
                if(encryptionServerHelper.decryptSwitch()){
                    userPhone = encryptionServerHelper.mobileDecryptStr(userDO.getUserTelphoneMask(), userDO.getUserTelphone());
                    userPhone = StringUtils.isBlank(userPhone) ? encryptionServerHelper.mobileDecryptStr(userDO.getUserNameMask(), userDO.getUserName()) : userPhone;
                }else{
                    userPhone = userDO.getUserTelphone();
                    userPhone = StringUtils.isBlank(userPhone) ? userDO.getUserName() : userPhone;
                }
            }
        }
        return userPhone;
    }*/

    /**
     * 设置红包开始结束时间
     */
    private void setStartOrEndTime(UserCdKeyEntity userCdkey, RedPacketEntity redPacket) {
        String cityName = redPacket.getCity();

        String timezone = StringUtils.isBlank(cityName)
                ? AppConfig.getConfig().getTimeZoneDefault() : cityConfigSql.getCityConfigByCityName(cityName).getTimeZone();
        Integer validDays = redPacket.getValidDays();
        validDays = validDays == null ? 0 : validDays;
        //当前日期的0点时间
        long currentDay = DateUtil.getToday0h0m0sMillsByZone(timezone);

        Integer validTimeType = redPacket.getValidTimeType();
        if (RedPacketPromotionEnum.ValidTimeTypeEnum.TIME_DAY.getCode().equals(validTimeType) || validDays > 0) {
            long endTime = currentDay + new BigDecimal(validDays + 1).multiply(BigDecimal.valueOf(24 * 3600 * 1000)).longValue() - 1L;
            userCdkey.setStartTime(currentDay);
            userCdkey.setEndTime(endTime);
            return;
        }
        userCdkey.setStartTime(Math.max(currentDay, redPacket.getStartTime()));
        userCdkey.setEndTime(redPacket.getEndTime());
    }
    public UserCdkeyCollectSource buildUserCdkeyCollectSource(UserCdKeyEntity userCdkey, String activitySn, String remark) {
        UserCdkeyCollectSource userCdkeyCollectSource = new UserCdkeyCollectSource();
        long nowTime = System.currentTimeMillis();
        userCdkeyCollectSource.setUserCdkeyId(userCdkey.getUserCdkeyId());
        userCdkeyCollectSource.setCollectSource(userCdkey.getCollectSource());
        userCdkeyCollectSource.setGmtCreated(nowTime);
        userCdkeyCollectSource.setActivitySn(StringUtils.isEmpty(activitySn) ? "" : activitySn);
        userCdkeyCollectSource.setRemark(remark);
        return userCdkeyCollectSource;
    }

    private void handleUserCollectNum(UserCollectNumEntity userCollectNum, Long userId, Long redPacketId) {
        if (Objects.isNull(userCollectNum)){
            UserCollectNumEntity insertUserCollectNum = new UserCollectNumEntity();
            insertUserCollectNum.setCollectNum(1);
            insertUserCollectNum.setUserId(userId);
            insertUserCollectNum.setRedPacketId(redPacketId);
            insertUserCollectNum.setUnUsedNum(1);
            insertUserCollectNum.setIsDel(PromotionEnum.DeleteStatus.NORMAL.getCode());
            insertUserCollectNum.setCreateTime(System.currentTimeMillis());
            userCollectNumSql.insertSelective(insertUserCollectNum);
            return;
        }
        //存在则更新数据
        userCollectNumSql.updateNumByUserIdAndRedPacketId(System.currentTimeMillis(),userId,redPacketId);
    }
}
