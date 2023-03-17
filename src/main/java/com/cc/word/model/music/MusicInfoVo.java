package com.cc.word.model.music;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author liyc
 * @date 2023/2/5 1:15
 */
public class MusicInfoVo {
    private String id;
    @NotBlank(message = "歌曲名不能为空")
    private String musicName;
    @NotBlank(message = "歌手不能为空")
    private String singerId;
    private String describe;
    private String musicImg;
    private String musicUrl;
    private String lyricUrl;

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

    @Override
    public String toString() {
        return "MusicInfoVo{" +
                "id='" + id + '\'' +
                ", musicName='" + musicName + '\'' +
                ", singerId='" + singerId + '\'' +
                ", describe='" + describe + '\'' +
                ", musicImg='" + musicImg + '\'' +
                ", musicUrl='" + musicUrl + '\'' +
                ", lyricUrl='" + lyricUrl + '\'' +
                '}';
    }
}
