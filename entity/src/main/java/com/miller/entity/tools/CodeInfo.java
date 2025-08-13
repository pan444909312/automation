package com.miller.entity.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor


public class CodeInfo {
    private String telephone;
    private String createTime;
    private String verifycode;
}

