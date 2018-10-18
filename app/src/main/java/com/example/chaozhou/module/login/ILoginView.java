package com.example.chaozhou.module.login;

import com.example.chaozhou.api.bean.BaseInfo;
import com.example.chaozhou.module.base.IBaseView;


/**
 * Created by yfj on 18-4-12
 */
public interface ILoginView extends IBaseView {
    /**
     * 显示用户信息
     * @param info
     */
    void loadInfo(BaseInfo info);

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
