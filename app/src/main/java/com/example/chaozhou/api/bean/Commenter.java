package com.example.chaozhou.api.bean;

public class Commenter {
    private String uname;
    private String head;
    private String cid;
    private String cuid;
    private String ctime;
    private String cssthhId;
    private String ctext;

    public Commenter(String uname,String ctextc){
        this.uname = uname;
        this.ctext = ctextc;
    }

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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCssthhId() {
        return cssthhId;
    }

    public void setCssthhId(String cssthhId) {
        this.cssthhId = cssthhId;
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext;
    }

    @Override
    public String toString() {
        return "Commenter{" +
                "uname='" + uname + '\'' +
                ", head='" + head + '\'' +
                ", cid='" + cid + '\'' +
                ", cuid='" + cuid + '\'' +
                ", ctime='" + ctime + '\'' +
                ", cssthhId='" + cssthhId + '\'' +
                ", ctext='" + ctext + '\'' +
                '}';
    }
}
