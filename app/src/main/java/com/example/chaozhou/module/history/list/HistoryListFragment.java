package com.example.chaozhou.module.history.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.HistoryListInfo;
import com.example.chaozhou.injector.component.DaggerHistoryListComponent;
import com.example.chaozhou.injector.modules.HistoryListModule;
import com.example.chaozhou.module.base.BaseFragment;
import com.example.chaozhou.module.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HistoryListFragment extends BaseFragment<IBasePresenter> implements IHistoryListView {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.rv_read_list)
    RecyclerView rvHistoryList;

    @Inject
    BaseQuickAdapter mAdapter;

    private List<HistoryListInfo> data = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_read_list;
    }

    @Override
    protected void initInjector() {
        DaggerHistoryListComponent.builder()
                .applicationComponent(getAppComponent())
                .historyListModule(new HistoryListModule(this,R.layout.adapter_history_list,data))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(toolBar, true,"");
        desc.setText("历史");
        rvHistoryList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);
        rvHistoryList.setAdapter(mAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<HistoryListInfo> data) {
        this.data = data;
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreData(List<HistoryListInfo> data) {
        this.data.addAll(data);
    }

    @Override
    public void loadNoData() {

    }

}
