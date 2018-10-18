package com.example.chaozhou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.HistoryListInfo;
import com.example.chaozhou.module.history.detail.HistoryInfoActivity;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.utils.ToastUtils;
import com.example.chaozhou.widget.RippleView;

import java.util.List;

public class HistoryListAdapter extends BaseQuickAdapter<HistoryListInfo,BaseViewHolder> {

    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    public HistoryListAdapter(int layoutResId, @Nullable List<HistoryListInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HistoryListInfo item) {
        ImageView hotelPic = helper.getView(R.id.iv_icon);
        ImageLoader.loadCenterCrop(mContext, pictureBaseUrl+item.getImgs().get(0), hotelPic, DefIconFactory.provideIcon());
        helper.setText(R.id.tv_title,item.getHistitle())
                .setText(R.id.tv_desc,item.getHisdescribe());
        RippleView rippleLayout = helper.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                HistoryInfoActivity.launch(mContext,item.getHisssthhId());
            }
        });
    }
}
