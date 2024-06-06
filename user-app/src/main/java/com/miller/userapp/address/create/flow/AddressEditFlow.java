package com.miller.userapp.address.create.flow;

import com.alibaba.fastjson.JSONPath;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.userapp.address.create.request.AddressRequestDTO;
import com.miller.userapp.address.create.response.AddressResponseDTO;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
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

    private Map<String,Object> headers;


    public Map<String, Object> setCommonHeaders() {

        if (headers != null){
            return headers;
        }
        headers = new HashMap<>();

        RequestUtils.setHeaders(headers);

        headers.put("Content-Type","application/json");
        headers.put("Authorization", "aa12137192fcf7ae6a77b86db8fc4228");
        return headers;
    }

    /**
     * 添加地址接口调用
     * @param addressRequestDTO
     * @return
     */
    public AddressResponseDTO addAddress(AddressRequestDTO addressRequestDTO) {

        Map<String, Object> headers = setCommonHeaders();

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
        Map<String, Object> headers = setCommonHeaders();

        AddressResponseDTO addressResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(saveOrUpdateUrl, null, headers, addressRequestDTO, null, AddressResponseDTO.class);

        return addressResponseDTO;
    }

    /**
     * 删除地址接口调用
     * @param addressRequestDTO
     * @return
     */
    public AddressResponseDTO deleteAddress(AddressRequestDTO addressRequestDTO){
        Map<String, Object> headers = setCommonHeaders();

        AddressResponseDTO addressResponseDTO = HttpUtils.sendPostRequestReturnJavaObject(deleteUrl, null, headers, addressRequestDTO, null, AddressResponseDTO.class);


        return addressResponseDTO;
    }

}
