package com.example.chaozhou.api;

import com.example.chaozhou.api.bean.AllMessage;
import com.example.chaozhou.api.bean.Commenter;
import com.example.chaozhou.api.bean.ShareListInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface comment {
    @POST("comments/subComments/")
    Observable<Integer> sendComment(@Query("cuid") Long id_share, @Query("cssthhId") String id_shared, @Query("ctext") String text);

//
//    @POST("comments/commentsList/{id}")
//    Observable<List<Commenter>> getComment(@Path("id")String id, @Query("cursor")String id_shared, @Query("pageSize")String text);

    @POST("comments/commentsList/{id}")
    Observable<List<Commenter>> getComment(@Path("id") String id);

    @GET("share/shareInfo/{id}")
    Observable<ShareListInfo> getoneShareInfo(@Path("id") String id);

    @POST("share/info/{id}")
    Observable<AllMessage> getUserMsg(@Path("id") Long id);

}
