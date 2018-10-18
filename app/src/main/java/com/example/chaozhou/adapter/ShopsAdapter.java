package com.example.chaozhou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.MapInfo;
import com.example.chaozhou.api.bean.ShopInfo;
import com.example.chaozhou.ui.home.MainActivity2;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.RippleView;

import java.util.List;

public class ShopsAdapter extends BaseQuickAdapter<ShopInfo,BaseViewHolder> {

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com";
    private List<ShopInfo> data;

    public ShopsAdapter(int layoutResId, @Nullable List<ShopInfo> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopInfo item) {
        ImageView shopPic = helper.getView(R.id.sp_picture);
        ImageLoader.loadCenterCrop(mContext, pictureBaseUrl+item.getShopicture(), shopPic,
                DefIconFactory.provideIcon());
        helper.setText(R.id.sp_title,item.getShoname())
                .setText(R.id.sp_marea,item.getMap().getMarea())
                .setText(R.id.sp_type,item.getShotype())
                .setText(R.id.sp_phone,item.getShophone());
        RippleView rippleLayout = helper.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                MapInfo mapInfo = item.getMap();
                final String latitude,longitude,title,address;
                if (mapInfo != null) {
                    latitude = mapInfo.getMlatitude();
                    longitude = mapInfo.getMlongitude();
                    title = item.getShoname();
                    address = mapInfo.getMarea();
                    MainActivity2.newInstance(mContext,latitude,longitude,title,address);
                }
            }
        });

    }
}
