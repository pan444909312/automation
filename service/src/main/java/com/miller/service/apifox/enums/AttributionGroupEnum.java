package com.miller.service.apifox.enums;

import lombok.Getter;

@Getter
public enum AttributionGroupEnum {
    B("347781"),
    C("347946"),
    P("347947"),
    D("347945"),
    DEBUG("DEBUG")

    ;
    String t;

    AttributionGroupEnum(String t) {
        this.t = t;
    }


}
