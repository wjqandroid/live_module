package com.visionvera.live.module.home.bean;

/**
 * @Desc 聊天群实体类
 *
 * @Author yemh
 * @Date 2019-07-12 11:04
 */
public class GroupIdBean {
    private int id;
    private String name;
    private int creatorId;
    private int adminId;
    private long createTime;
    private long updateTime;
    private boolean enabled;
    private int systemId;
    private String businessId;
    private int groupChatters;

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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public int getGroupChatters() {
        return groupChatters;
    }

    public void setGroupChatters(int groupChatters) {
        this.groupChatters = groupChatters;
    }
}
