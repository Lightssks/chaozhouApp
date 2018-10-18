package com.example.chaozhou.ui.route_plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.example.chaozhou.AndroidApplication;
import com.example.chaozhou.Constant;
import com.example.chaozhou.R;
import com.example.chaozhou.event.EventPoint;
import com.example.chaozhou.module.base.MapBaseActivity;
import com.example.chaozhou.ui.adapter.RoutePlanBusAdapter;
import com.example.chaozhou.ui.detail.BusRouteDetailActivityMap;
import com.example.chaozhou.ui.detail.DriveRouteDetailActivityMap;
import com.example.chaozhou.ui.detail.RideRouteDetailActivityMap;
import com.example.chaozhou.ui.detail.WalkRouteDetailActivityMap;
import com.example.chaozhou.ui.search.RoutePlanSearchActivityMap;
import com.example.chaozhou.utils.AMapUtil;
import com.example.chaozhou.utils.DrivingRouteOverlay;
import com.example.chaozhou.utils.RideRouteOverlay;
import com.example.chaozhou.utils.SPUtil;
import com.example.chaozhou.utils.WalkRouteOverlay;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by dingmouren on 2017/3/1.
 */

public class RoutePlanActivityMap extends MapBaseActivity implements AMap.OnMapClickListener, AMap.OnMarkerClickListener
        , AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener
        , LocationSource, AMapLocationListener {
    private static final String TAG = RoutePlanActivityMap.class.getName();
    @BindView(R.id.edit_start)
    MaterialEditText mEditStart;
    @BindView(R.id.edit_end)
    MaterialEditText mEditEnd;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.img_return)
    ImageView mImgReturn;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.route_map)
    MapView mMapView;
    @BindView(R.id.bottom_info)
    RelativeLayout mBottomInfo;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.firstline)
    TextView mFirstLine;
    @BindView(R.id.secondline)
    TextView mSeconLine;
    @BindView(R.id.detail)
    LinearLayout mDetail;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_logo)
    TextView mTvLogo;
    @BindView(R.id.root_layout)
    RelativeLayout mRootLayout;

    private AMap mAMap;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private BusRouteResult mBusRouteResult;
    private WalkRouteResult mWalkRouteResult;
    private RideRouteResult mRideRouteResult;
    private LatLonPoint mStartPoint;//起点，
    private LatLonPoint mEndPoint;//终点，
    private String mCurrentCityName;
    private final int ROUTE_TYPE_DRIVE = 0;
    private final int ROUTE_TYPE_BUS = 1;
    private final int ROUTE_TYPE_WALK = 2;
    private final int ROUTE_TYPE_RIDE = 3;
    private RoutePlanBusAdapter mBusAdapter;
    private UiSettings mUiSetting;
    private int animatorX ,animatorY;//动画开始和结束的坐标

    //定位
    private OnLocationChangedListener mLocationChangedListener;//定位回调监听
    private AMapLocationClient mLocationClient;//AMapLocationClient类对象
    private AMapLocationClientOption mLocationOption;//定位参数对象
    private String mCurrentCityCode;
    private String mTargetCityCode;
    private PoiItem mPoiItem;
    private static int flag=0;
    private static int flag2=1;
    private String mTag;//标记  是否隐藏软键盘
    public static String[] ways = new String[]{"驾车", "公交", "步行", "骑行"};

    public static void newInstance(Activity activity, LatLonPoint startPoint, LatLonPoint endPoint, PoiItem poiItem, String cityName, String tag) {
        Intent intent = new Intent(activity, RoutePlanActivityMap.class);

        intent.putExtra("start_point", startPoint);
        intent.putExtra("end_point", endPoint);
        intent.putExtra("poiItem", poiItem);
        intent.putExtra("city_name", cityName);
        intent.putExtra("tag", tag);
      //  intent.putExtra("mTargetCityCode","0768");
        intent.putExtra("mTargetCityCode","0768");
        activity.startActivity(intent);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_route_plan;
    }

    @Override
    public void init(Bundle savedInstanceStae) {
        if (null != getIntent()) {
            mStartPoint = getIntent().getParcelableExtra("start_point");
            mEndPoint = getIntent().getParcelableExtra("end_point");
            mCurrentCityName = getIntent().getStringExtra("city_name");
            mPoiItem = getIntent().getParcelableExtra("poiItem");
            mTag = getIntent().getStringExtra("tag");
            mTargetCityCode = getIntent().getStringExtra("mTargetCityCode");
           /* if (null != mPoiItem) {
                mTargetCityCode = mPoiItem.getCityCode();
            }*/
            System.out.println("1"+mStartPoint.getLatitude()+"/"+mStartPoint.getLongitude());
            System.out.println("2"+mEndPoint.getLatitude()+"/"+mEndPoint.getLongitude());
            System.out.println("3"+mPoiItem.getCityName()+" "+mPoiItem.getProvinceName()+" "+mPoiItem.getTitle());
            System.out.println("4"+mCurrentCityName);
            System.out.println("5"+mTargetCityCode);
            System.out.println("6"+mCurrentCityCode);

        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        animatorX = (int) SPUtil.get(AndroidApplication.getContext(), Constant.REVEAL_CENTER_X,this.getWindowManager().getDefaultDisplay().getWidth());//默认值是屏幕宽度
        animatorY = (int) SPUtil.get(AndroidApplication.getContext(),Constant.REVEAL_CENTER_Y,this.getWindowManager().getDefaultDisplay().getHeight());//默认值是屏幕高度
        EventBus.getDefault().register(this);
        mMapView.onCreate(savedInstanceState);
        if (null == mAMap) mAMap = mMapView.getMap();
        if (null == mUiSetting && null != mAMap) {
            mUiSetting = mAMap.getUiSettings();
            mUiSetting.setLogoLeftMargin(getWindowManager().getDefaultDisplay().getWidth());//隐藏高德地图的Logo
        }

        mRouteSearch = new RouteSearch(this);
        drawStartEnd();
        //定位
        mAMap.setLocationSource(this);
        mAMap.setMyLocationEnabled(true);
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        for (int i = 0; i < ways.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(ways[i]));
        }
       // mTabLayout.setScrollPosition(1, 0, true);//滑动到公交路线
        mTabLayout.setScrollPosition(0, 0, true);//滑动到驾车路线
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        if (mTag.equals("search_result")) {//焦点
            mEditStart.setFocusable(false);
            mEditEnd.setFocusable(false);
        } else {
            mEditStart.setText("输入起点", TextView.BufferType.NORMAL);
            mEditStart.setFocusable(true);
            mEditEnd.setText("目的地", TextView.BufferType.NORMAL);
            mEditEnd.setFocusable(true);
        }
        if (null != mPoiItem && mPoiItem.getTitle() != null) {
            mEditEnd.setText(mPoiItem.getTitle(), TextView.BufferType.NORMAL);

        }

    }


    @Override
    public void initListener() {
        if (null != mRouteSearch)
            mRouteSearch.setRouteSearchListener(this);
        mAMap.setOnMapClickListener(RoutePlanActivityMap.this);
        mAMap.setOnMarkerClickListener(RoutePlanActivityMap.this);
        mAMap.setOnInfoWindowClickListener(RoutePlanActivityMap.this);
        mAMap.setInfoWindowAdapter(RoutePlanActivityMap.this);

        mImgBack.setOnClickListener(v -> onBackPressed());
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case ROUTE_TYPE_DRIVE:
                        System.out.println("hhhh");
                        mMapView.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                        mBottomInfo.setVisibility(View.VISIBLE);
                        mTvLogo.setVisibility(View.VISIBLE);
                        flag = 0;
                        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
                        break;
                    case ROUTE_TYPE_BUS:
                        mTvLogo.setVisibility(View.GONE);
                        flag = 1;
                        searchBusRoute();
                        break;
                    case ROUTE_TYPE_WALK:
                        checkStartAndEndPoint();
                        if (!mCurrentCityCode.equals(mTargetCityCode)&&mStartPoint!=null && mEndPoint != null) {
                            System.out.println(mCurrentCityCode+"  "+mTargetCityCode+"  "+mStartPoint+" "+mEndPoint);
                            mMapView.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.GONE);
                            mBottomInfo.setVisibility(View.GONE);
                            mTvLogo.setVisibility(View.GONE);
                            Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mMapView.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                        mBottomInfo.setVisibility(View.VISIBLE);
                        mTvLogo.setVisibility(View.VISIBLE);
                        flag=2;
                        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WALK_DEFAULT);
                        break;
                    case ROUTE_TYPE_RIDE:
                        checkStartAndEndPoint();
                        if (!mCurrentCityCode.equals(mTargetCityCode)&&mStartPoint!=null && mEndPoint != null) {
                            System.out.println(mCurrentCityCode+"  "+mTargetCityCode+"  "+mStartPoint+" "+mEndPoint);
                            mMapView.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.GONE);
                            mBottomInfo.setVisibility(View.GONE);
                            mTvLogo.setVisibility(View.GONE);
                            Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mMapView.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                        mBottomInfo.setVisibility(View.VISIBLE);
                        mTvLogo.setVisibility(View.VISIBLE);
                        flag=3;
                        searchRouteResult(ROUTE_TYPE_RIDE, RouteSearch.RIDING_DEFAULT);

                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mEditStart.setOnClickListener(v -> RoutePlanSearchActivityMap.newInstance(RoutePlanActivityMap.this, "start",mCurrentCityName));
        mEditEnd.setOnClickListener(v -> RoutePlanSearchActivityMap.newInstance(RoutePlanActivityMap.this, "end",mCurrentCityName));

    }

    @Override
    public void initData() {

       // searchBusRoute();//初始化公交车路线数据
        searchDriverRoute();//初始化驾车路线数据
    }

    private void searchDriverRoute() {
        mMapView.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
        mBottomInfo.setVisibility(View.VISIBLE);
        mTvLogo.setVisibility(View.VISIBLE);
        flag = 0;
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
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
    }


  /*  @Override
    public void onBackPressed() {
     revealAnimatorUtil.startRevealAnimator(true,animatorX,animatorY);
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 公交路线搜索
     */
    private void searchBusRoute() {
        mMapView.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mBottomInfo.setVisibility(View.GONE);
        searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BUS_LEASE_WALK);
    }

    /**
     * 开始搜索路线
     */
    private void searchRouteResult(int routeType, int mode) {
        checkStartAndEndPoint();
        mProgressBar.setVisibility(View.VISIBLE);
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
        switch (routeType) {
            case ROUTE_TYPE_DRIVE:
                //第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null, null, "");
                mRouteSearch.calculateDriveRouteAsyn(driveRouteQuery);
                break;
            case ROUTE_TYPE_BUS:
                Log.e(TAG, "mCurrentCityName:" + mCurrentCityName);
                RouteSearch.BusRouteQuery busRouteQuery = new RouteSearch.BusRouteQuery(fromAndTo, mode, mCurrentCityName, 0);//0表示不计算夜班车
                mRouteSearch.calculateBusRouteAsyn(busRouteQuery);
                break;
            case ROUTE_TYPE_WALK:
                RouteSearch.WalkRouteQuery walkRouteQuery = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
                mRouteSearch.calculateWalkRouteAsyn(walkRouteQuery);
                break;
            case ROUTE_TYPE_RIDE:
                RouteSearch.RideRouteQuery rideRouteQuery = new RouteSearch.RideRouteQuery(fromAndTo, mode);
                mRouteSearch.calculateRideRouteAsyn(rideRouteQuery);
                break;
        }
    }


    @Override//OnMapClickListener
    public void onMapClick(LatLng latLng) {

    }

    @Override//OnMarkerClickListener
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override//OnInfoWindowClickListener
    public void onInfoWindowClick(Marker marker) {

    }

    @Override//InfoWindowAdapter-1
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override//InfoWindowAdapter-2
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override//公交路线搜索结果方法回调
    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        mProgressBar.setVisibility(View.GONE);
        mBottomInfo.setVisibility(View.GONE);
        mAMap.clear();//清空地图上的覆盖物
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mBusRouteResult = result;
                    mBusAdapter = new RoutePlanBusAdapter(mBusRouteResult);
                    //公交车详线路情
                    mBusAdapter.setOnItemClickListener((view, busPath, busRouteResult, position) -> {
                        BusRouteDetailActivityMap.newInstance(RoutePlanActivityMap.this, busPath, busRouteResult);
                    });
                    mRecycler.setAdapter(mBusAdapter);

                } else if (result != null && result.getPaths().size() == 0) {
                    Toast.makeText(AndroidApplication.getContext(), " 没有搜索到相关数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AndroidApplication.getContext(), " 没有搜索到相关数据", Toast.LENGTH_SHORT).show();

            }
        } else {


        }
    }

    @Override//驾车路线搜索结果回调
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int rCode) {
        mProgressBar.setVisibility(View.GONE);
        mAMap.clear();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths().get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            AndroidApplication.getContext(), mAMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    mFirstLine.setText(des);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    if (taxiCost != 0) {
                        mSeconLine.setVisibility(View.VISIBLE);
                        mSeconLine.setText("打车约" + taxiCost + "元");
                    }
                    mBottomInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DriveRouteDetailActivityMap.newInstance(RoutePlanActivityMap.this, drivePath, mDriveRouteResult);
                        }
                    });
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                    Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();
            }
        } else {
        }
    }

    @Override//步行路线搜索结果回调
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int rCode) {
        mProgressBar.setVisibility(View.GONE);
        mAMap.clear();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (walkRouteResult != null && walkRouteResult.getPaths() != null) {
                if (walkRouteResult.getPaths().size() > 0) {
                    mWalkRouteResult = walkRouteResult;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            this, mAMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    mFirstLine.setText(des);
                    mSeconLine.setVisibility(View.GONE);
                    mBottomInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WalkRouteDetailActivityMap.newInstance(RoutePlanActivityMap.this, walkPath, mWalkRouteResult);
                        }
                    });
                } else if (walkRouteResult != null && walkRouteResult.getPaths() == null) {
                    Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();

            }
        } else {
        }
    }

    @Override//骑行路线搜索结果回调
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int rCode) {
        mProgressBar.setVisibility(View.GONE);
        mAMap.clear();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (rideRouteResult != null && rideRouteResult.getPaths() != null) {
                if (rideRouteResult.getPaths().size() > 0) {
                    mRideRouteResult = rideRouteResult;
                    final RidePath ridePath = mRideRouteResult.getPaths()
                            .get(0);
                    RideRouteOverlay rideRouteOverlay = new RideRouteOverlay(
                            this, mAMap, ridePath,
                            mRideRouteResult.getStartPos(),
                            mRideRouteResult.getTargetPos());
                    rideRouteOverlay.removeFromMap();
                    rideRouteOverlay.addToMap();
                    rideRouteOverlay.zoomToSpan();
                    int dis = (int) ridePath.getDistance();
                    int dur = (int) ridePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    mFirstLine.setText(des);
                    mSeconLine.setVisibility(View.GONE);
                    mBottomInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RideRouteDetailActivityMap.newInstance(RoutePlanActivityMap.this, ridePath);
                        }
                    });
                } else if (rideRouteResult != null && rideRouteResult.getPaths() == null) {
                    Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AndroidApplication.getContext(), "没有搜索到相关数据", Toast.LENGTH_SHORT).show();
            }
        } else {
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != mLocationChangedListener && null != aMapLocation) {
            if (null != aMapLocation && aMapLocation.getErrorCode() == 0) {
                mCurrentCityCode = aMapLocation.getCityCode();

            } else {
                Toast.makeText(AndroidApplication.getContext(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationChangedListener = onLocationChangedListener;
        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mLocationClient.setLocationListener(this);//设置定位监听
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置高精度定位模式
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mLocationChangedListener = null;
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    /**
     * 添加起点和终点的Marker
     */
    private void drawStartEnd() {
        if (null != mStartPoint && null != mEndPoint) {
            mAMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(mStartPoint)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.start)));
            mAMap.addMarker(new MarkerOptions().position(AMapUtil.convertToLatLng(mEndPoint)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.end)));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getStartOrEnd(EventPoint eventPoint) {
        if (eventPoint.getTag() == 0) {
            mEditStart.setText(eventPoint.getTitle(), TextView.BufferType.NORMAL);
            mStartPoint = eventPoint.getLatLonPoint();
            mCurrentCityCode = eventPoint.getCityCode();
            //Log.e(TAG, "startCode:" + mCurrentCityCode);
            System.out.println("startCode:" + mCurrentCityCode);

        } else if (eventPoint.getTag() == 1) {
            mEditEnd.setText(eventPoint.getTitle(), TextView.BufferType.NORMAL);
            mEndPoint = eventPoint.getLatLonPoint();
            mTargetCityCode = eventPoint.getCityCode();
         //   Log.e(TAG, "targetCode:" + mTargetCityCode);
            System.out.println("targetCode:" + mTargetCityCode);
            drawStartEnd();
           // searchBusRoute();
           // mTabLayout.setScrollPosition(1, 0, true);//滑动到公交路线
        }

        switch (flag){
            case 0:
                searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DRIVING_SINGLE_DEFAULT);
                mTabLayout.setScrollPosition(0, 0, true);//滑动到驾车路线
                break;
            case 1:
                searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BUS_LEASE_WALK);
                mTabLayout.setScrollPosition(1, 0, true);//滑动到公交路线
                break;
            case 2:
                searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WALK_DEFAULT);
                mTabLayout.setScrollPosition(2, 0, true);//滑动到步行路线
                break;
            case 3:
                searchRouteResult(ROUTE_TYPE_RIDE, RouteSearch.RIDING_DEFAULT);
                mTabLayout.setScrollPosition(3, 0, true);//滑动到骑行路线
                break;
        }
    }

    /**
     * 检查起点和终点是不是为空
     */
    private void checkStartAndEndPoint() {
        if (null == mStartPoint) {
            Toast toast = Toast.makeText(AndroidApplication.getContext(), "请设置起点", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
        if (null == mEndPoint) {
            Toast toast =Toast.makeText(AndroidApplication.getContext(), "请设置终点", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }
    }
}
