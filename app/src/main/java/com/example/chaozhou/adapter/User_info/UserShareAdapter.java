package com.example.chaozhou.adapter.User_info;

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
import com.example.chaozhou.module.share.Topic;
import com.example.chaozhou.module.share.detail.Share_detailActivity;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.FlowImageLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.chaozhou.AndroidApplication.getContext;

public class UserShareAdapter extends BaseAdapter {

    private List<Topic> list = new ArrayList<Topic>();
    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";
    private Context mContext;
    private LayoutInflater mInflater;

    //评论获取本id 没有解决
    public void addShareList(Topic topic) {
        list.add(topic);
        notifyDataSetChanged();
    }
    public UserShareAdapter(Context ctx, List<Topic> topics){
        this.mInflater = LayoutInflater.from(ctx);
        this.mContext = ctx;
        list = topics;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Topic getItem(int position) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_topic, parent, false);
            holder = new ViewHolder();
            holder.user_image = (ImageView) convertView.findViewById(R.id.user_image);
            holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            holder.topic_content = (TextView) convertView.findViewById(R.id.topic_content);
            holder.image_layout = (FlowImageLayout) convertView.findViewById(R.id.image_layout);
            holder.content_time = (TextView) convertView.findViewById(R.id.content_time);
            holder.comment_button = convertView.findViewById(R.id.image_button_message);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }


        final Topic topic = list.get(position);
        ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + topic.getHead(), holder.user_image, DefIconFactory.provideIcon());
        holder.user_name.setText(topic.getUname());
        holder.topic_content.setText(topic.getShatext());
        holder.content_time.setText(topic.getShatime());

        if (topic.getImgs() != null && topic.getImgs().size()>0){
            holder.image_layout.setVisibility(View.VISIBLE);
            holder.image_layout.loadImage(topic.getImgs().size(), new FlowImageLayout.OnImageLayoutFinishListener() {
                @Override
                public void layoutFinish(List<ImageView> images) {
                    for (int i = 0; i < images.size(); i++) {

                        ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + topic.getImgs().get(i), images.get(i), DefIconFactory.provideIcon());
                    }
                }

            });
        }else{
            holder.image_layout.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Share_detailActivity.class);
                intent.putExtra("shareid",topic.getShassthhId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    public static class ViewHolder {
        ImageView user_image;
        TextView user_name;
        TextView topic_content;
        FlowImageLayout image_layout;
        TextView content_time;
        ImageButton comment_button;

//        NoScrollGridView gridView;
    }
}
