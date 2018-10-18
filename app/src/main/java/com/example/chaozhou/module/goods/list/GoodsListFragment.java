package com.example.chaozhou.module.goods.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.GoodsListInfo;
import com.example.chaozhou.injector.component.DaggerGoodsListComponent;
import com.example.chaozhou.injector.modules.GoodsListModule;
import com.example.chaozhou.module.base.BaseFragment;
import com.example.chaozhou.module.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class GoodsListFragment extends BaseFragment<IBasePresenter> implements IGoodsListView {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.rv_read_list)
    RecyclerView rvGoodsList;

    @Inject
    BaseQuickAdapter mAdapter;

    private List<GoodsListInfo> data = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_read_list;
    }

    @Override
    protected void initInjector() {
        DaggerGoodsListComponent.builder()
                .applicationComponent(getAppComponent())
                .goodsListModule(new GoodsListModule(this, R.layout.adapter_goods_list, data))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(toolBar, true,"");
        desc.setText("特产");
        rvGoodsList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);
        rvGoodsList.setAdapter(mAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<GoodsListInfo> data) {
        this.data = data;
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreData(List<GoodsListInfo> data) {
        this.data.addAll(data);
    }

    @Override
    public void loadNoData() {

    }
}
