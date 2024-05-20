package com.dream.exerciseSystem.utils;


import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@Getter
public class DataWrapper implements Serializable {
    private boolean flag;
    private String msg;
    private Object data;
    private Integer code;

    public DataWrapper(boolean flag) {
        this.flag = flag;
    }

    public DataWrapper flagBuilder(boolean flag) {
        this.flag = flag;
        return this;
    }

    public DataWrapper msgBuilder(String msg) {
        this.msg = msg;
        return this;
    }

    public DataWrapper dataBuilder(Object data) {
        this.data = data;
        return this;
    }

    public DataWrapper codeBuilder(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "DataWrapper{" +
                "flag=" + flag +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
