package com.example.chaozhou.module.goods.detail;

import com.example.chaozhou.api.bean.GoodsInfo;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.IBasePresenter;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.functions.Action0;

public class GoodsInfoPresenter implements IBasePresenter {

    private final String spessthhId;
    private final IGoodsInfoView mView;

    public GoodsInfoPresenter(String spessthhId, IGoodsInfoView mView) {
        this.spessthhId = spessthhId;
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        ReadService.getGoodsInfo(spessthhId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribe(new Subscriber<GoodsInfo>() {
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
                    public void onNext(GoodsInfo goodsInfo) {
                        mView.loadData(goodsInfo);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
