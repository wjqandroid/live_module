package com.visionvera.live.module.home.bean;

/**
 * @Desc 聊天人实体类
 *
 * @Author yemh
 * @Date 2019-07-12 11:04
 */
public class ChatterBean {

    /**
     * id : 1
     * name : 聊天者 1
     * status : 1
     * createTime : 1547000644000
     * updateTime : 1548140017000
     * enabled : true
     * loginName : chatter_1
     * password : null
     * systemId : 1
     */

    private int id;
    private String name;
    private int status;
    private long createTime;
    private long updateTime;
    private boolean enabled;
    private String loginName;
    private String password;
    private int systemId;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }
}
