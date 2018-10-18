package com.example.chaozhou.api.bean;

import java.util.List;

public class AllMessage {
    private int status;
    private List<Message> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Message> getMsg() {
        return data;
    }

    public void setMsg(List<Message> msg) {
        this.data = msg;
    }

    @Override
    public String toString() {
        return "AllMessage{" +
                "status=" + status +
                ", msg=" + data +
                '}';
    }
}
