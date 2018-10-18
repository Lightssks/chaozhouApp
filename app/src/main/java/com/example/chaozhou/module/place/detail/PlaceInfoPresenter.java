package com.example.chaozhou.module.place.detail;

import com.example.chaozhou.api.bean.PlaceInfo;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.IBasePresenter;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

public class PlaceInfoPresenter implements IBasePresenter {

    private final String toussthhId;
    private final IPlaceInfoView mView;

    public PlaceInfoPresenter(String toussthhId, IPlaceInfoView mView) {
        this.toussthhId = toussthhId;
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        ReadService.getPlaceInfo(toussthhId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribe(new Subscriber<PlaceInfo>() {
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
                    public void onNext(PlaceInfo placeInfo) {
                        mView.loadData(placeInfo);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
