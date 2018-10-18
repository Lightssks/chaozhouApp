package com.example.chaozhou.module.login;




import com.example.chaozhou.api.bean.BaseInfo;
import com.example.chaozhou.api.service.Log_RegService;
import com.example.chaozhou.local.table.User;
import com.example.chaozhou.module.base.ILoginPresenter;
import com.example.chaozhou.utils.MobilePhone;
import com.example.chaozhou.utils.ToastUtils;

import rx.Subscriber;


/**
 * Created by yfj on 18-4-12.
 */

public class LoginPresenter implements ILoginPresenter{

    private ILoginView mView;
    public User user;

    public LoginPresenter(ILoginView mView) {
        this.mView = mView;
    }


    public void getUserData(String mobile, String password) {
        if (!MobilePhone.isMobileNO(mobile)) {
            mView.showToast("请输入正确的手机号!");
            mView.showAnimation("");
            return;
        }
        mView.showLoading();
        Log_RegService
                .login(mobile, password)
                .subscribe(new Subscriber<BaseInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showToast("获取服务器数据异常！");

                    }

                    @Override
                    public void onNext(BaseInfo baseInfo) {
                        mView.loadInfo(baseInfo);

                    }
                });

    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
