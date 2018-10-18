package com.example.chaozhou.module.hotel.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.HotelListInfo;
import com.example.chaozhou.injector.component.DaggerHotelListComponent;
import com.example.chaozhou.injector.modules.HotelListModule;
import com.example.chaozhou.module.base.BaseFragment;
import com.example.chaozhou.module.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HotelListFragment extends BaseFragment<IBasePresenter> implements IHotelListView {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.rv_read_list)
    RecyclerView rvHotelList;

    @Inject
    BaseQuickAdapter mAdapter;


    private List<HotelListInfo> data = new ArrayList<>();

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_read_list;
    }

    @Override
    protected void initInjector() {
        DaggerHotelListComponent.builder()
                .applicationComponent(getAppComponent())
                .hotelListModule(new HotelListModule(this, R.layout.adapter_hotel_list, data))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(toolBar, true,"");
        desc.setText("住宿");
        rvHotelList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);
        rvHotelList.setAdapter(mAdapter);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<HotelListInfo> data) {
        this.data = data;
        mAdapter.addData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreData(List<HotelListInfo> data) {
        this.data.addAll(data);
    }

    @Override
    public void loadNoData() {
    }

}
