package com.example.chaozhou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.Message;
import com.example.chaozhou.module.share.detail.Share_detailActivity;
import com.example.chaozhou.utils.DateConvert;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.chaozhou.AndroidApplication.getContext;

public class MsgListAdapter extends BaseAdapter {
    private List<Message> list = new ArrayList<Message>();
    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";
    private Context mContext;
    private LayoutInflater mInflater;


    public MsgListAdapter(Context ctx, List<Message> msgs){
        this.mInflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        list = msgs;
    }

    public void addMsg(Message msg){
        list.add(msg);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message, parent, false);
            holder = new ViewHolder();
            holder.user_image = (ImageView) convertView.findViewById(R.id.user_image_msg);
            holder.user_name = (TextView) convertView.findViewById(R.id.user_name_msg);
            holder.msg_content = (TextView) convertView.findViewById(R.id.topic_content);
            holder.content_time = (TextView) convertView.findViewById(R.id.msg_time_time);
            holder.good_button = convertView.findViewById(R.id.image_button);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        final Message msg = list.get(position);
        ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + msg.getHead(), holder.user_image, DefIconFactory.provideIcon());
        holder.user_name.setText(msg.getUname());
        holder.msg_content.setText(msg.getComments());
        holder.content_time.setText(DateConvert.toString(msg.getTime()));
        if (msg.getComments()!=null){
            //评论
            holder.good_button.setVisibility(View.GONE);
        }else{
            //点赞
            holder.msg_content.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Share_detailActivity.class);
                intent.putExtra("shareid",msg.getShareId());
                mContext.startActivity(intent);

            }
        });
        return convertView;
    }


    public static class ViewHolder {
        ImageView user_image;
        TextView user_name;
        TextView msg_content;
        TextView content_time;
        ImageButton good_button;


//        NoScrollGridView gridView;
    }
}
