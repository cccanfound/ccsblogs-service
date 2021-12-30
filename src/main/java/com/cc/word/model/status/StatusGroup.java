package com.cc.word.model.status;

/**
 * @author liyc
 * @date 2021/12/29 17:45
 */
public class StatusGroup {
    //指标分类id
    private int id;
    //指标分类名称
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StatusGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
