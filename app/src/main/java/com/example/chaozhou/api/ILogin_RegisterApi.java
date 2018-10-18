package com.example.chaozhou.api;

import com.example.chaozhou.api.bean.BaseInfo;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ILogin_RegisterApi {
    /**
     * 用户注册
     * eg:http://193.112.86.153/account/register
     * @param mobile 手机号码
     * @param password 密码
     * @return
     */

    @POST("account/register")
    Observable<BaseInfo> register(@Query("phoneNumber") String mobile, @Query("password") String password, @Query("uname") String uname);



    /**
     * 用户登录
     * eg:http://193.112.86.153 /account/login
     * @param mobile 手机号码
     * @param password 密码
     * @return
     */

    @POST("account/login")
    Observable<BaseInfo> login(@Query("phoneNumber") String mobile, @Query("password") String password);

    /**
     * 获取手机短信验证码
     * eg:http://193.112.86.153/account/smsAuth
     * @param mobile 手机号
     * @return
     */

    @POST("account/smsAuth")
    Observable<String> getCheckCode(@Query("phoneNumber") String mobile);


    /**
     * 找回密码
     * eg:http://193.112.86.153/account/findpwd
     * findpassword
     * @return
     */

    @POST("account/findpwd")
    Observable<BaseInfo> findpassword(@Query("phoneNumber") String mobile, @Query("password") String password);

    /**
     * 获取个人信息
     *
     *
     * @return
     */

    @GET("account/userByPhone/{phoneNumber}")
    Observable<BaseInfo> getUserInfoByNumber(@Query("phoneNumber") String phoneNumber);

    @GET("account/userById/{id}")
    Observable<BaseInfo> getUserInfoById(@Query("id") Long id);

    @POST("account/updateByPhone")
    Observable<Integer> UpdateByPhone(@Query("id") Long id, @Query("uname") String uname, @Query("phoneNumber") String phoneNumber, @Query("birthday") String birthday, @Query("sex") String sex, @Query("age") int age, @Query("head") String head);
}
