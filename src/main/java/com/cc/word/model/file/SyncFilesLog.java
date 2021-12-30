package com.cc.word.model.file;

import java.util.Date;

/**
 * @author liyc
 * @date 2021/10/9 17:58
 */
public class SyncFilesLog {
    private int id;
    private String type;
    private int total;
    private int success;
    private int fail;
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SyncFilesLog{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", total=" + total +
                ", success=" + success +
                ", fail=" + fail +
                ", time=" + time +
                '}';
    }
}
