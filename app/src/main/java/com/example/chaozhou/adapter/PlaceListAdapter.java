package com.example.chaozhou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.PlaceListInfo;
import com.example.chaozhou.module.place.detail.PlaceInfoActivity;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.utils.ToastUtils;
import com.example.chaozhou.widget.RippleView;

import java.util.List;

public class PlaceListAdapter extends BaseQuickAdapter<PlaceListInfo,BaseViewHolder> {

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    public PlaceListAdapter(int layoutResId, @Nullable List<PlaceListInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PlaceListInfo item) {
        ImageView hotelPic = helper.getView(R.id.iv_icon);
        List<String> imgs = item.getImgs();
        if(imgs != null) {
            ImageLoader.loadCenterCrop(mContext, pictureBaseUrl+imgs.get(0), hotelPic,
                    DefIconFactory.provideIcon());
        }
        helper.setText(R.id.tv_title,item.getToutitle())
                .setText(R.id.tv_desc,item.getToudescribe());
        // 波纹效果
        RippleView rippleLayout = helper.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                PlaceInfoActivity.launch(mContext, item.getToussthhId());
            }
        });
    }
}