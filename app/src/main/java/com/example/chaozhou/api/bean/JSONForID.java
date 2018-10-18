package com.example.chaozhou.api.bean;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class JSONForID {
    private  String mId;
    private  String contrastID;

    public JSONForID(String mId, String contrastID) {
        this.mId = mId;
        this.contrastID = contrastID;
    }

    public String getContrastID() {
        return contrastID;
    }

    public void setContrastID(String contrastID) {
        this.contrastID = contrastID;
    }

    public String getmId() {
        return mId;
    }

    @Override
    public String toString() {
        return "JSONForID{" +
                "mId=" + mId +
                ", contrastID=" + contrastID +
                '}';
    }

    public void setmId(String mId) {
        this.mId = mId;
    }


}
