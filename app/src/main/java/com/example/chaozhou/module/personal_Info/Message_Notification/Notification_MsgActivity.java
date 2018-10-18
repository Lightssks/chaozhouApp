package com.example.chaozhou.module.personal_Info.Message_Notification;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.adapter.MsgAdater;
import com.example.chaozhou.api.bean.AllMessage;
import com.example.chaozhou.api.bean.Message;
import com.example.chaozhou.api.service.Comment_Service;
import com.example.chaozhou.module.home.MainActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class Notification_MsgActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MsgAdater adapter;
    private RecyclerView msgList;
    private static List<Message> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__msg);
        Button title_left = findViewById(R.id.title_left);
        title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title_centent = findViewById(R.id.title_center);
        title_centent.setText("我的消息");
        Button title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });

        adapter = new MsgAdater(R.layout.adapter_comment_list,data);
        msgList = findViewById(R.id.list_message);


        init();

        msgList.setLayoutManager(new LinearLayoutManager(this));
        msgList.setItemAnimator(new DefaultItemAnimator());
        msgList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        msgList.setAdapter(adapter);

    }

    private void initInfos(List<Message> Msg_list){
        adapter.data.clear();
        adapter.addData(Msg_list);
        adapter.notifyDataSetChanged();
    }


    //调用接口
    private void init(){

        Comment_Service.getUserMsg(MainActivity.user.getUid())
                .subscribe(new Subscriber<AllMessage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Logger.d(throwable.toString());
                    }

                    @Override
                    public void onNext(AllMessage allMessages) {

                        if (allMessages.getStatus()==0){
                            initInfos(allMessages.getMsg());
                            swipeRefreshLayout.setRefreshing(false);

                        } else {
                            Logger.d("出现异常！");
                        }
                    }
                });
    }
}
