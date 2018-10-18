package com.example.chaozhou.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.example.chaozhou.R;
import com.example.chaozhou.module.base.MapBaseActivity;
import com.example.chaozhou.ui.adapter.WalkSegmentListAdapter;
import com.example.chaozhou.utils.AMapUtil;

import butterknife.BindView;

/**
 * Created by dingmouren on 2017/3/3.
 */

public class WalkRouteDetailActivityMap extends MapBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listview) ListView mListView;
    @BindView(R.id.tv_route_info) TextView mTvRouteInfo;
    private WalkSegmentListAdapter mWalkSegmentListAdapter;
    private WalkPath mWalkPath;

    public static void newInstance(Activity activity, WalkPath walkPath, WalkRouteResult walkRouteResult){
        Intent intent = new Intent(activity,WalkRouteDetailActivityMap.class);
        intent.putExtra("walk_path",walkPath);
        intent.putExtra("walk_result",walkRouteResult);
        activity.startActivity(intent);
    }
    @Override
    public int setLayoutId() {
        return R.layout.activity_walk_route_detail;
    }

    @Override
    public void init(Bundle savedInstanceStae) {
        if (null != getIntent()){
            mWalkPath = getIntent().getParcelableExtra("walk_path");
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        String dur = AMapUtil.getFriendlyTime((int) mWalkPath.getDuration());
        String dis = AMapUtil .getFriendlyLength((int) mWalkPath.getDistance());
        if (null != dur && null != dis) {
            mTvRouteInfo.setText("步行耗时" + dur + ",路程" + dis);
        }else {
            mTvRouteInfo.setText("步行路线详情");
        }
        mWalkSegmentListAdapter = new WalkSegmentListAdapter(
                this.getApplicationContext(), mWalkPath.getSteps());
        mListView.setAdapter(mWalkSegmentListAdapter);
    }

    @Override
    public void initListener() {
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void initData() {

    }
}
