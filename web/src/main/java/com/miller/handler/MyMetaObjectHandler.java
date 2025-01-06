package com.miller.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author panjuxiang
 * @since 2024/9/26 9:18
 */
@Component
@Log4j2
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("开始插入填充...");
        this.setFieldValByName("createTime",System.currentTimeMillis(),metaObject);
        this.setFieldValByName("updateTime",System.currentTimeMillis(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("开始更新填充...");
        this.setFieldValByName("updateTime",System.currentTimeMillis(),metaObject);
    }


}