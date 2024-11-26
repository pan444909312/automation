package com.miller.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.erp.moudle.pc.authcode.flow.AuthCodeFlow;
import com.miller.erp.moudle.pc.authlogin.flow.AuthLoginFlow;
import com.miller.service.LoginService;
import com.miller.entity.dao.LoginDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    /**
     * erp 登录到获取 pc 站 token
     * @param loginDao
     * @param shopId
     * @return pcToken
     */

    public String pcLogin(String shopId) {
        ERPLoginFlow.loginByDefaultUser();
        final String authCode = AuthCodeFlow.getAuthCode(shopId);
        return AuthLoginFlow.getPcToken(authCode);
    }




}
