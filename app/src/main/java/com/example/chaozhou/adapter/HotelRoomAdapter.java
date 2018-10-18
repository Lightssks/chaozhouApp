package com.example.chaozhou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.RoomInfo;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.orhanobut.logger.Logger;

import java.util.List;

public class HotelRoomAdapter extends BaseQuickAdapter<RoomInfo,BaseViewHolder> {

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com";
    private List<RoomInfo> data;

    public HotelRoomAdapter(int layoutResId, @Nullable List<RoomInfo> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomInfo item) {
        ImageView hotelPic = helper.getView(R.id.rm_icon);
        ImageLoader.loadCenterCrop(mContext, pictureBaseUrl+item.getRoomImg(), hotelPic,
                DefIconFactory.provideIcon());
        helper.setText(R.id.rm_title, item.getRoomName())
                .setText(R.id.rm_type, item.getRoomType())
                .setText(R.id.rm_favor,item.getSatisfaction());
    }
}
