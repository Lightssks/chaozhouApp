package com.example.chaozhou.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.adapter.ShopsAdapter;
import com.example.chaozhou.api.bean.ShopInfo;
import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.goods.detail.GoodsInfoActivity;
import com.example.chaozhou.module.goods.detail.GoodsInfoPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class GoodsInfoModule {

    private String spessthhId;
    private GoodsInfoActivity mView;
    private int layoutResId;
    private List<ShopInfo> data;

    public GoodsInfoModule(String spessthhId, GoodsInfoActivity mView,int layoutResId,List<ShopInfo> data) {
        this.spessthhId = spessthhId;
        this.mView = mView;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new GoodsInfoPresenter(spessthhId,mView);
    }

    @PerActivity
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new ShopsAdapter(layoutResId,data);
    }
}
