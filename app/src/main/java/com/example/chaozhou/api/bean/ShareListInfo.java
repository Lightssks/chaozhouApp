package com.example.chaozhou.api.bean;

import com.example.chaozhou.module.share.Topic;

public class ShareListInfo {
    private int status;
    private Topic data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Topic getData() {
        return data;
    }

    public void setData(Topic data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShareListInfo{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
