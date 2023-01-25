package com.ashadujjaman.cregadmin.Chat;

public class ClassChatList {
    String userName, lastMessage, sentTime, userId, adminId;

    public ClassChatList(){}
    public ClassChatList(String userName, String lastMessage, String sentTime, String userId) {
        this.userName = userName;
        this.lastMessage = lastMessage;
        this.sentTime = sentTime;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
