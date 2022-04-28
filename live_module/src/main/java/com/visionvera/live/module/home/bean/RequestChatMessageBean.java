package com.visionvera.live.module.home.bean;

/**
 * @Desc 请求历史互动消息实体类
 *
 * @Author yemh
 * @Date 2019-08-02 09:52
 *
 */
public class RequestChatMessageBean {

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
        private int systemId;
        private int receiverId;
        private int receiverType;

        public int getSystemId() {
            return systemId;
        }

        public void setSystemId(int systemId) {
            this.systemId = systemId;
        }

        public int getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(int receiverId) {
            this.receiverId = receiverId;
        }

        public int getReceiverType() {
            return receiverType;
        }

        public void setReceiverType(int receiverType) {
            this.receiverType = receiverType;
        }
    }
}
