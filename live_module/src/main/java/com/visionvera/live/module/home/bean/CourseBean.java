package com.visionvera.live.module.home.bean;

import java.io.Serializable;

/**
 * @Desc 课程实体类
 *
 * @Author yemh
 * @Date 2019-07-16 10:57
 *
 */
public class CourseBean implements Serializable {
    private int courseId;
    private String courseName;
    private int courseDeptId;
    private String courseDeptName;
    private int courseTypeId;
    private String courseTypeName;
    private String description;
    private int expertId;
    private String expertName;
    private String masterPicUrl;
    private int hotStatus;
    private boolean isCollectCourse;
    private boolean isCollectExpert;
    private int playModel;
    private String videoUrl;
    public String startTime = "";
    public String endTime = "";
    private String duration;
    private String expertHospital;
    private int liveStatus = -1;//直播状态：0未开始，1直播中，2已结束（录播为-1）
    private int courseTypeType;//1.课程类 2.会议类

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseDeptId() {
        return courseDeptId;
    }

    public void setCourseDeptId(int courseDeptId) {
        this.courseDeptId = courseDeptId;
    }

    public String getCourseDeptName() {
        return courseDeptName;
    }

    public void setCourseDeptName(String courseDeptName) {
        this.courseDeptName = courseDeptName;
    }

    public int getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(int courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getCourseTypeName() {
        return courseTypeName;
    }

    public void setCourseTypeName(String courseTypeName) {
        this.courseTypeName = courseTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getMasterPicUrl() {
        return masterPicUrl;
    }

    public void setMasterPicUrl(String masterPicUrl) {
        this.masterPicUrl = masterPicUrl;
    }

    public int getHotStatus() {
        return hotStatus;
    }

    public void setHotStatus(int hotStatus) {
        this.hotStatus = hotStatus;
    }

    public boolean isCollectCourse() {
        return isCollectCourse;
    }

    public void setCollectCourse(boolean collectCourse) {
        isCollectCourse = collectCourse;
    }

    public boolean isCollectExpert() {
        return isCollectExpert;
    }

    public void setCollectExpert(boolean collectExpert) {
        isCollectExpert = collectExpert;
    }

    public int getPlayModel() {
        return playModel;
    }

    public void setPlayModel(int playModel) {
        this.playModel = playModel;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExpertHospital() {
        return expertHospital;
    }

    public void setExpertHospital(String expertHospital) {
        this.expertHospital = expertHospital;
    }

    public int getCourseTypeType() {
        return courseTypeType;
    }

    public void setCourseTypeType(int courseTypeType) {
        this.courseTypeType = courseTypeType;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }
}
