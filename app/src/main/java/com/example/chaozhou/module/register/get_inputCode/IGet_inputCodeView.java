package com.example.chaozhou.module.register.get_inputCode;

import com.example.chaozhou.module.base.IBaseView;

public interface IGet_inputCodeView  extends IBaseView {

    /**
     * 显示验证码信息
     * @param 验证码
     */
    void loadInfo(String msg);

    /**
     * 显示toast
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示动画
     */
    void showAnimation(String msg);
}
