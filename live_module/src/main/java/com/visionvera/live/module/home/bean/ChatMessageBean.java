package com.visionvera.live.module.home.bean;

import java.util.List;

/**
 * @Desc 聊天消息实体类
 *
 * @Author yemh
 * @Date 2019-07-12 11:04
 */
public class ChatMessageBean {

    private int id;
    private int typeId;
    private String content;
    private int status;
    private int systemId;
    private int senderId;
    private int groupId;
    private long createTime;
    private List<ReceiversBean> receivers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<ReceiversBean> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ReceiversBean> receivers) {
        this.receivers = receivers;
    }

    public static class ReceiversBean {
        /**
         * id : 646704
         * messageId : 1638173
         * receiverId : 101105
         * receiverType : 2
         * status : 1
         */

        private int id;
        private int messageId;
        private int receiverId;
        private int receiverType;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
