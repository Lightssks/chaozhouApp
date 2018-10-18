package com.example.chaozhou.api.bean;

import java.util.List;

public class HistoryInfo {

    private String hisssthhId;
    private String histitle;
    private String hisdescribe;
    private List<String> imgs;

    public String getHisssthhId() {
        return hisssthhId;
    }

    public void setHisssthhId(String hisssthhId) {
        this.hisssthhId = hisssthhId;
    }

    public String getHistitle() {
        return histitle;
    }

    public void setHistitle(String histitle) {
        this.histitle = histitle;
    }

    public String getHisdescribe() {
        return hisdescribe;
    }

    public void setHisdescribe(String hisdescribe) {
        this.hisdescribe = hisdescribe;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "HistoryInfo{" +
                "hisssthhId='" + hisssthhId + '\'' +
                ", histitle='" + histitle + '\'' +
                ", hisdescribe='" + hisdescribe + '\'' +
                ", imgs=" + imgs +
                '}';
    }
}
