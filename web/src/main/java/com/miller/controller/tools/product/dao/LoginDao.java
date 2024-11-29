package com.miller.controller.tools.product.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginDao {

    private String userName;
    private String password;
}
