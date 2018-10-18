package com.example.chaozhou.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.adapter.GoodsListAdapter;
import com.example.chaozhou.api.bean.GoodsListInfo;
import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.goods.list.GoodsListFragment;
import com.example.chaozhou.module.goods.list.GoodsListPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class GoodsListModule {

    private final GoodsListFragment mGoodsListView;
    private int layoutResId;
    private List<GoodsListInfo> data;


    public GoodsListModule(GoodsListFragment mGoodsListView,int layoutResId,List<GoodsListInfo> data) {
        this.mGoodsListView = mGoodsListView;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new GoodsListPresenter(mGoodsListView);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new GoodsListAdapter(layoutResId,data);
    }

}
