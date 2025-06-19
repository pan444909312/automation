package com.miller.userapp.module.activity.newuserpopup.request;

import lombok.Data;

/**
 * 新人权益弹窗接口请求参数
 */
@Data
public class NewUserPopupRequestDTO {
    private  int showEntry;
    private  int showModuleAd;
    private  int type;

}
