package com.miller.service.apifox;


public interface ApifoxToolsService {

    boolean sendDingDing(String access_token, String timestamp, String sign, String body);
}
