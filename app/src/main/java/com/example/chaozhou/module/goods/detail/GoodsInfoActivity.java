package com.example.chaozhou.module.goods.detail;

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
import com.example.chaozhou.api.bean.GoodsInfo;
import com.example.chaozhou.api.bean.ShopInfo;
import com.example.chaozhou.injector.component.DaggerGoodsInfoComponent;
import com.example.chaozhou.injector.modules.GoodsInfoModule;
import com.example.chaozhou.module.base.BaseSwipeBackActivity;
import com.example.chaozhou.module.base.IBasePresenter;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.CommentLayout;
import com.orhanobut.logger.Logger;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class GoodsInfoActivity extends BaseSwipeBackActivity<IBasePresenter> implements IGoodsInfoView {

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
    @BindView(R.id.tv_shop_list)
    RecyclerView tvShopList;
    @BindView(R.id.tv_comment_list)
    CommentLayout tvCommentList;

    @Inject
    BaseQuickAdapter mAdapter;

    private static final String Goods_ID_KEY = "GoodsIdKey";

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    private String spessthhId;
    private List<ShopInfo> data = new ArrayList<>();

    public static void launch(Context context, String spessthhId) {
        Intent intent = new Intent(context, GoodsInfoActivity.class);
        intent.putExtra(Goods_ID_KEY, spessthhId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected void initInjector() {
        DaggerGoodsInfoComponent.builder()
                .goodsInfoModule(new GoodsInfoModule(spessthhId, this,R.layout.adapter_shop_list,data))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        spessthhId = getIntent().getStringExtra(Goods_ID_KEY);
        titleRight.setVisibility(View.GONE);
        titleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        tvCommentList = new CommentLayout(this);
        tvCommentList.loadData(spessthhId);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(GoodsInfo goodsInfo) {
        ImageLoader.loadCenterCrop(this, pictureBaseUrl + goodsInfo.getSpepicture(),
                tvPicture, DefIconFactory.provideIcon());
        titleCenter.setText(goodsInfo.getSpename());
        RichText.from(goodsInfo.getSpedescribe())
                .into(tvText);
        //tvText.setText(goodsInfo.getSpedescribe());
        data = goodsInfo.getShop();
        mAdapter.addData(data);
        tvShopList.setLayoutManager(new LinearLayoutManager(GoodsInfoActivity.this,
                LinearLayoutManager.HORIZONTAL,false));
        tvShopList.setItemAnimator(new DefaultItemAnimator());
        tvShopList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        tvShopList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }

}
