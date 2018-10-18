package com.example.chaozhou.module.history.detail;

import com.example.chaozhou.api.bean.HistoryInfo;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.IBasePresenter;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

public class HistoryInfoPresenter implements IBasePresenter {

    private final String hisssthhId;
    private final IHistoryInfoView mView;

    public HistoryInfoPresenter(String hisssthhId, IHistoryInfoView mView) {
        this.hisssthhId = hisssthhId;
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        ReadService.getHistoryInfo(hisssthhId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribe(new Subscriber<HistoryInfo>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(HistoryInfo historyInfo) {
                        mView.loadData(historyInfo);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
