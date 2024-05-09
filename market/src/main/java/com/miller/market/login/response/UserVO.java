package com.miller.market.login.response;

import com.panda.market.dal.entity.User;

public class UserVO {

    private String token;

    private User user;

    //外层h5用
    private Boolean inviteSuccess = false;

    private String msg;

    public User getUser() {
        return this.user;
    }

    public String getToken() {
        return this.token;
    }


    public void setToken(final String token) {
        this.token = token;
    }


}
