package com.visionvera.live.module.home.bean;

import java.util.List;

/**
 * @Desc 发送消息实体类
 *
 * @Author yemh
 * @Date 2019-07-15 17:12
 *
 */
public class SendBean {
    private int typeId;
    private int systemId;
    private int senderId;
    private String content;
    private List<ReceiversBean> receivers;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReceiversBean> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ReceiversBean> receivers) {
        this.receivers = receivers;
    }

    public static class ReceiversBean {
        /**
         * receiverId : 1
         * receiverType : 1
         */

        private int receiverId;
        private int receiverType;

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
