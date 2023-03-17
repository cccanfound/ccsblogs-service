package com.cc.word.model.music;

import java.util.Date;

/**
 * @author liyc
 * @date 2023/2/2 22:35
 */
public class Singer {
    //歌手id
    private int id;
    //歌手姓名
    private String singerName;
    //创建时间
    private Date createTime;
    //创建时间
    private Date editTime;
    //最后一次编辑者
    private int editUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public int getEditUserId() {
        return editUserId;
    }

    public void setEditUserId(int editUserId) {
        this.editUserId = editUserId;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "id=" + id +
                ", singerName='" + singerName + '\'' +
                ", createTime=" + createTime +
                ", editTime=" + editTime +
                ", editUserId=" + editUserId +
                '}';
    }
}
