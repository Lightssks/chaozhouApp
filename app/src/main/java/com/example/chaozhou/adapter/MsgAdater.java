package com.example.chaozhou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.Message;
import com.example.chaozhou.utils.DateConvert;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;

import java.util.List;

public class MsgAdater extends BaseQuickAdapter<Message,BaseViewHolder> {

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    public static List<Message> data;

    public MsgAdater(int layoutResId, @Nullable List<Message> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        ImageView hotelPic = helper.getView(R.id.user_head);
        ImageLoader.loadCenterCrop(mContext, pictureBaseUrl+item.getHead(), hotelPic, DefIconFactory.provideIcon());
        helper.setText(R.id.user_name,item.getUname())
                .setText(R.id.cm_text,item.getComments())
                .setText(R.id.cm_time, DateConvert.toString(item.getTime()));
    }
}
