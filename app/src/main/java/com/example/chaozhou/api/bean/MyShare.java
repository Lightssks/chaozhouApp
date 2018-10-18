package com.example.chaozhou.api.bean;

import com.example.chaozhou.module.share.Topic;

import java.util.List;

public class MyShare {
    private int status;
    private List<Topic> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Topic> getData() {
        return data;
    }

    public void setData(List<Topic> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MyShare{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
