package com.visionvera.live.module.home.bean;

import java.util.List;

/**
 * @Desc 日程安排实体类(1)
 * @Author yemh
 * @Date 2019-07-12 11:04
 */
public class CalendarListBean {
    private int meetingCalendarId;
    private String name;
    private String startDate;
    private String endDate;
    private List<MeetingExpertListBean> meetingExpertList;

    public int getMeetingCalendarId() {
        return meetingCalendarId;
    }

    public void setMeetingCalendarId(int meetingCalendarId) {
        this.meetingCalendarId = meetingCalendarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<MeetingExpertListBean> getMeetingExpertList() {
        return meetingExpertList;
    }

    public void setMeetingExpertList(List<MeetingExpertListBean> meetingExpertList) {
        this.meetingExpertList = meetingExpertList;
    }
}
