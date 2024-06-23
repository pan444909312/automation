package com.miller.userapp.module.person.address.create.flow;

import com.alibaba.fastjson.JSONPath;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.userapp.module.person.address.create.request.AddressRequestDTO;
import com.miller.userapp.module.person.address.create.response.AddressResponseDTO;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.LoginUtils;

import java.util.Map;

/**
 * 目前里面包含了新增、编辑、删除的场景，
 * todo 待之后明确结构划分后在调整结构和命名
 * @author panjuxiang
 * @since 2024/3/22 18:30
 */
public class AddressEditFlow {

    private String saveOrUpdateUrl = BusinessConstant.DOMAIN + "/api/app/user/v1/address/edit";
    private String deleteUrl = BusinessConstant.DOMAIN + "/api/app/user/v1/address/edit";

    private Map<String,Object> headers = LoginUtils.getHeaderWithAuth();



    /**
     * 添加地址接口调用
     * @param addressRequestDTO
     * @return
     */
    public AddressResponseDTO addAddress(AddressRequestDTO addressRequestDTO) {


        AddressResponseDTO addressResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(saveOrUpdateUrl, null, headers, addressRequestDTO, null, AddressResponseDTO.class);

        String addressEditResponseDTOJson = JSONUtils.toJSONString(addressResponseDTO);

        Object addressId = JSONPath.read(addressEditResponseDTOJson, "$.result.addressId");


        CacheUtils.set("addressId",addressId);

        return addressResponseDTO;
    }

    /**
     * 编辑地址接口调用
     * @param addressRequestDTO
     * @return
     */
    public AddressResponseDTO editAddress(AddressRequestDTO addressRequestDTO){

        AddressResponseDTO addressResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(saveOrUpdateUrl, null, headers, addressRequestDTO, null, AddressResponseDTO.class);

        return addressResponseDTO;
    }

    /**
     * 删除地址接口调用
     * @param addressRequestDTO
     * @return
     */
    public AddressResponseDTO deleteAddress(AddressRequestDTO addressRequestDTO){

        AddressResponseDTO addressResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(deleteUrl, null, headers, addressRequestDTO, null, AddressResponseDTO.class);

        return addressResponseDTO;
    }

}
