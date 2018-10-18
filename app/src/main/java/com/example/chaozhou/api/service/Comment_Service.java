package com.example.chaozhou.api.service;

import com.example.chaozhou.api.RetrofitService;
import com.example.chaozhou.api.bean.AllMessage;
import com.example.chaozhou.api.bean.Commenter;
import com.example.chaozhou.api.bean.ShareListInfo;
import com.example.chaozhou.api.comment;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Comment_Service {

    private static comment baseApi = RetrofitService.createsCommentService();


    /**
     * 发表评论
     *

     * @return
     */

    public static Observable<Integer> sendComment(Long id_share, String id_shared, String ctext) {
        return baseApi.sendComment(id_share, id_shared, ctext)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());

    }

        /**
         * 发表评论
         *

         * @return
         */

    public static Observable<List<Commenter>> getComment(String id_share) {
        return baseApi.getComment(id_share)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取一个分享信息
     *

     * @return
     */

    public static Observable<ShareListInfo> getoneShareInfo(String id) {
        return baseApi.getoneShareInfo(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取消息通知
     *

     * @return
     */

    public static Observable<AllMessage> getUserMsg(Long id) {
        return baseApi.getUserMsg(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
