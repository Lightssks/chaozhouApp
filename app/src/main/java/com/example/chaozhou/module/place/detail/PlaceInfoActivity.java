package com.example.chaozhou.module.place.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.MapInfo;
import com.example.chaozhou.api.bean.PlaceInfo;
import com.example.chaozhou.injector.component.DaggerPlaceInfoComponent;
import com.example.chaozhou.injector.modules.PlaceInfoModule;
import com.example.chaozhou.module.base.BaseSwipeBackActivity;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.ui.home.MainActivity2;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.CommentLayout;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import butterknife.BindView;

public class PlaceInfoActivity extends BaseSwipeBackActivity<IBasePresenter> implements IPlaceInfoView {

    @BindView(R.id.title_left)
    Button titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    Button titleRight;
    @BindView(R.id.tv_picture)
    ImageView tvPicture;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_comment_list)
    CommentLayout tvCommentList;

    private static final String Place_ID_KEY = "PlaceIdKey";

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    private String toussthhId;

    public static void launch(Context context, String toussthhId) {
        Intent intent = new Intent(context, PlaceInfoActivity.class);
        intent.putExtra(Place_ID_KEY, toussthhId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_place_info;
    }

    private PlaceInfoActivity getActivity() {
        return this;
    }

    @Override
    protected void initInjector() {
        DaggerPlaceInfoComponent.builder()
                .placeInfoModule(new PlaceInfoModule(toussthhId, this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        toussthhId = getIntent().getStringExtra(Place_ID_KEY);
        titleRight.setVisibility(View.GONE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCommentList = new CommentLayout(this);
        tvCommentList.loadData(toussthhId);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(PlaceInfo placeInfo) {
        final String latitude,longitude,title,address;
        List<String> imgs = placeInfo.getImgs();
        if(imgs != null) {
            ImageLoader.loadCenterCrop(this, pictureBaseUrl+imgs.get(0), tvPicture,
                    DefIconFactory.provideIcon());
        }
        titleCenter.setText(placeInfo.getToutitle());
        RichText.from(placeInfo.getToudescribe())
                .into(tvText);
        MapInfo mapInfo = placeInfo.getMap();
        if (mapInfo != null) {
            tvArea.setText(mapInfo.getMarea());
            latitude = mapInfo.getMlatitude();
            longitude = mapInfo.getMlongitude();
            title = placeInfo.getToutitle();
            address = mapInfo.getMarea();
            tvArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity2.newInstance(getActivity(),latitude,longitude,title,address);
                }
            });
        }
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
