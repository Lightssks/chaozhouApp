package com.example.chaozhou.module.hotel.detail;

import com.example.chaozhou.api.bean.HotelInfo;
import com.example.chaozhou.module.base.IBaseView;

public interface IHotelInfoView extends IBaseView {

    /**
     * 显示数据
     * @param hotelInfo 酒店详情
     */
    void loadData(HotelInfo hotelInfo);
}
