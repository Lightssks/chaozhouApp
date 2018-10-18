package com.example.chaozhou.api.service;

import com.example.chaozhou.api.IReadApi;
import com.example.chaozhou.api.RetrofitService;
import com.example.chaozhou.api.bean.ContrastId;
import com.example.chaozhou.api.bean.GoodsInfo;
import com.example.chaozhou.api.bean.GoodsListInfo;
import com.example.chaozhou.api.bean.HistoryInfo;
import com.example.chaozhou.api.bean.HistoryListInfo;
import com.example.chaozhou.api.bean.HotelInfo;
import com.example.chaozhou.api.bean.HotelListInfo;
import com.example.chaozhou.api.bean.PlaceInfo;
import com.example.chaozhou.api.bean.PlaceListInfo;
import com.example.chaozhou.api.bean.SpecId;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReadService {

    private static IReadApi sReadService = RetrofitService.createReadService();

    public static Observable<List<HotelListInfo>> getHotelList(int page,int count) {
       return sReadService.getHotelList(page,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<HotelInfo> getHotelInfo(String hotssthhId) {
        return sReadService.getHotelInfo(hotssthhId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<GoodsListInfo>> getGoodsList(int page, int count) {
        return sReadService.getGoodsList(page,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<GoodsInfo> getGoodsInfo(String spessthhId) {
        return sReadService.getGoodsInfo(spessthhId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SpecId> getGoodsId(String shossthId) {
        return sReadService.getGoodsID(shossthId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ContrastId> getContrastId(String mid) {
        return sReadService.getContrastId(mid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> getShopName(String shossthhId) {
        return sReadService.getShopName(shossthhId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<PlaceListInfo>> getPlaceList(int page, int count) {
        return sReadService.getPlaceList(page,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<PlaceInfo> getPlaceInfo(String toussthhId) {
        return sReadService.getPlaceInfo(toussthhId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<HistoryListInfo>> getHistoryList(int page, int count) {
        return sReadService.getHistoryList(page,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<HistoryInfo> getHistoryInfo(String hisssthhId) {
        return sReadService.getHistoryInfo(hisssthhId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
