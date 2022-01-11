package com.cc.word.model.status;

import javax.validation.constraints.NotBlank;

/**
 * @author liyc
 * @date 2022/1/1 1:25
 * 检测指标数据类型
 */
public class StatusDefAddVo {
    @NotBlank(message = "名称不能为空")
    private String name;
    @NotBlank(message = "单位不能为空")
    private String unit;
    private String floor;
    private String ceiling;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCeiling() {
        return ceiling;
    }

    public void setCeiling(String ceiling) {
        this.ceiling = ceiling;
    }

    @Override
    public String toString() {
        return "StatusDefAddVo{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", floor='" + floor + '\'' +
                ", ceiling='" + ceiling + '\'' +
                '}';
    }
}
