package com.miller.controller.tools.dao;


import com.panda.erp.server.common.enums.EnvEnum;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ToolsBaseReqDao<T> {


    private int count;
    private T body;
}
