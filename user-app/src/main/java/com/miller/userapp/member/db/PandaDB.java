package com.miller.userapp.member.db;

import com.miller.service.framework.db.DBUtils;

import java.util.Objects;

public class PandaDB {
    private static DBUtils dbUtils ;
    private static final String USERNAME = "panda_test";
    private static final String PASSWORD = "Pan$te19*";
    private static final String URL="jdbc:mysql://rm-3ns24734o9z8747d0jo.mysql.rds.aliyuncs.com/panda_test?useUnicode=true&characterEncoding=utf8&useSSL=false&tinyInt1isBit=false&transformedBitIsBoolean=false&serverTimezone=Asia/Shanghai";
    private PandaDB(){

    }
    public static DBUtils getDBInstance () {
        if (Objects.isNull(dbUtils)){
            dbUtils = new DBUtils(URL,USERNAME,PASSWORD);
            return dbUtils;
        }
        return dbUtils;
    }
}
