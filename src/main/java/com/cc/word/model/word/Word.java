package com.cc.word.model.word;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author liyc
 * @date 2020/10/3 22:05
 */
public class Word {
    private int id;
    private String content;
    private String announce;
    private String phonetic;
    private String wordType;
    private String explaination;

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", announce='" + announce + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", wordType='" + wordType + '\'' +
                ", explaination='" + explaination + '\'' +
                '}';
    }
}
