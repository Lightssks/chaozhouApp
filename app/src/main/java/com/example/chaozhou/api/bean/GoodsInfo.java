package com.example.chaozhou.api.bean;

import java.util.List;

public class GoodsInfo {
    private String spessthhId;
    private String spename;
    private String spepicture;
    private String spedescribe;
    private List<ShopInfo> shop;

    public String getSpessthhId() {
        return spessthhId;
    }

    public void setSpessthhId(String spessthhId) {
        this.spessthhId = spessthhId;
    }

    public String getSpename() {
        return spename;
    }

    public void setSpename(String spename) {
        this.spename = spename;
    }

    public String getSpepicture() {
        return spepicture;
    }

    public void setSpepicture(String spepicture) {
        this.spepicture = spepicture;
    }

    public String getSpedescribe() {
        return spedescribe;
    }

    public void setSpedescribe(String spedescribe) {
        this.spedescribe = spedescribe;
    }

    public List<ShopInfo> getShop() {
        return shop;
    }

    public void setShop(List<ShopInfo> shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "spessthhId='" + spessthhId + '\'' +
                ", spename='" + spename + '\'' +
                ", spepicture='" + spepicture + '\'' +
                ", spedescribe='" + spedescribe + '\'' +
                ", shop=" + shop +
                '}';
    }
}
