package com.miller.controller.tools;

import lombok.Data;

@Data
public class ResultVO<T> {

    private Integer code;
    private String message;

    private T data;

    public ResultVO() {
    }

    public ResultVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultVO success(T t, String message) {
        return new ResultVO(1, message, t);
    }

    public static ResultVO success() {
        return ResultVO.success(null);
    }

    public static <T> ResultVO success(T t) {
        return ResultVO.success(t, "success");
    }

    public static ResultVO failed(String message) {
        return new ResultVO(-1, message, null);
    }

    public static ResultVO failed(Integer code, String message) {
        return new ResultVO(code, message, null);
    }

}
