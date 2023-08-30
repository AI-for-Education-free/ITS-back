package com.dream.exerciseSystem.service.utils;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum CodeMsg {
    SUCCESS(200, "请求成功！"),
    SERVER_ERROR(500, "服务端异常");

    private Integer code;
    private String msg;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
