package com.miller.service.apifox;


import com.miller.service.apifox.enums.AttributionGroupEnum;

public interface ApifoxToolsService {

    boolean sendDingDing(String access_token, String timestamp, String sign, String body);

    void parsingReport(AttributionGroupEnum attributionGroup);
}
