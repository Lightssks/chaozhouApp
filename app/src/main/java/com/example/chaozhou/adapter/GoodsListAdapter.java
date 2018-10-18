package com.example.chaozhou.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.GoodsListInfo;
import com.example.chaozhou.module.goods.detail.GoodsInfoActivity;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.utils.ToastUtils;
import com.example.chaozhou.widget.RippleView;

import java.util.List;

public class GoodsListAdapter extends BaseQuickAdapter<GoodsListInfo,BaseViewHolder>{

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    public GoodsListAdapter(int layoutResId, List<GoodsListInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,final GoodsListInfo item) {
        ImageView hotelPic = helper.getView(R.id.iv_icon);
        ImageLoader.loadCenterCrop(mContext, pictureBaseUrl+item.getSpepicture(), hotelPic, DefIconFactory.provideIcon());
        helper.setText(R.id.tv_desc,item.getSpedescribe())
                .setText(R.id.tv_type,item.getSpetype())
                .setText(R.id.tv_price,item.getSpeprice());
        // 波纹效果
        RippleView rippleLayout = helper.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                GoodsInfoActivity.launch(mContext, item.getSpessthhId());
            }
        });
    }
}
