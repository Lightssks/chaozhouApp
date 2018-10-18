package com.example.chaozhou.module.register.setPass;

import com.example.chaozhou.api.bean.BaseInfo;
import com.example.chaozhou.module.base.IBaseView;

public interface ISetPassView extends IBaseView {

    /**
     * 加载信息
     */


    void loadInfo(BaseInfo baseInfo);

    // void showView();

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
