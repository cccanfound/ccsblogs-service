package com.cc.word.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class User {
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    private Date createTime;
    private String headImg;

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
