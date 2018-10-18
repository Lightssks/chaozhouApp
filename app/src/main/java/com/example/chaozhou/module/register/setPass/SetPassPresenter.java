package com.example.chaozhou.module.register.setPass;

import com.example.chaozhou.api.bean.BaseInfo;
import com.example.chaozhou.api.service.Log_RegService;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import rx.Subscriber;

public class SetPassPresenter implements ISetPassPresenter {

    private ISetPassView mView;

    public SetPassPresenter(ISetPassView mView) {
        this.mView = mView;
    }

    public void getUpData(String user, String pass, String input) {
        mView.showLoading();
        if (input.equals("register")){
            Log_RegService
                    .register(user,pass,"用户")
                    .subscribe(new Subscriber<BaseInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Logger.d(throwable.getMessage());
                            ToastUtils.showToast("返回服务器数据异常！");
                        }

                        @Override
                        public void onNext(BaseInfo baseInfo) {
                            mView.loadInfo(baseInfo);

                        }
                    });
        }else if (input.equals("forget")){
            Log_RegService
                    .findpassword(user,pass)
                    .subscribe(new Subscriber<BaseInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            ToastUtils.showToast("返回服务器数据异常！");
                        }

                        @Override
                        public void onNext(BaseInfo baseInfo) {
                            mView.loadInfo(baseInfo);

                        }
                    });
        }

    }

    @Override
    public void getData(String user, String pass, String pass2) {
        if (pass.equals("")) {
            mView.showAnimation("edt_pass");
        } else if (pass2.equals("")) {
            mView.showAnimation("edt_pass2");
        } else if (pass.length() > 0 && pass.length() < 6) {
            mView.showAnimation("pass<6");
        } else if (pass.length() >= 6) {
            mView.showAnimation("pass>=6");
        }
    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
