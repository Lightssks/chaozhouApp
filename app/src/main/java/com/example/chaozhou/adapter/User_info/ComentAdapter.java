package com.example.chaozhou.adapter.User_info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.Commenter;

import java.util.List;

public class ComentAdapter extends BaseAdapter {

    private List<Commenter> mList;
    private Context mContext;



    public ComentAdapter(Context context, List<Commenter> list) {
        mList = list;
        mContext = context;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.share_detail_info, null);
                holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
                holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
//            convertView = View.inflate(mContext, R.layout.share_detail_info, null);
//                ComentAdapter.ViewHolder holder = new ComentAdapter.ViewHolder();
//                holder.mCommentList = (LinearLayout) convertView.findViewById(R.id.comment_list);
//                holder.mBtnInput = convertView.findViewById(R.id.btn_input_comment1);
//                holder.mContent = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 适配数据
        holder.comment_name.setText(mList.get(position).getUname());
        holder.comment_content.setText(mList.get(position).getCtext());
        return convertView;
    }

    public static class ViewHolder{
        TextView comment_name;
        TextView comment_content;
    }

    /**
     * 添加一条评论,刷新列表
     * @param comment
     */
    public void addComment(Commenter comment){
        mList.add(comment);
        notifyDataSetChanged();
    }
}
