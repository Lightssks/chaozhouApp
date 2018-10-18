package com.example.chaozhou.api.bean;
import com.example.chaozhou.local.table.User;

import org.greenrobot.greendao.annotation.Property;

/**
 * Created by yfj on 18-4-12.
 */

public class BaseInfo {

    /**
     * status : -1or0
     * data : {"mobile":"phone","password":"value","id":0}
     */
    private int status;

    @Property(nameInDb = "data")
    private User data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public User getUser() {
        return data;
    }

    public void setUser(User data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "result={" +
                "\"status\":" + status +
                ", \"user\":{" + data.toString() +
                '}';
    }
}
