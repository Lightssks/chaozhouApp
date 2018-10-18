package com.example.chaozhou.module.history.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.HistoryInfo;
import com.example.chaozhou.injector.component.DaggerHistoryInfoComponent;
import com.example.chaozhou.injector.modules.HistoryInfoModule;
import com.example.chaozhou.module.base.BaseSwipeBackActivity;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.CommentLayout;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;

public class HistoryInfoActivity extends BaseSwipeBackActivity<IBasePresenter> implements IHistoryInfoView {


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
    @BindView(R.id.tv_comment_list)
    CommentLayout tvCommentList;

    private static final String History_ID_KEY = "HistoryIdKey";

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    private String hisssthhId;

    public static void launch(Context context, String hisssthhId) {
        Intent intent = new Intent(context, HistoryInfoActivity.class);
        intent.putExtra(History_ID_KEY, hisssthhId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_history_info;
    }

    @Override
    protected void initInjector() {
        DaggerHistoryInfoComponent.builder()
                .historyInfoModule(new HistoryInfoModule(hisssthhId, this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        hisssthhId = getIntent().getStringExtra(History_ID_KEY);
        titleRight.setVisibility(View.GONE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCommentList = new CommentLayout(this);
        tvCommentList.loadData(hisssthhId);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(HistoryInfo historyInfo) {
        ImageLoader.loadCenterCrop(this, pictureBaseUrl + historyInfo.getImgs().get(0),
                tvPicture, DefIconFactory.provideIcon());
        titleCenter.setText(historyInfo.getHistitle());
        RichText.from(historyInfo.getHisdescribe())
                .into(tvText);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
