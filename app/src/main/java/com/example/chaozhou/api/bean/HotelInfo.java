package com.example.chaozhou.api.bean;

import java.util.List;

public class HotelInfo {

    private String hotssthhId;
    private String hotname;
    private MapInfo map;
    private List<RoomInfo> room;
    private List<String> devices;
    private String hotdescribe;
    private String hotpicture;

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

    public MapInfo getMap() {
        return map;
    }

    public void setMap(MapInfo map) {
        this.map = map;
    }

    public List<RoomInfo> getRoom() {
        return room;
    }

    public void setRoom(List<RoomInfo> room) {
        this.room = room;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public String getHotdescribe() {
        return hotdescribe;
    }

    public void setHotdescribe(String hotdescribe) {
        this.hotdescribe = hotdescribe;
    }

    public String getHotpicture() {
        return hotpicture;
    }

    public void setHotpicture(String hotpicture) {
        this.hotpicture = hotpicture;
    }

    @Override
    public String toString() {
        return "HotelInfo{" +
                "hotssthhId='" + hotssthhId + '\'' +
                ", hotname='" + hotname + '\'' +
                ", map=" + map +
                ", room=" + room +
                ", devices=" + devices +
                ", hotdescribe='" + hotdescribe + '\'' +
                ", hotpicture='" + hotpicture + '\'' +
                '}';
    }
}
