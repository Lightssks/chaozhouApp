package com.example.chaozhou.adapter.User_info;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.local.table.Personal_Info;

import java.util.List;

public class UserInfoAdapter extends ArrayAdapter<Personal_Info> {
    private int resourceId;

    public UserInfoAdapter(Context context, int textViewResourceId, List<Personal_Info> objects){
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
        TextView message_text = (TextView)view.findViewById(R.id.user_msg);
        message_text.setText(info.getMessage());
        message_text.setGravity(Gravity.CENTER);
        message_text.setGravity(Gravity.CENTER_VERTICAL);

        TextView infoname = (TextView)view.findViewById(R.id.user_name);
        infoname.setText(info.getName());
        infoname.setGravity(Gravity.CENTER);
        infoname.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }
}
