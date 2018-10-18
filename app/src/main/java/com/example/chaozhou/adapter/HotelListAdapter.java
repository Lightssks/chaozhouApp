package com.example.chaozhou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.HotelListInfo;
import com.example.chaozhou.api.bean.MapInfo;
import com.example.chaozhou.module.hotel.detail.HotelInfoActivity;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.RippleView;
import com.orhanobut.logger.Logger;

import java.util.List;

public class HotelListAdapter extends BaseQuickAdapter<HotelListInfo,BaseViewHolder> {

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    public HotelListAdapter(int layoutResId, @Nullable List<HotelListInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HotelListInfo item) {
        ImageView hotelPic = helper.getView(R.id.iv_icon);
        ImageLoader.loadCenterCrop(mContext, pictureBaseUrl+item.getHotpicture(), hotelPic, DefIconFactory.provideIcon());
        MapInfo mapInfo = item.getMap();
        if (mapInfo != null) {
            helper.setText(R.id.tv_area, item.getMap().getMarea());
        }
        helper.setText(R.id.tv_title, item.getHotname())
                .setText(R.id.tv_state, item.getHotstate())
                .setText(R.id.tv_phone,item.getHotphone());
        // 波纹效果
        RippleView rippleLayout = helper.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                   HotelInfoActivity.launch(mContext, item.getHotssthhId());
                }
            });
    }
}

