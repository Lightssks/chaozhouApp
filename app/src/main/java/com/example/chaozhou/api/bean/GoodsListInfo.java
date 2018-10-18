package com.example.chaozhou.api.bean;

public class GoodsListInfo {

    private String spessthhId;
    private String spepicture;
    private String spedescribe;
    private String spetype;
    private String speprice;

    public String getSpessthhId() {
        return spessthhId;
    }

    public void setSpessthhId(String spessthhId) {
        this.spessthhId = spessthhId;
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

    public String getSpetype() {
        return spetype;
    }

    public void setSpetype(String spetype) {
        this.spetype = spetype;
    }

    public String getSpeprice() {
        return speprice;
    }

    public void setSpeprice(String speprice) {
        this.speprice = speprice;
    }

    @Override
    public String toString() {
        return "GoodsListInfo{" +
                "spessthhId='" + spessthhId + '\'' +
                ", spepicture='" + spepicture + '\'' +
                ", spedescribe='" + spedescribe + '\'' +
                ", spetype='" + spetype + '\'' +
                ", speprice='" + speprice + '\'' +
                '}';
    }
}
