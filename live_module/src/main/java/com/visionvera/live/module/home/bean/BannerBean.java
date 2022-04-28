package com.visionvera.live.module.home.bean;

/**
 * @Desc 首页轮播图实体类
 *
 * @Author yemh
 * @Date 2019-07-15 17:12
 *
 */
public class BannerBean {
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
    private String time;
    private String duration;
    private int courseTypeType;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getCourseTypeType() {
        return courseTypeType;
    }

    public void setCourseTypeType(int courseTypeType) {
        this.courseTypeType = courseTypeType;
    }
}
