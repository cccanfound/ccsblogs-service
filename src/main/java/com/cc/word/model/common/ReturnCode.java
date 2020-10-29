package com.cc.word.model.common;

/**
 * 接口返回编码及描述
 *
 * @author lhj
 */
public enum ReturnCode {
    /**
     * 0:调用成功
     */
    success("0", "调用成功"),
    /**
     * E1001:参数缺失
     */
    param_miss("E1001", "参数缺失"),
    /**
     * E1002:传入信息不存在
     */
    info_not_exist("E1002", "传入信息不存在！"),
    /**
     * E1003:传入信息不匹配
     */
    info_not_match("E1003", "传入信息不匹配！"),
    /**
     * E1004:传入参数不合法
     */
    param_illegal("E1004", "传入参数不合法!"),
    /**
     * E1005:信息已存在
     */
    info_existed("E1005", "信息已存在!"),
    /**
     * E1006:短时间内重复提交
     */
    repeated_submit("E1006", "短时间内重复提交!"),

    /**
     * E1007:第三方接口调用失败
     */
    fail("E1007", "第三方接口调用失败"),
    /**
     * E999:调用失败
     */
    error("E400", "接口异常");


    public String returnMsg;
    public String returnCode;

    private ReturnCode(String returnCode, String returnMsg) {
        this.returnMsg = returnMsg;
        this.returnCode = returnCode;
    }

}
