package com.example.chaozhou.api;

import com.example.chaozhou.api.bean.BaseResponse;
import com.example.chaozhou.api.bean.MyShare;
import com.example.chaozhou.api.bean.ShareResponse;
import com.example.chaozhou.module.share.Topic;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface IUploadApi {

    /**
     * 上传头像
     * @param file
     * @return
     */
    @Multipart
    @POST("/img/headUpload/{phoneNumber}")
    Observable<BaseResponse> uploadFileWithRequestBody(@Part MultipartBody.Part file,@Path("phoneNumber") String phoneNumber);

//    @Multipart
//    @POST("img/submitShare/")
//    Observable<ShareResponse> shareinfo(@Part List<MultipartBody.Part> parts, @Part("json") RequestBody jsonBody);

    @Multipart
    @POST("img/submitShare/")
    Observable<ShareResponse> shareinfo(@Part List<MultipartBody.Part> parts, @Query("shauid") Long shauid, @Query("shatext") String shatext);

    @Multipart
    @POST("img/submitShareWithoutImg/")
    Observable<ShareResponse> shareinfo2(@Part("shauid") Long shauid, @Query("shatext") String shatext);

    @GET("share/all/")
    Observable<List<Topic>> getshareinfo(@Query("cursor") int id_share, @Query("pageSize") int sum_share);

    @POST("share/giveLove/{id}")
    Observable<Integer> updategood_num(@Path("id") String id);


    @POST("share/getShare/{id}")
    Observable<MyShare> getMyShareinfo(@Path("id") Long id);

}

