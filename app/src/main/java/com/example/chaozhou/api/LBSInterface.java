package com.example.chaozhou.api;

import android.app.Activity;

import com.amap.api.services.core.PoiItem;


/**
 * Created by Administrator on 2018/4/20 0020.
 */

public interface LBSInterface {

    //传入经纬度，店名，具体地址，默认为广东省，潮州市（可添加）
    public PoiItem getPoiItemParams(Activity activity, String latitude, String longitude, String title, String address);

}
