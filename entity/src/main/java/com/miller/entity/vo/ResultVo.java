package com.miller.entity.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultVo<T> {

    private Integer code;
    private String message;

    private T data;

    public ResultVo() {
    }

    public ResultVo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultVo success(T t, String message) {
        return new ResultVo(1, message, t);
    }

    public static ResultVo success() {
        return ResultVo.success(null);
    }

    public static <T> ResultVo success(T t) {
        return ResultVo.success(t, "success");
    }

    public static ResultVo failed(String message) {
        return new ResultVo(-1, message, null);
    }

    public static ResultVo failed(Integer code, String message) {
        return new ResultVo(code, message, null);
    }

}
