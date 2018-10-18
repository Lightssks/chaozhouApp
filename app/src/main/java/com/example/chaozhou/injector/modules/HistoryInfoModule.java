package com.example.chaozhou.injector.modules;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.history.detail.HistoryInfoActivity;
import com.example.chaozhou.module.history.detail.HistoryInfoPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryInfoModule {

    private String hisssthhId;
    private HistoryInfoActivity mView;

    public HistoryInfoModule(String hisssthhId,HistoryInfoActivity mView) {
        this.hisssthhId = hisssthhId;
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public IBasePresenter providePresenter() {
        return new HistoryInfoPresenter(hisssthhId,mView);
    }
}
