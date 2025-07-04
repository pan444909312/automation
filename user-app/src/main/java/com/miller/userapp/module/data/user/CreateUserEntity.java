package com.miller.userapp.module.data.user;

import com.miller.userapp.module.person.address.create.request.AddressRequestDTO;
import lombok.Data;

@Data
public class CreateUserEntity {
    private String loginPassword;
    private String payPassword;
    private Integer balance;
    private String phone;
    private AddressRequestDTO address;


}
