package com.example.chaozhou.injector.modules;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.place.detail.PlaceInfoActivity;
import com.example.chaozhou.module.place.detail.PlaceInfoPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaceInfoModule {

    private String toussthhId;
    private PlaceInfoActivity mView;

    public PlaceInfoModule(String toussthhId,PlaceInfoActivity mView) {
        this.toussthhId = toussthhId;
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new PlaceInfoPresenter(toussthhId,mView);
    }
}
