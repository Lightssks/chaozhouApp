package com.example.chaozhou.module.place.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.PlaceListInfo;
import com.example.chaozhou.injector.component.DaggerPlaceListComponent;
import com.example.chaozhou.injector.modules.PlaceListModule;
import com.example.chaozhou.module.base.BaseFragment;
import com.example.chaozhou.module.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class PlaceListFragment extends BaseFragment<IBasePresenter> implements IPlaceListView {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_read_list)
    RecyclerView rvPlaceList;
    @BindView(R.id.desc)
    TextView desc;

    @Inject
    BaseQuickAdapter mAdapter;

    private List<PlaceListInfo> data = new ArrayList<>();


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_read_list;
    }

    @Override
    protected void initInjector() {
        DaggerPlaceListComponent.builder()
                .applicationComponent(getAppComponent())
                .placeListModule(new PlaceListModule(this, R.layout.adapter_place_list, data))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(toolBar, true,"");
        desc.setText("景点");
        rvPlaceList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);
        rvPlaceList.setAdapter(mAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<PlaceListInfo> data) {
        this.data = data;
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreData(List<PlaceListInfo> data) {
        this.data.addAll(data);
    }

    @Override
    public void loadNoData() {

    }


}
