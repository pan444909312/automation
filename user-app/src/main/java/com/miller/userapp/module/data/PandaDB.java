package com.miller.userapp.module.data;

import com.miller.service.framework.db.DBUtils;
import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.data.DataName;
import org.apache.ibatis.session.SqlSession;

import java.util.Objects;

public class PandaDB {
    private static DBUtils dbUtils ;
    private static SqlSession sqlSession;
    private static SqlSession sqlSessionPay;
//    private static final String USERNAME = "panda_test";
//    private static final String PASSWORD = "Pan$te19*";
//    private static final String URL="jdbc:mysql://rm-3ns24734o9z8747d0jo.mysql.rds.aliyuncs.com/panda_test?useUnicode=true&characterEncoding=utf8&useSSL=false&tinyInt1isBit=false&transformedBitIsBoolean=false&serverTimezone=Asia/Shanghai";
    private static final String URL = PropertiesUtils.getProperty("spring.datasource.url");
    private static final String USERNAME = PropertiesUtils.getProperty("spring.datasource.username");
    private static final String PASSWORD = PropertiesUtils.getProperty("spring.datasource.password");
    private static final String PayURL = PropertiesUtils.getProperty("spring.datasource.pay.url");
    private PandaDB(){

    }
    public static DBUtils getDBInstance () {
        if (Objects.isNull(dbUtils)){
            dbUtils = new DBUtils(URL,USERNAME,PASSWORD);
            return dbUtils;
        }
        return dbUtils;
    }
    public static SqlSession getSqlSession () {
        if (Objects.isNull(sqlSession)){
            MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
            sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(URL, USERNAME, PASSWORD).getDataSource());
            return sqlSession;
        }
        return sqlSession;
    }
    public static SqlSession getSqlSession (Class<?> cls) {
        String source = cls.getAnnotation(DataName.class).value();
        MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
        switch (source){
            case "pay":
                if (Objects.isNull(sqlSessionPay)) {
                    sqlSessionPay = myBatisPlusConfig.getSqlSession(new DataSourceConfig(PayURL, USERNAME, PASSWORD).getDataSource());
                }
                return sqlSessionPay;
            default:
                if (Objects.isNull(sqlSession)){
                    sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(URL, USERNAME, PASSWORD).getDataSource());
                }
                return sqlSession;
        }
    }
    public static void main(String[] args){
//        ShopExtraInfoMapper shopExtraInfoMapper = PandaDB.getSqlSession().getMapper(ShopExtraInfoMapper.class);
//        QueryWrapper<ShopExtraInfoEntity> queryWrapper = new QueryWrapper<>();
//        LambdaQueryWrapper<ShopExtraInfoEntity> lambda = queryWrapper.lambda();
//        lambda.eq(ShopExtraInfoEntity::getShopId, TestCaseDataForMerchantConstant.shopId);
//        ShopExtraInfoEntity shopExtraInfoEntity = shopExtraInfoMapper.selectOne(queryWrapper);
//        System.out.println("shopExtraInfoEntity: "+shopExtraInfoEntity.getPreorderOpenType());
//        System.out.println("=============update 1============== " +shopExtraInfoEntity.getPreorderOpenType());
//        UpdateWrapper<ShopExtraInfoEntity> updateWrapper = new UpdateWrapper<>();
//        LambdaUpdateWrapper<ShopExtraInfoEntity> updateLambda  = updateWrapper.lambda();
//        updateLambda.eq(ShopExtraInfoEntity::getShopId,TestCaseDataForMerchantConstant.shopId);
//        updateLambda.set(ShopExtraInfoEntity::getPreorderOpenType,2);
//        shopExtraInfoMapper.update(null,updateWrapper);
//
//
//        System.out.println("value: "+shopExtraInfoEntity.getPreorderOpenType());
    }
}
