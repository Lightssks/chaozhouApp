package com.example.chaozhou.api.service;

import com.example.chaozhou.api.ICommentApi;
import com.example.chaozhou.api.RetrofitService;
import com.example.chaozhou.api.bean.CommentInfo;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommentService {

    private static ICommentApi sCommentService = RetrofitService.createCommentService();

    public static Observable<List<CommentInfo>> getCommentsList(String id) {
        return sCommentService.getCommentsList(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Integer> subComments(long cuid,String cssthhId,String ctext) {
        return sCommentService.subComments(cuid,cssthhId,ctext)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
