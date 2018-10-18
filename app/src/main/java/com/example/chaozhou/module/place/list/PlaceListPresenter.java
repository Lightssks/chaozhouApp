package com.example.chaozhou.module.place.list;

import com.example.chaozhou.api.RetrofitService;
import com.example.chaozhou.api.bean.PlaceListInfo;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

public class PlaceListPresenter implements IBasePresenter {

    private IPlaceListView mView;

    private int mPage = 1;
    private int mCount = 10;

    public PlaceListPresenter(PlaceListFragment mPlaceListView) {
        mView = mPlaceListView;
    }

    @Override
    public void getData(final boolean isRefresh) {
            ReadService.getPlaceList(mPage,mCount)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .subscribe(new Subscriber<List<PlaceListInfo>>() {
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
                    public void onNext(List<PlaceListInfo> placeListInfos) {
                        mView.loadData(placeListInfos);
                        mPage++;
                    }
                });

    }

    @Override
    public void getMoreData() {
        ReadService.getPlaceList(mPage,mCount)
                .subscribe(new Subscriber<List<PlaceListInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(List<PlaceListInfo> placeListInfos) {
                        mView.loadMoreData(placeListInfos);
                        mPage++;
                    }
                });
    }
}
