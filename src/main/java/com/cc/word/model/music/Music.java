package com.cc.word.model.music;

import java.util.Date;

/**
 * @author liyc
 * @date 2023/2/5 1:03
 */
public class Music {
    private String id;
    private String musicName;
    private String singerId;
    private String describe;
    private String musicImg;
    private Date createTime;
    private Date editTime;
    private String editUserId;
    private String musicUrl;
    private String lyricUrl;
    private String singerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getMusicImg() {
        return musicImg;
    }

    public void setMusicImg(String musicImg) {
        this.musicImg = musicImg;
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

    public String getEditUserId() {
        return editUserId;
    }

    public void setEditUserId(String editUserId) {
        this.editUserId = editUserId;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getLyricUrl() {
        return lyricUrl;
    }

    public void setLyricUrl(String lyricUrl) {
        this.lyricUrl = lyricUrl;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id='" + id + '\'' +
                ", musicName='" + musicName + '\'' +
                ", singerId='" + singerId + '\'' +
                ", describe='" + describe + '\'' +
                ", musicImg='" + musicImg + '\'' +
                ", createTime=" + createTime +
                ", editTime=" + editTime +
                ", editUserId='" + editUserId + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", lyricUrl='" + lyricUrl + '\'' +
                ", singerName='" + singerName + '\'' +
                '}';
    }
}
