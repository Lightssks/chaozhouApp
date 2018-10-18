package com.example.chaozhou.module.share;

import java.util.List;

/**
 * Created by dee on 15/6/29.
 */
public class Topic {
//    private String userImagePath;
//    private Long shauid;
//    private String userName;
//    private String shatext ;
//    private String good_sum;
//    private List<String> imgaes;
//    private String time;
    private String uname;
    private String head;
    private String shassthhId;
    private Long shauid;
    private int shahit;
    private String shatime;
    private String  shatext;
    private List<String> imgs;

    public Topic(Long uid, String shassthhId,String userImagePath, String userName){
        this.shauid = uid;
        this.shassthhId = shassthhId;
        this.head = userImagePath;
        this.uname = userName;
    }

    public Topic(Long id,String userImagePath, String userName, String shatext, List<String> imgaes, String time, int good_sum){
        this.shauid = id;
        this.uname = userName;
        this.shatext = shatext;
        this.imgs = imgaes;
        this.shatime = time;
        this.shahit = good_sum;
    }

    public Topic(String userImagePath, String userName, String shatext,List<String> imgaes, String time, int good_sum) {
        this.head = userImagePath;
        this.uname = userName;
        this.shatext = shatext;
        this.imgs = imgaes;
        this.shatime = time;
        this.shahit = good_sum;
    }

    public Topic (Topic tic){
        this.head = tic.head;
        this.uname = tic.uname;
        this.shassthhId = tic.shassthhId;
        this.shauid = tic.shauid;
        this.shatext = tic.shatext;
        this.imgs = tic.imgs;
        this.shatime = tic.shatime;
        this.shahit = tic.shahit;

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

    public String getShassthhId() {
        return shassthhId;
    }

    public void setShassthhId(String shassthhId) {
        this.shassthhId = shassthhId;
    }

    public Long getShauid() {
        return shauid;
    }

    public void setShauid(Long shauid) {
        this.shauid = shauid;
    }

    public int getShahit() {
        return shahit;
    }

    public void setShahit(int shahit) {
        this.shahit = shahit;
    }

    public String getShatime() {
        return shatime;
    }

    public void setShatime(String shatime) {
        this.shatime = shatime;
    }

    public String getShatext() {
        return shatext;
    }

    public void setShatext(String shatext) {
        this.shatext = shatext;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imas) {
        this.imgs = imas;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "uname='" + uname + '\'' +
                ", head='" + head + '\'' +
                ", shassthhId='" + shassthhId + '\'' +
                ", shauid=" + shauid +
                ", shahit='" + shahit + '\'' +
                ", shatime='" + shatime + '\'' +
                ", shatext='" + shatext + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}
