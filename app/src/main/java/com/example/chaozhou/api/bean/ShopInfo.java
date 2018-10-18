package com.example.chaozhou.api.bean;

public class ShopInfo {

    private String shossthhId;
    private String shoname;
    private String shotype;
    private String shophone;
    private String shopicture;
    private MapInfo map;

    public String getShossthhId() {
        return shossthhId;
    }

    public void setShossthhId(String shossthhId) {
        this.shossthhId = shossthhId;
    }

    public String getShoname() {
        return shoname;
    }

    public void setShoname(String shoname) {
        this.shoname = shoname;
    }

    public String getShotype() {
        return shotype;
    }

    public void setShotype(String shotype) {
        this.shotype = shotype;
    }

    public String getShophone() {
        return shophone;
    }

    public void setShophone(String shophone) {
        this.shophone = shophone;
    }

    public String getShopicture() {
        return shopicture;
    }

    public void setShopicture(String shopicture) {
        this.shopicture = shopicture;
    }

    public MapInfo getMap() {
        return map;
    }

    public void setMap(MapInfo map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "shossthhId='" + shossthhId + '\'' +
                ", shoname='" + shoname + '\'' +
                ", shotype='" + shotype + '\'' +
                ", shophone='" + shophone + '\'' +
                ", shopicture='" + shopicture + '\'' +
                ", map=" + map +
                '}';
    }
}
