package com.cc.word.utils;

public class JsonData {
    //状态码 0 成功 1处理中 -1失败
    private int code;
    private Object data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonData(){}

    public JsonData(int code,Object data){
        this.code=code;
        this.data=data;
    }
    public JsonData(int code,Object data,String msg){
        this.code=code;
        this.data=data;
        this.msg=msg;
    }
    public static JsonData buildSuccess(Object data){
        return new JsonData(0,data,"success");
    }
    public static JsonData buildSuccess(){
        return new JsonData(0,"success");
    }
    public static JsonData buildFail(String msg){
        return new JsonData(-1,"",msg);
    }
    public static JsonData buildError(String msg){
        return new JsonData(-2,"",msg);
    }

    public JsonData setSuccess(Object data){
        this.setCode(0);
        this.setData(data);
        this.setMsg("success");
        return this;
    }
    public JsonData setFail(String msg){
        this.setCode(-1);
        this.setMsg("msg");
        return this;
    }
    public JsonData setError(String msg){
        this.setCode(-2);
        this.setMsg("msg");
        return this;
    }
}
