package com.example.chaozhou.module.hotel.detail;

import com.example.chaozhou.api.bean.HotelInfo;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.IBasePresenter;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

public class HotelInfoPresenter implements IBasePresenter {

    private final String hotssthhId;
    private final IHotelInfoView mView;

    public HotelInfoPresenter(String hotssthhId, IHotelInfoView mView) {
        this.hotssthhId = hotssthhId;
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        ReadService.getHotelInfo(hotssthhId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribe(new Subscriber<HotelInfo>() {
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
                    public void onNext(HotelInfo hotelInfo) {
                        mView.loadData(hotelInfo);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
