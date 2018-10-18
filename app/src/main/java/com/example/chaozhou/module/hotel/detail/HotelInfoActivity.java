package com.example.chaozhou.module.hotel.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.HotelInfo;
import com.example.chaozhou.api.bean.MapInfo;
import com.example.chaozhou.api.bean.RoomInfo;
import com.example.chaozhou.injector.component.DaggerHotelInfoComponent;
import com.example.chaozhou.injector.modules.HotelInfoModule;
import com.example.chaozhou.module.base.BaseSwipeBackActivity;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.ui.home.MainActivity2;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.CommentLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HotelInfoActivity extends BaseSwipeBackActivity<IBasePresenter> implements IHotelInfoView {

    @BindView(R.id.title_left)
    Button titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    Button titleRight;
    @BindView(R.id.tv_picture)
    ImageView tvPicture;
    @BindView(R.id.tv_ac_title)
    TextView tvAcTitle;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_room_list)
    RecyclerView tvRoomList;
    @BindView(R.id.tv_comment_list)
    CommentLayout tvCommentList;

    private static final String Hotel_ID_KEY = "HotelIdKey";

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    private String hotssthhId;
    private List<RoomInfo> data = new ArrayList<>();
    private List<String> devices = new ArrayList<>();

    @Inject
    BaseQuickAdapter mAdapter;

    public static void launch(Context context, String hotssthhId) {
        Intent intent = new Intent(context, HotelInfoActivity.class);
        intent.putExtra(Hotel_ID_KEY, hotssthhId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_hotel_info;
    }

    private HotelInfoActivity getActivity() {
        return this;
    }

    @Override
    protected void initInjector() {
        hotssthhId = getIntent().getStringExtra(Hotel_ID_KEY);
        DaggerHotelInfoComponent.builder()
                .hotelInfoModule(new HotelInfoModule(hotssthhId, this, R.layout.adapter_room_list, data))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        hotssthhId = getIntent().getStringExtra(Hotel_ID_KEY);
        titleCenter.setText("详情");
        titleRight.setVisibility(View.GONE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCommentList = new CommentLayout(this);
        tvCommentList.loadData(hotssthhId);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(HotelInfo hotelInfo) {
        final String latitude,longitude,title,address;
        ImageLoader.loadCenterCrop(this, pictureBaseUrl + hotelInfo.getHotpicture(),
                tvPicture, DefIconFactory.provideIcon());
        tvAcTitle.setText(hotelInfo.getHotname());
        tvDescribe.setText(hotelInfo.getHotdescribe());
        MapInfo mapInfo = hotelInfo.getMap();
        if (mapInfo != null) {
            tvArea.setText(mapInfo.getMarea());
            latitude = mapInfo.getMlatitude();
            longitude = mapInfo.getMlongitude();
            title = hotelInfo.getHotname();
            address = mapInfo.getMarea();
            tvArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity2.newInstance(getActivity(),latitude,longitude,title,address);
                }
            });
        }
        devices = hotelInfo.getDevices();
        data = hotelInfo.getRoom();
        mAdapter.addData(data);
        tvRoomList.setLayoutManager(new LinearLayoutManager(HotelInfoActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        tvRoomList.setItemAnimator(new DefaultItemAnimator());
        tvRoomList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        tvRoomList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

}
