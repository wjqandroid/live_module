package com.visionvera.live.module.home.bean;

/**
 * @Desc 群成员实体类
 *
 * @Author yemh
 * @Date 2019-07-12 11:04
 */
public class GroupChatterBean {
    private int id;
    private int groupId;
    private int chatterId;
    private int roleId;
    private String name;
    private int status;
    private long createTime;
    private long enterTime;
    private long leaveTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(long enterTime) {
        this.enterTime = enterTime;
    }

    public long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(long leaveTime) {
        this.leaveTime = leaveTime;
    }
}
