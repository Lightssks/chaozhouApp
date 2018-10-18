package com.example.chaozhou.api.bean;

public class HotelListInfo {

    private String hotssthhId;
    private String hotname;
    private String hotphone;
    private String hotpicture;
    private String hotstate;
    private MapInfo map;

    public String getHotssthhId() {
        return hotssthhId;
    }

    public void setHotssthhId(String hotssthhId) {
        this.hotssthhId = hotssthhId;
    }

    public String getHotname() {
        return hotname;
    }

    public void setHotname(String hotname) {
        this.hotname = hotname;
    }

    public String getHotphone() {
        return hotphone;
    }

    public void setHotphone(String hotphone) {
        this.hotphone = hotphone;
    }

    public String getHotpicture() {
        return hotpicture;
    }

    public void setHotpicture(String hotpicture) {
        this.hotpicture = hotpicture;
    }

    public String getHotstate() {
        return hotstate;
    }

    public void setHotstate(String hotstate) {
        this.hotstate = hotstate;
    }

    public MapInfo getMap() {
        return map;
    }

    public void setMap(MapInfo map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "HotelListInfo{" +
                "hotssthhId='" + hotssthhId + '\'' +
                ", hotname='" + hotname + '\'' +
                ", hotphone='" + hotphone + '\'' +
                ", hotpicture='" + hotpicture + '\'' +
                ", hotstate='" + hotstate + '\'' +
                ", map=" + map +
                '}';
    }
}
