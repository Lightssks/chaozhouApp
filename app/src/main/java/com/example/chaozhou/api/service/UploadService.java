package com.example.chaozhou.api.service;

import com.example.chaozhou.api.IUploadApi;
import com.example.chaozhou.api.RetrofitService;
import com.example.chaozhou.api.bean.BaseResponse;
import com.example.chaozhou.api.bean.MyShare;

import com.example.chaozhou.api.bean.ShareResponse;
import com.example.chaozhou.module.share.Topic;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UploadService {
    private static IUploadApi baseApi = RetrofitService.createUploadService();

    /**
     * 上传头像
     *
     * @param phoneNumber 数据  ｛文件名称，用户账号/手机号｝
     * @param file 图片
     * @return
     */

    public static Observable<BaseResponse> uploadFileWithRequestBody(MultipartBody.Part file,String phoneNumber) {
        return baseApi.uploadFileWithRequestBody(file,phoneNumber)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 上传个人分享内容
     *
     * @param parts 图片
     * 分享id  ｛用户账号/手机号｝
     *                分享内容
     * @return
     */
    public static Observable<ShareResponse> shareinfo(List<MultipartBody.Part> parts, Long shauid, String shatext) {
        return baseApi.shareinfo(parts,shauid,shatext)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 上传个人分享内容
     *
     * @param shatext 纯文字
     * 分享id  ｛用户账号/手机号｝
     *                分享内容
     * @return
     */
    public static Observable<ShareResponse> shareinfo2( Long shauid, String shatext) {
        return baseApi.shareinfo2(shauid,shatext)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取个人分享内容
     *

     * @return
     */
    public static Observable<List<Topic>> getshareinfo(int id_share,int sum_share) {
        return baseApi.getshareinfo(id_share,sum_share)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 更新点赞
     *

     * @return
     */
    public static Observable<Integer> updategood_num(String id_share) {
        return baseApi.updategood_num(id_share)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<MyShare> getMyShareinfo(Long id){
        return baseApi.getMyShareinfo(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
