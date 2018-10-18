package com.example.chaozhou.module.register.get_inputCode;

import com.example.chaozhou.api.service.Log_RegService;
import com.example.chaozhou.utils.MobilePhone;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import rx.Subscriber;

public class Get_inputCodePresenter implements IGet_inputCodePresenter {


    private IGet_inputCodeView mView;
    private String msg;

    public Get_inputCodePresenter(IGet_inputCodeView mView) {
        this.mView = mView;
    }

    @Override
    public void getData(String mobile) {
        if (!MobilePhone.isMobileNO(mobile)) {
            mView.showAnimation("");
            return;
        }
        mView.showLoading();
        Log_RegService
                .getCheckCode(mobile)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Logger.d(throwable.getMessage());
                        ToastUtils.showToast("获取服务器数据异常！");

                    }

                    @Override
                    public void onNext(String baseInfo) {
                        mView.loadInfo(baseInfo);
                        msg = baseInfo;
                        Logger.d(baseInfo);
                    }
                });
    }


    public void checkCode(String code) {
        if(code.length()!=6){
            mView.showToast("请输入正确位数的验证码");
            return;
        }

        mView.showLoading();
        mView.loadInfo(msg);


    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
