package com.atguigu.exceptionhandler;

import lombok.Data;

@Data
public class GuliException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息

    public GuliException() {
    }

    public GuliException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
