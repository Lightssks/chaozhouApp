package com.example.chaozhou.api.bean;

import android.os.Parcel;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class PoiItemBean extends PoiItem {
    private String mid ;  //店ID
    private LatLonPoint latLonPoint; //店位置
    private String title; //店名
    private  String snippy; //店地址
    public PoiItemBean(String mid, LatLonPoint latLonPoint, String title, String snippy) {
        super(mid,latLonPoint,title,snippy);
        this.mid = mid;
        this.latLonPoint=latLonPoint;
        this.title=title;
        this.snippy=snippy;

    }

    protected PoiItemBean(Parcel parcel) {
        super(parcel);
    }
}
