package com.example.chaozhou.api.bean;

import java.util.Date;

public class CommentInfo {

    private String uname;
    private String head;
    private String cid;
    private String cuid;
    private Date ctime;
    private String ctext;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "uname='" + uname + '\'' +
                ", head='" + head + '\'' +
                ", cid='" + cid + '\'' +
                ", cuid='" + cuid + '\'' +
                ", ctime=" + ctime +
                ", ctext='" + ctext + '\'' +
                '}';
    }
}
