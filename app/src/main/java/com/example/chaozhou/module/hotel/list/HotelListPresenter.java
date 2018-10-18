package com.example.chaozhou.module.hotel.list;

import com.example.chaozhou.api.bean.HotelListInfo;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

public class HotelListPresenter implements IBasePresenter {

    private IHotelListView mView;

    private int mPage = 1;
    private int mCount = 10;

    public HotelListPresenter(HotelListFragment mHotelListView) {
        this.mView = mHotelListView;
    }

    @Override
    public void getData(final boolean isRefresh) {
        ReadService.getHotelList(mPage,mCount)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .subscribe(new Subscriber<List<HotelListInfo>>() {
                    @Override
                    public void onCompleted() {
                        Logger.w("onCompleted " + isRefresh);
                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString() + " " + isRefresh);
                        if (isRefresh) {
                            mView.finishRefresh();
                            // 可以提示对应的信息，但不更新界面
                            ToastUtils.showToast("刷新失败");
                        } else {
                            mView.showNetError();
                        }
                    }

                    @Override
                    public void onNext(List<HotelListInfo> hotelListInfos) {
                        mView.loadData(hotelListInfos);
                        mPage++;
                    }
                });
    }

    @Override
    public void getMoreData() {
        ReadService.getHotelList(mPage,mCount)
                .subscribe(new Subscriber<List<HotelListInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(List<HotelListInfo> hotelListInfos) {
                        mView.loadMoreData(hotelListInfos);
                        mPage++;
                    }
                });
    }
}
