package com.example.chaozhou.api.bean;

import java.util.Date;

public class Message {
    private String sentId;
    private String shareId;
    private String comments;

    private String head;
    private String uname;
    private Long uid;

    private Date time;


    public Message( String imags, String uname,  String content, Date time) {
        this.head = imags;
        this.uname = uname;
        this.comments = content;
        this.time = time;

    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getSentId() {
        return sentId;
    }

    public void setSentId(String sentId) {
        this.sentId = sentId;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sentId='" + sentId + '\'' +
                ", shareId='" + shareId + '\'' +
                ", comments='" + comments + '\'' +
                ", head='" + head + '\'' +
                ", uname='" + uname + '\'' +
                ", uid=" + uid +
                ", time='" + time + '\'' +
                '}';
    }
}
