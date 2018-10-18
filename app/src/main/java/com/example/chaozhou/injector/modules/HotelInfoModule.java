package com.example.chaozhou.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.adapter.HotelListAdapter;
import com.example.chaozhou.adapter.HotelRoomAdapter;
import com.example.chaozhou.api.bean.HotelInfo;
import com.example.chaozhou.api.bean.HotelListInfo;
import com.example.chaozhou.api.bean.RoomInfo;
import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.hotel.detail.HotelInfoActivity;
import com.example.chaozhou.module.hotel.detail.HotelInfoPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class HotelInfoModule {

    private String hotssthhId;
    private HotelInfoActivity mView;
    private int layoutResId;
    private List<RoomInfo> data;

    public HotelInfoModule(String hotssthhId, HotelInfoActivity mView,int layoutResId,List<RoomInfo> data) {
        this.hotssthhId = hotssthhId;
        this.mView = mView;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new HotelInfoPresenter(hotssthhId,mView);
    }

    @PerActivity
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new HotelRoomAdapter(layoutResId,data);
    }
}
