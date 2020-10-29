package com.cc.word.exception;

import org.springframework.stereotype.Component;


public class Cexception extends RuntimeException{

    private Integer code;
    private String message;
    public Cexception(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
