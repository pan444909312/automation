package com.miller.entity.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginDao {

    private String userName;
    private String password;
}
