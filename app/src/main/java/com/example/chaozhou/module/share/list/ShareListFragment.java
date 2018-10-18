package com.example.chaozhou.module.share.list;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.adapter.TopicAdapter;
import com.example.chaozhou.api.service.UploadService;
import com.example.chaozhou.module.base.BaseActivity;
import com.example.chaozhou.module.share.AddShareFragment;
import com.example.chaozhou.module.share.Topic;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;


public class ShareListFragment extends Fragment {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.image_list)
    ListView imageList;
    @BindView(R.id.floatingBtu)
    FloatingActionButton floatingBtu;
    private TopicAdapter adapter;
    private List<Topic> Topic_list = new ArrayList<Topic>();

    protected Context mContext;
    //缓存Fragment view
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_share, null);
            ButterKnife.bind(this, mRootView);
            initViews();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    protected void initViews() {
        Logger.d("ShareList initViews");
        initToolBar(toolBar, true,"");
        desc.setText("分享");
        floatingBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareListFragment.this.mContext,AddShareFragment.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Logger.d("ShareList OnRefresh");
                initViews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        imageList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
            }
        });
        adapter = new TopicAdapter(mContext,Topic_list);
        imageList.setAdapter(adapter);
        init();
        adapter.notifyDataSetChanged();
    }

    public void init(){
        UploadService.getshareinfo(10,50)

                .subscribe(new  Subscriber<List<Topic>>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable.toString() + " 1234" );
                    }

                    @Override
                    public void onNext(List<Topic> data) {
//                        mView.loadData(shareListInfos);
                        adapter.list.clear();
                        for (int i = data.size()-1;i>=0;i--){
                            adapter.addTopic(data.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        Logger.d("ShareList OnNext");
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                initViews();
            }
        });
        Logger.d("Resume");
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        adapter.notifyDataSetChanged();
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity)getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }



}
