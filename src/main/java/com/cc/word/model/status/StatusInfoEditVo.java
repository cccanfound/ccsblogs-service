package com.cc.word.model.status;

import javax.validation.constraints.NotBlank;

/**
 * @author liyc
 * @date 2022/1/1 1:25
 * 检测指标数据类型
 */
public class StatusInfoEditVo {
    private String id;
    @NotBlank(message = "指标类型不能为空")
    private String statusId;
    @NotBlank(message = "报告值不能为空")
    private String val;
    @NotBlank(message = "报告时间不能为空")
    private String createTime;
    @NotBlank(message = "检测地点不能为空")
    private String locationId;
    private String remark;
    @NotBlank(message = "是否异常不能为空")
    private String danger;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    @Override
    public String toString() {
        return "StatusInfoEditVo{" +
                "id='" + id + '\'' +
                ", statusId='" + statusId + '\'' +
                ", val='" + val + '\'' +
                ", createTime='" + createTime + '\'' +
                ", locationId='" + locationId + '\'' +
                ", remark='" + remark + '\'' +
                ", danger='" + danger + '\'' +
                '}';
    }
}
