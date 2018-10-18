package com.example.chaozhou.module.personal_Info.list;

import com.example.chaozhou.module.base.IBasePresenter;

public class Share_LIstPresenter implements IBasePresenter {
    IShare_ListView mView;

    public Share_LIstPresenter(Share_ListActivity mIShareListView){
        this.mView = (IShare_ListView) mIShareListView;
    }
    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }
}
