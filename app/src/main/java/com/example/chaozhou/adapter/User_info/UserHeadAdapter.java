package com.example.chaozhou.adapter.User_info;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chaozhou.R;
import com.example.chaozhou.local.table.Personal_Info;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;

import java.util.List;

public class UserHeadAdapter extends ArrayAdapter<Personal_Info> {

    private int resourceId;
    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    public UserHeadAdapter(Context context, int textViewResourceId, List<Personal_Info> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Personal_Info info = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        } else {
            view = convertView;
        }
        BaseViewHolder helper = new BaseViewHolder(view);
        ImageView hotelPic = helper.getView(R.id.user_head_image);
        ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + info.getMessage(), hotelPic, DefIconFactory.provideIcon());
        TextView infoname = (TextView)view.findViewById(R.id.user_head);
        infoname.setText(info.getName());
        infoname.setGravity(Gravity.CENTER);
        infoname.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }

}
