package com.cc.word.model.essay;

import java.util.Date;

public class Essay {
    private int id;
    private String title;
    private int authorId;
    private Date createTime;
    private String content;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Essay{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
