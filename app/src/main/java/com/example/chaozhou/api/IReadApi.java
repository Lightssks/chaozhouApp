package com.example.chaozhou.api;

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

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.example.chaozhou.api.RetrofitService.CACHE_CONTROL_NETWORK;

public interface IReadApi {

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("live/hotelList")
    Observable<List<HotelListInfo>> getHotelList(@Query("page")int page,@Query("count")int count);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("live/hotelInfo/{hotssthhId}")
    Observable<HotelInfo> getHotelInfo(@Path("hotssthhId")String hotssthhId);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("spec/specList")
    Observable<List<GoodsListInfo>> getGoodsList(@Query("page")int page, @Query("count")int count);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("spec/specInfo/{spessthhId}")
    Observable<GoodsInfo> getGoodsInfo(@Path("spessthhId")String spessthhId);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("spec/specByShopid/{id}")
    Observable<SpecId> getGoodsID(@Path("id")String id);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("map/info/{mid}")
    Observable<ContrastId> getContrastId(@Path("mid")String mid);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("shop/nameById/{id}")
    Observable<String> getShopName(@Path("id")String shossthhId);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("tour/tourlList")
    Observable<List<PlaceListInfo>> getPlaceList(@Query("page")int page, @Query("count")int count);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("tour/tourInfo/{toussthhId}")
    Observable<PlaceInfo> getPlaceInfo(@Path("toussthhId")String toussthhId);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("history/historyList")
    Observable<List<HistoryListInfo>> getHistoryList(@Query("page")int page, @Query("count")int count);

    @Headers(CACHE_CONTROL_NETWORK)
    @GET("history/historyInfo/{hisssthhId}")
    Observable<HistoryInfo> getHistoryInfo(@Path("hisssthhId")String hisssthhId);

}
