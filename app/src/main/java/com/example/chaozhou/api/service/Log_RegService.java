package com.example.chaozhou.api.service;


import com.example.chaozhou.api.ILogin_RegisterApi;
import com.example.chaozhou.api.RetrofitService;
import com.example.chaozhou.api.bean.BaseInfo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by yfj on 18-4-12.
 *
 *
 */
public class Log_RegService {

    private static ILogin_RegisterApi baseApi = RetrofitService.createLog_RegService();
    /**
     * 登录
     *
     * @param mobile 用户手机
     * @param password 密码
     * @return
     */
    public static Observable<BaseInfo> login(String mobile, String password) {
        return baseApi.login(mobile, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     *
     * @param mobile 用户手机
     *
     * @return
     */

    public static Observable<BaseInfo> register(String mobile, String passwrod, String uname){
        return baseApi.register(mobile,passwrod,uname)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取验证码
     *
     * @param mobile 手机号
     * @return
     */
    public static Observable<String> getCheckCode(String mobile) {
        return baseApi.getCheckCode(mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 找回密码
     *
     *  status 状态码
     * @return
     */
    public static Observable<BaseInfo> findpassword(String mobile, String password) {
        return baseApi.findpassword(mobile,password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取个人信息
     *
     *  status 状态码
     * @return
     */

    public static Observable<BaseInfo> getUserInfoByNumber(String mobile) {
        return baseApi.getUserInfoByNumber(mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<BaseInfo> getUserInfoByid(long id) {
        return baseApi.getUserInfoById(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 更新个人信息
     *
     *  status 状态码
     * @return
     */
        public static Observable<Integer> UpdateByPhone(Long id, String uname,String phoneNumber, String birthday,String sex,int age,String head) {
        return baseApi.UpdateByPhone(id,uname,phoneNumber,birthday,sex,age,head)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
