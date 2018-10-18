package com.example.chaozhou.api;

import com.example.chaozhou.api.bean.CommentInfo;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

import static com.example.chaozhou.api.RetrofitService.CACHE_CONTROL_NETWORK;

public interface ICommentApi {

    @GET("comments/commentsList/{id}")
    Observable<List<CommentInfo>> getCommentsList(@Path("id") String id);

    @FormUrlEncoded
    @POST("comments/subComments")
    Observable<Integer> subComments(@Field("cuid") long cuid,
                                   @Field("cssthhId") String cssthhId,
                                   @Field("ctext") String ctext);

}
