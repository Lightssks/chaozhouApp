package com.example.chaozhou.api.bean;

import java.util.Arrays;

public class ShareResponse {
    private int status;
    private String shareUserid;
    private int id;
    private String  path[];
    private String shareTime;
    private String shareText;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShareUserid() {
        return shareUserid;
    }

    public void setShareUserid(String shareUserid) {
        this.shareUserid = shareUserid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    @Override
    public String toString() {
        return "ShareResponse{" +
                "status=" + status +
                ", shareUserid='" + shareUserid + '\'' +
                ", id=" + id +
                ", path=" + Arrays.toString(path) +
                ", shareTime='" + shareTime + '\'' +
                ", shareText='" + shareText + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
