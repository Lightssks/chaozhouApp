package com.example.chaozhou.ui.search_result;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.chaozhou.AndroidApplication;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.ContrastId;
import com.example.chaozhou.api.bean.HotelInfo;
import com.example.chaozhou.api.bean.PlaceInfo;
import com.example.chaozhou.api.bean.PoiItemBean;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.base.MapBaseActivity;
import com.example.chaozhou.ui.adapter.InfoWinAdapter;
import com.example.chaozhou.ui.route_plan.RoutePlanActivityMap;
import com.example.chaozhou.ui.search.SearchActivity;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;

/**
 * Created by mouren on 2017/2/28.
 */

public class SearchResultActivityMap extends MapBaseActivity implements LocationSource,AMapLocationListener,PoiSearch.OnPoiSearchListener, AMap.OnMarkerClickListener,AMap.OnMapClickListener{
    private static final String TAG = SearchResultActivityMap.class.getName();
    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.search_bar) MaterialSearchBar mSearchBar;
    @BindView(R.id.tv_name) TextView mName;
    @BindView(R.id.tv_distance) TextView mDistance;
    @BindView(R.id.tv_address) TextView mAddress;
    @BindView(R.id.fab_location)
    FloatingActionButton mFabLocation;
    @BindView(R.id.fab_to_where)
    FloatingActionButton mFabToWhere;
    private AMap mAMap;//地图控制类
    private PoiItem mPoiItem;
    private UiSettings mUiSettings;//操作控件类
    private OnLocationChangedListener mLocationChangedListener;//定位回调监听
    private AMapLocationClient mLocationClient;//AMapLocationClient类对象
    private AMapLocationClientOption mLocationOption;//定位参数对象
    private InfoWinAdapter adapter;
    private Marker oldMarker;
    private Marker mLocationMarker;
    private String mMyLocationAdress;
    private LatLng myLatLng;
    private LatLng destnationLatLng;
    private LatLonPoint mStartPoint;//起点
    private LatLonPoint mEndPoint ;//终点
    private DecimalFormat decimalFormat;//保留小数点用的
    private String mCurrentCityName;
    private PoiSearch.Query mQuery;
    private PoiSearch mPoiSearch;
    private String contrastId;
    private String Title;
    List<PoiItemBean> poiItemList = new ArrayList<PoiItemBean>();
    public static void newInstance(Activity activity, PoiItem poiItem){
        Intent intent = new Intent(activity,SearchResultActivityMap.class);
        intent.putExtra("poiItem",poiItem);
        activity.startActivity(intent);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_route_detail;
    }

    @Override
    public void init(Bundle savedInstanceStae) {
        if (null != getIntent()){
        mPoiItem = getIntent().getParcelableExtra("poiItem");
        }
        destnationLatLng = new LatLng(mPoiItem.getLatLonPoint().getLatitude(),mPoiItem.getLatLonPoint().getLongitude());
        mEndPoint = new LatLonPoint(mPoiItem.getLatLonPoint().getLatitude(),mPoiItem.getLatLonPoint().getLongitude());

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mMapView.onCreate(savedInstanceState);//创建地图
        if (null == mAMap) mAMap = mMapView.getMap();//获取地图控制类
        if (null == mUiSettings && null != mAMap){
            mUiSettings = mAMap.getUiSettings();//获取操作控件类
            mUiSettings.setScaleControlsEnabled(false);//是否显示比例尺控件
            mUiSettings.setZoomControlsEnabled(true);//是否显示缩放按钮
            mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
            mUiSettings.setLogoLeftMargin(getWindowManager().getDefaultDisplay().getWidth());//隐藏高德地图的Logo
        }
         mSearchBar.setVisibility(View.GONE);
        mAMap.showMapText(true);
        mAMap.setMapType(AMap.MAP_TYPE_NORMAL);

        //定位
        mAMap.setLocationSource(this);//设置定位监听
        mUiSettings.setMyLocationButtonEnabled(false);//设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mAMap.setOnMapClickListener(this);
        mAMap.setOnMarkerClickListener(this);
        mAMap.clear();
        MarkerOptions markerOptions =  new MarkerOptions()
                .position(destnationLatLng)
                .title(mPoiItem.getTel()).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.poi)));
        mAMap.addMarker(markerOptions);  //添加marker
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mPoiItem.getLatLonPoint().getLatitude(),mPoiItem.getLatLonPoint().getLongitude()),12));//级别在3~21之间，数字越大，地图越详细，地图缩放级别
        //显示所选位置的信息
        if (null != mPoiItem){
            mName.setText(mPoiItem.getTitle());
     //       mAddress.setText(mPoiItem.getProvinceName()+mPoiItem.getCityName()+mPoiItem.getAdName()+mPoiItem.getSnippet());
            mAddress.setText(mPoiItem.getProvinceName()+mPoiItem.getCityName()+mPoiItem.getSnippet());
            Log.e(mPoiItem.getTitle(),mPoiItem.getProvinceName()+mPoiItem.getCityName()+mPoiItem.getAdName()+mPoiItem.getSnippet());
        }
        adapter = new InfoWinAdapter();
        mAMap.setInfoWindowAdapter(adapter);
        mQuery = new PoiSearch.Query("","",mCurrentCityName);
        //  mPoitQuery.setPageSize(Integer.MAX_VALUE);
        //  mPoitQuery.setPageNum(1);
      //  mQuery.setPageSize(1);// 设置每页最多返回多少条poiitem
     //   mQuery.setPageNum(0);// 设置查第一页
        mQuery.setPageSize(10);
        mQuery.setPageNum(1);
        mQuery.setCityLimit(true);
        mPoiSearch = new PoiSearch(SearchResultActivityMap.this,mQuery);
        mPoiSearch.setOnPoiSearchListener(SearchResultActivityMap.this);
        LatLonPoint latLonPoint = new LatLonPoint(mPoiItem.getLatLonPoint().getLatitude(),mPoiItem.getLatLonPoint().getLongitude());
        mPoiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 10000, true));//基于latLonPoint范围内搜索POI
        mPoiSearch.searchPOIAsyn();//异步搜索
      /*  mAMap.addMarker(new MarkerOptions().position(destnationLatLng).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.drawable.amap_bus)))
                .draggable(true));*/
    }



    @Override
    public void initListener() {
        mSearchBar.setOnClickListener(v -> startActivity(new Intent(SearchResultActivityMap.this,SearchActivity.class)));
        mFabLocation.setOnClickListener(v -> {
            if (null != myLatLng){
                if (null == mLocationMarker && null != myLatLng){
                    MarkerOptions markerOptions =  new MarkerOptions()
                            .position(myLatLng)
                            .title(mMyLocationAdress).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                    .decodeResource(getResources(),R.mipmap.my_location)));
                    mLocationMarker = mAMap.addMarker(markerOptions);
                }else {
                    mLocationMarker.setPosition(myLatLng);
                }
                mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng,15));
            }
        });
        //导航
        mFabToWhere.setOnClickListener(v -> {

             RoutePlanActivityMap.newInstance(this,mStartPoint,mEndPoint,mPoiItem,mCurrentCityName,"search_result");

        });

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mMapView) {
            mMapView.onDestroy();
        }
        if(null != mLocationClient){
            mLocationClient.onDestroy();
        }
    }


    @Override//定位回调监听器
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != mLocationChangedListener && null != aMapLocation){
            if (null != aMapLocation && aMapLocation.getErrorCode() == 0){
                mMyLocationAdress = aMapLocation.getAddress();
                myLatLng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                mStartPoint = new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());
                mCurrentCityName = aMapLocation.getCity();
                System.out.println("111"+mCurrentCityName);
                if (null != myLatLng && null != destnationLatLng){
                    decimalFormat = new DecimalFormat(".0");
                    mDistance.setText(decimalFormat.format(AMapUtils.calculateLineDistance(myLatLng,destnationLatLng)/1000) +"公里");
                }
//                mLocationChangedListener.onLocationChanged(aMapLocation);//显示系统的小圆点

            }else {
                Toast.makeText(AndroidApplication.getContext(),"定位失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override//设置定位初始化以及启动定位,激活定位
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationChangedListener = onLocationChangedListener;
        if (null == mLocationClient){
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mLocationClient.setLocationListener(this);//设置定位监听
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置高精度定位模式
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    @Override//停止定位的相关回调
    public void deactivate() {
        mLocationChangedListener = null;
        if (null != mLocationClient){
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        sendRequestWithOkHttp("http://193.112.86.153/map/all");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("poiItemList.size()"+poiItemList.size());
        for (PoiItemBean poiItem : poiItemList) {
           /* contrastId = getMarkerContrastId(poiItem);
            Logger.d("DO After GetMarkerContrastId");
            Title = getMakerTitle(contrastId);
            Logger.d("Do After GetMarkerTitle");*/
            LatLng latLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
            addMarkerToMap(latLng,poiItem.getTitle(),poiItem.getSnippet(),poiItem.getPoiId());
        }
    }

    public String  getMarkerContrastId(PoiItemBean poiItem) {
        String mid = poiItem.getPoiId();
        ReadService.getContrastId(mid)
                .subscribe(new Subscriber<ContrastId>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(ContrastId contrastIds) {
                        contrastId = contrastIds.getData().getContrastId();
                        Logger.d("GetContrastId "+contrastId);
                    }
                });
        return contrastId;
    }

    public String getMakerTitle(String contrastId) {
        String type = contrastId.substring(0,2);
        Logger.d("GetContrastId "+type);
        if (contrastId.length() != 0) {
            type = contrastId.substring(0,2);
            Logger.d("GetContrastId "+type);
            switch (type) {
                case "20":
                    ReadService.getHotelInfo(contrastId)
                            .subscribe(new Subscriber<HotelInfo>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Logger.e(e.toString());
                                }

                                @Override
                                public void onNext(HotelInfo hotelInfo) {
                                    Title = hotelInfo.getHotname();
                                }
                            });
                    break;
                case "40":
                    ReadService.getPlaceInfo(contrastId)
                            .subscribe(new Subscriber<PlaceInfo>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Logger.e(e.toString());
                                }

                                @Override
                                public void onNext(PlaceInfo placeInfo) {
                                    Title = placeInfo.getToutitle();
                                }
                            });
                    break;
                case "30":
                    ReadService.getShopName(contrastId)
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Logger.e(e.toString());
                                }

                                @Override
                                public void onNext(String s) {
                                    Title = s;
                                }
                            });
                    break;
            }
        }
        Logger.d("Mapper " + Title);
        return Title;
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
    //获取JSON数据
    private void sendRequestWithOkHttp(String string) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(string)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                     parseJSONWithJSONObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();

    }
    //解析JSON数据，范围POIList，PoiItemBean（midID,经纬度，店名，详细地址）
  private List<PoiItemBean> parseJSONWithJSONObject(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (!jsonObject.isNull("mlongitude")&&!jsonObject.isNull("mlatitude")){
                    LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(jsonObject.getString("mlatitude")), Double.valueOf(jsonObject.getString("mlongitude")));
                    PoiItemBean poitem = new PoiItemBean(String.valueOf(jsonObject.get("mid")), latLonPoint, jsonObject.getString("logo"), jsonObject.getString("marea"));
                    System.out.println(poitem.getPoiId() + ":" + poitem.getLatLonPoint() + ":" + poitem.getTitle() + ":" + poitem.getSnippet());
                    poiItemList.add(poitem);//22 30 36 38
                   // sendRequestWithOkHttpForID("http://193.112.86.153/map/info/", poitem.getPoiId());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return poiItemList;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图上没marker 的地方，隐藏inforwindow
        System.out.println("onMapClick:"+oldMarker);
        if (oldMarker != null) {
            System.out.println(oldMarker+"!=null");
        //    oldMarker.hideInfoWindow();
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.amap_through));
        }else {
            System.out.println(oldMarker+"==null");
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.e("marker","onclick");
        if (!marker.getPosition().equals(myLatLng)){ //点击的marker不是自己位置的那个marker
            if (oldMarker != null) {
                Log.e("marker","self");
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.amap_through));
            }
            oldMarker = marker;
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_selected));
        }

        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }
    //添加Marker方法
    private void addMarkerToMap(LatLng latLng, String title, String snippet, String mid) {
        mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(latLng)
                .title(mid+" "+title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.amap_through))
        );

    }
}



