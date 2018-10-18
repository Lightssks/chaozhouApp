package com.example.chaozhou.api.bean;

public class MapInfo {

    private String mid;
    private String marea;
    private String mlongitude;
    private String mlatitude;
    private String mroad;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMarea() {
        return marea;
    }

    public void setMarea(String marea) {
        this.marea = marea;
    }

    public String getMlongitude() {
        return mlongitude;
    }

    public void setMlongitude(String mlongitude) {
        this.mlongitude = mlongitude;
    }

    public String getMlatitude() {
        return mlatitude;
    }

    public void setMlatitude(String mlatitude) {
        this.mlatitude = mlatitude;
    }

    public String getMroad() {
        return mroad;
    }

    public void setMroad(String mroad) {
        this.mroad = mroad;
    }

    @Override
    public String toString() {
        return "MapInfo{" +
                "mid='" + mid + '\'' +
                ", marea='" + marea + '\'' +
                ", mlongitude='" + mlongitude + '\'' +
                ", mlatitude='" + mlatitude + '\'' +
                ", mroad='" + mroad + '\'' +
                '}';
    }
}
