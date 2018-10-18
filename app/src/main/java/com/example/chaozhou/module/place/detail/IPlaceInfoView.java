package com.example.chaozhou.module.place.detail;

import com.example.chaozhou.api.bean.PlaceInfo;
import com.example.chaozhou.module.base.IBaseView;

public interface IPlaceInfoView extends IBaseView{

    /**
     * 显示数据
     * @param placeInfo 景点详情
     */
    void loadData(PlaceInfo placeInfo);

}
