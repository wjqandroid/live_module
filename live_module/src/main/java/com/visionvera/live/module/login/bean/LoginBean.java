package com.visionvera.live.module.login.bean;

import java.io.Serializable;

/**
 * @Desc 登录实体类
 *
 * @Author yemh
 * @Date 2019-07-13 17:40
 *
 */
public class LoginBean implements Serializable {
    private String deptName;
    private String worked;
    private String pictureUrl;
    private int sex;
    private String mobile;
    private String nickname;
    private int deptId;
    private String title;
    private int userId;
    private String token;
    private int chatterId;
    private String hospitalName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorked() {
        return worked;
    }

    public void setWorked(String worked) {
        this.worked = worked;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getChatterId() {
        return chatterId;
    }

    public void setChatterId(int chatterId) {
        this.chatterId = chatterId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "deptName='" + deptName + '\'' +
                ", worked='" + worked + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", sex=" + sex +
                ", mobile='" + mobile + '\'' +
                ", nickname='" + nickname + '\'' +
                ", deptId=" + deptId +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", chatterId=" + chatterId +
                ", hospitalName='" + hospitalName + '\'' +
                '}';
    }
}
