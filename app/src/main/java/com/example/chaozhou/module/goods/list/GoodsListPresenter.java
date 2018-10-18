package com.example.chaozhou.module.goods.list;

import com.example.chaozhou.api.bean.GoodsListInfo;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

public class GoodsListPresenter implements IBasePresenter {

    private IGoodsListView mView;

    private int mPage = 1;
    private int mCount = 10;

    public GoodsListPresenter(GoodsListFragment mGoodsListView) {
        mView = mGoodsListView;
    }

    @Override
    public void getData(final boolean isRefresh) {
        ReadService.getGoodsList(mPage,mCount)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .subscribe(new Subscriber<List<GoodsListInfo>>() {
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
                    public void onNext(List<GoodsListInfo> goodsListInfos) {
                        mView.loadData(goodsListInfos);
                        mPage++;
                    }
                });
    }

    @Override
    public void getMoreData() {
        ReadService.getGoodsList(mPage,mCount)
                .subscribe(new Subscriber<List<GoodsListInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(List<GoodsListInfo> goodsListInfos) {
                        mView.loadMoreData(goodsListInfos);
                        mPage++;
                    }
                });
    }
}
