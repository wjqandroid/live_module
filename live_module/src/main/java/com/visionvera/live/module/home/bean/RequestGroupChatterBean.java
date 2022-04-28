package com.visionvera.live.module.home.bean;

/**
 * @Desc 请求群成员实体类
 *
 * @Author yemh
 * @Date 2019-08-02 09:52
 *
 */
public class RequestGroupChatterBean {
    private WhereBean where;
    private int size;
    private int start;

    public WhereBean getWhere() {
        return where;
    }

    public void setWhere(WhereBean where) {
        this.where = where;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public static class WhereBean {
        /**
         * groupId : 100002
         */

        private int groupId;

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
    }
}
