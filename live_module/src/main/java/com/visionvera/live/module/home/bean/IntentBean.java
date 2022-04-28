package com.visionvera.live.module.home.bean;

import java.io.Serializable;

public class IntentBean implements Serializable {
    public String url = "";
    public int courseId = 0;
    public String title = "";
    public String webUrl = "";
    public String imageUrl = "";
    public String startTime = "";
    public String endTime = "";
    public int playModel = 2;//1.录播 2.直播
    public int type = 1;//1.课程类 2.会议类
    public boolean isCollect;
    public String hospital = "";
    public String description = "";
    public String duration = "0";
    public int expertId = 0;
    public int liveStatus = -1;//直播状态：0未开始，1直播中，2已结束（录播为-1）

    @Override
    public String toString() {
        return "IntentBean{" +
                "url='" + url + '\'' +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", playModel=" + playModel +
                ", type=" + type +
                ", isCollect=" + isCollect +
                ", hospital='" + hospital + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", expertId='" + expertId + '\'' +
                ", liveStatus='" + liveStatus + '\'' +
                '}';
    }
}
