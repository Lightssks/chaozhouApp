package com.example.chaozhou.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.chaozhou.AndroidApplication;
import com.example.chaozhou.R;
import com.example.chaozhou.ui.adapter.SearchResultAdapter;
import com.example.chaozhou.ui.search_result.SearchResultActivityMap;
import com.example.chaozhou.widget.CustomRecyclerView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends FragmentActivity implements PoiSearch.OnPoiSearchListener{
    private static final String TAG = SearchActivity.class.getName();
    @BindView(R.id.search_bar)  MaterialSearchBar mSearchBar;
    @BindView(R.id.recycler)
    CustomRecyclerView mRecycler;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;
    private SearchResultAdapter mSearchResultAdapter;
    private PoiSearch mPoiSearch;//POI搜索
    private PoiSearch.Query mPoitQuery;//POI查询条件类
    private InputMethodManager inputMethodManager;
    private String mCurrentCity;
    private String mCurrentData;

    public static void newInstance(Activity activity,String cityName,String data){

        Intent intent = new Intent(activity,SearchActivity.class);
        intent.putExtra("city_name",cityName);
        intent.putExtra("data",data);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        init();
        initListener();
    }

    private void init() {
        if (null != getIntent()){
            mCurrentCity = getIntent().getStringExtra("city_name");
            mCurrentData = getIntent().getStringExtra("data");
            Log.e(TAG,"mCurrentCity:" + mCurrentCity);
            Log.e(TAG,"mCurrentData:" + mCurrentData);
        }
        if (null == inputMethodManager)inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null == mSearchResultAdapter) mSearchResultAdapter = new SearchResultAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(mSearchResultAdapter);


    }

    private void initListener() {
        mSearchBar.enableSearch();


         mSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean b) {
                Log.e("onSearchStateChanged","1");
            }

            @Override
            public void onSearchConfirmed(CharSequence charSequence) {

                System.out.println("222"+mCurrentCity);

             mPoitQuery = new PoiSearch.Query(String.valueOf(charSequence),"",mCurrentCity);
            //  mPoitQuery.setPageSize(Integer.MAX_VALUE);
              //  mPoitQuery.setPageNum(1);
                mPoitQuery.setPageSize(1);// 设置每页最多返回多少条poiitem
                mPoitQuery.setPageNum(0);// 设置查第一页
                mPoitQuery.setCityLimit(true);
                mPoiSearch = new PoiSearch(SearchActivity.this,mPoitQuery);
                mPoiSearch.setOnPoiSearchListener(SearchActivity.this);
               // mPoiSearch.setBound(new PoiSearch.SearchBound(lp, 10000, true));//
                mPoiSearch.searchPOIAsyn();
                mProgressBar.setVisibility(View.VISIBLE);
                if (null != inputMethodManager){//隐藏软件盘
                    inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                }

            }

            @Override
            public void onButtonClicked(int i) {

            }

        });
        mSearchResultAdapter.setOnItemClickListener((view, poiItem, position) -> {
            SearchResultActivityMap.newInstance(SearchActivity.this,poiItem);

            finish();
        });

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        if (rCode == 0) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(mPoitQuery)) {// 是否是同一条
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    mSearchResultAdapter.setList(poiItems);
                    mSearchResultAdapter.notifyDataSetChanged();
                    mRecycler.scheduleLayoutAnimation();//执行Item进入动画
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(AndroidApplication.getContext(), "对不起,没有搜索到相关数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AndroidApplication.getContext(), "对不起,没有搜索到相关数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onPoiItemSearched(PoiItem poiItem, int rCode) {

    }
}

