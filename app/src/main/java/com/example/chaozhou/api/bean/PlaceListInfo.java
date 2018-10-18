package com.example.chaozhou.api.bean;

import java.util.List;

public class PlaceListInfo {

    private String toussthhId;
    private List<String> imgs;
    private String toudescribe;
    private String toutitle;

    public String getToussthhId() {
        return toussthhId;
    }

    public void setToussthhId(String toussthhId) {
        this.toussthhId = toussthhId;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getToudescribe() {
        return toudescribe;
    }

    public void setToudescribe(String toudescribe) {
        this.toudescribe = toudescribe;
    }

    public String getToutitle() {
        return toutitle;
    }

    public void setToutitle(String toutitle) {
        this.toutitle = toutitle;
    }

    @Override
    public String toString() {
        return "PlaceListInfo{" +
                "toussthhId='" + toussthhId + '\'' +
                ", imgs=" + imgs +
                ", toudescribe='" + toudescribe + '\'' +
                ", toutitle='" + toutitle + '\'' +
                '}';
    }
}
