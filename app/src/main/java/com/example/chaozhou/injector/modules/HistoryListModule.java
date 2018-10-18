package com.example.chaozhou.injector.modules;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.adapter.HistoryListAdapter;
import com.example.chaozhou.api.bean.HistoryListInfo;
import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.module.history.list.HistoryListFragment;
import com.example.chaozhou.module.history.list.HistoryListPresenter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryListModule {

    private final HistoryListFragment mHistoryListView;
    private int layoutResId;
    private List<HistoryListInfo> data;

    public HistoryListModule(HistoryListFragment mHistoryListView,int layoutResId,List<HistoryListInfo> data) {
        this.mHistoryListView = mHistoryListView;
        this.layoutResId = layoutResId;
        this.data = data;
    }

    @PerFragment
    @Provides
    public IBasePresenter providePresenter() {
        return new HistoryListPresenter(mHistoryListView);
    }

    @PerFragment
    @Provides
    public BaseQuickAdapter provideAdapter() {
        return new HistoryListAdapter(layoutResId,data);
    }


}
