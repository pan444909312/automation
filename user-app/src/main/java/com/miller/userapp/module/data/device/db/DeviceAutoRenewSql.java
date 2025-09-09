package com.miller.userapp.module.data.device.db;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hungrypanda.app.server.entity.invite.InviteAwardBenefitRecordEntity;
import com.hungrypanda.app.server.entity.redpacket.UserNewRedPacketRecordEntity;
import com.hungrypanda.app.server.entity.user.UserBenefitRedPacketRecord;
import com.hungrypanda.app.server.entity.user.UserLabelEntity;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.miller.userapp.mapper.device.NewRedPacketRecordMapper;
import com.miller.userapp.mapper.device.NewUserBenefitRecordMapper;
import com.miller.userapp.mapper.device.NewUserInviteBenefitRecordMapper;
import com.miller.userapp.mapper.device.UserLogInfoMapper;
import com.miller.userapp.mapper.shop.ShopNewUserLabelMapper;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;

public class DeviceAutoRenewSql {
    SqlSession sqlSession = DBUtils.getDBOfPandaTest();

    public void deleteUserLog(String deviceId){
//        删除user_log表对应设备的数据
        UserLogInfoMapper userLogInfoMapper = sqlSession.getMapper(UserLogInfoMapper.class);
        UpdateWrapper<UserLogEntity> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("device_Id",deviceId);
        userLogInfoMapper.delete(deleteWrapper);
    }
//    删除hp_user_benefit_red_packet_record表对应设备的数据
    public void deleteNewUserBenefitRecord(String deviceId){
        NewUserBenefitRecordMapper newUserBenefitRecordMapper=sqlSession.getMapper(NewUserBenefitRecordMapper.class);
        UpdateWrapper<UserBenefitRedPacketRecord> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("device_Id",deviceId);
        newUserBenefitRecordMapper.delete(deleteWrapper);
    }
    //    删除hp_user_benefit_red_packet_record表对应设备的数据
    public void deleteNewUserInviteBenefitRecord(String deviceId){
        NewUserInviteBenefitRecordMapper newUserInviteBenefitRecordMapper=sqlSession.getMapper(NewUserInviteBenefitRecordMapper.class);
        UpdateWrapper<InviteAwardBenefitRecordEntity> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("device_Id",deviceId);
        newUserInviteBenefitRecordMapper.delete(deleteWrapper);
    }
    public void deleteRedPacketRecord(String deviceId){
        NewRedPacketRecordMapper NewRedPacketRecordMapper=sqlSession.getMapper(NewRedPacketRecordMapper.class);
        UpdateWrapper<UserNewRedPacketRecordEntity> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("device_Id",deviceId);
        NewRedPacketRecordMapper.delete(deleteWrapper);
    }
//    删除上述所有设备数据
    public void deviceAutoRenew(String deviceId){
        deleteUserLog(deviceId);
        deleteNewUserBenefitRecord(deviceId);
        deleteNewUserInviteBenefitRecord(deviceId);
        deleteRedPacketRecord(deviceId);
    }


}
