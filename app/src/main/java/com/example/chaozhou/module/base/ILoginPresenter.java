package com.example.chaozhou.module.base;

public interface ILoginPresenter extends IBasePresenter {

    /**
     * 获取数据
     */
    public void getUserData(String mobile, String password);

}
