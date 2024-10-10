package com.miller.common.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<>();

    //    构造函数私有化
    private Result(){}

    /**
     * 请求成功
     * @return
     */
    public static Result success(){
        Result result = new Result();
        result.setCode(ResponseEnum.SUCCESS_200.getCode());
        result.setMessage(ResponseEnum.SUCCESS_200.getMessage());

        return result;
    }

    /**
     * 请求失败
     * @return
     */
    public static Result fail(){
        Result result = new Result();
        result.setCode(ResponseEnum.FAILURE_SERVICE_ERROR.getCode());
        result.setMessage(ResponseEnum.FAILURE_SERVICE_ERROR.getMessage());

        return result;
    }

    public Result data(String str,Object obj){

        this.getData().put(str,obj);
        return this;
    }
    public Result data(Map<String,Object> data){

        this.data = data;
        return this;
    }

    public Result code(int code){
        this.setCode(code);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }
}
