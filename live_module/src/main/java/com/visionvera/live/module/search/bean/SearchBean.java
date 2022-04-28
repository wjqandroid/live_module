package com.visionvera.live.module.search.bean;

/**
 * @Desc 搜索实体类
 *
 * @Author yemh
 * @Date 2019/6/26 10:44
 *
 */
public class SearchBean {
    private int courseId;
    private String courseName;
    private int courseDeptId;
    private int courseTypeId;
    private String courseTypeName;
    private String masterPicUrl;
    private int hotStatus;
    private String expertName;
    private boolean isCollect;
    private int playModel;
    private String duration;
    private String videoUrl;
    private String time;

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

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public boolean isIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public int getPlayModel() {
        return playModel;
    }

    public void setPlayModel(int playModel) {
        this.playModel = playModel;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
}
