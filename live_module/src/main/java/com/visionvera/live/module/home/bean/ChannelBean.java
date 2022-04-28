package com.visionvera.live.module.home.bean;

/**
 * @Desc 课程分类实体类
 *
 * @Author yemh
 * @Date 2019-07-12 11:04
 */
public class ChannelBean {

    private int id;
    private String name;
    private int status;
    private int type;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
