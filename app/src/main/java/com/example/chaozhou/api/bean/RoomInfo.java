package com.example.chaozhou.api.bean;

public class RoomInfo {
    private String roomName;
    private String roomType;
    private String satisfaction;
    private String roomImg;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(String roomImg) {
        this.roomImg = roomImg;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "roomName='" + roomName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", satisfaction='" + satisfaction + '\'' +
                ", roomImg='" + roomImg + '\'' +
                '}';
    }
}
