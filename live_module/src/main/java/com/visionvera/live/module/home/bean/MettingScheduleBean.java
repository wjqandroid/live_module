package com.visionvera.live.module.home.bean;

import java.util.List;

/**
 * @Desc 日程安排实体类
 *
 * @Author yemh
 * @Date 2019-07-12 11:04
 */
public class MettingScheduleBean {

    private String calendarDate;
    private List<CalendarListBean> calendarList;

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
    }

    public List<CalendarListBean> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(List<CalendarListBean> calendarList) {
        this.calendarList = calendarList;
    }
}
