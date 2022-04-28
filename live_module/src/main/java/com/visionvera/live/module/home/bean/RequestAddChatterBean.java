package com.visionvera.live.module.home.bean;

/**
 * @Desc 请求加入群实体类
 *
 * @Author yemh
 * @Date 2019-08-02 09:52
 *
 */
public class RequestAddChatterBean {
    private int groupId;
    private int chatterId;
    private String name;
    private int roleId;
    private int status;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getChatterId() {
        return chatterId;
    }

    public void setChatterId(int chatterId) {
        this.chatterId = chatterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
