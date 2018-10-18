package com.example.chaozhou.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.adapter.HotelListAdapter;
import com.example.chaozhou.api.bean.HotelListInfo;
import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.hotel.list.HotelListFragment;
import com.example.chaozhou.module.hotel.list.HotelListPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class HotelListModule {

    private final HotelListFragment mHotelListView;
    private int layoutResId;
    private List<HotelListInfo> data;

    public HotelListModule( HotelListFragment view,int layoutResId,List<HotelListInfo> data) {
        this.mHotelListView = view;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new HotelListPresenter(mHotelListView);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new HotelListAdapter(layoutResId,data);
    }
}
