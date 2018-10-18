package com.example.chaozhou.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.adapter.PlaceListAdapter;
import com.example.chaozhou.api.bean.PlaceListInfo;
import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.place.list.PlaceListFragment;
import com.example.chaozhou.module.place.list.PlaceListPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaceListModule {

    private final PlaceListFragment mPlaceListView;
    private int layoutResId;
    private List<PlaceListInfo> data;

    public PlaceListModule(PlaceListFragment mHistoryListView,int layoutResId,List<PlaceListInfo> data) {
        this.mPlaceListView = mHistoryListView;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new PlaceListPresenter(mPlaceListView);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new PlaceListAdapter(layoutResId,data);
    }
}
