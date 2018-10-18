package com.example.chaozhou.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.example.chaozhou.AndroidApplication;
import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.JSONForID;
import com.example.chaozhou.api.bean.SpecId;
import com.example.chaozhou.api.service.ReadService;
import com.example.chaozhou.module.goods.detail.GoodsInfoActivity;
import com.example.chaozhou.module.hotel.detail.HotelInfoActivity;
import com.example.chaozhou.module.place.detail.PlaceInfoActivity;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;


/**
 * Created by Teprinciple on 2016/8/23.
 * 地图上自定义的infowindow的适配器
 */
public class InfoWinAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {
    private Context mContext = AndroidApplication.getContext();
    private LatLng latLng;
    private LinearLayout call;
    private LinearLayout navigation;
    private TextView nameTV;
    private String agentName;
    private TextView addrTV;
    private String snippet;
    private String poiId;
    private JSONForID jsonForID;
    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        agentName = marker.getTitle();
        poiId = agentName.split(" ")[0];
    }

    @NonNull
    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        navigation =  view.findViewById(R.id.navigation_LL);
        call =  view.findViewById(R.id.reduce);
        nameTV = view.findViewById(R.id.name);
        addrTV =  view.findViewById(R.id.addr);
        nameTV.setText(agentName);
        addrTV.setText(String.format(mContext.getString(R.string.agent_addr),snippet));
        navigation.setOnClickListener(this);
        call.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        sendRequestWithOkHttpForID("http://193.112.86.153/map/info/",poiId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (jsonForID.getContrastID().length() != 0) {
            String ContrastID = jsonForID.getContrastID();
            Logger.d("INFOAdapter "+ContrastID);
            String type = ContrastID.substring(0,2);
            Logger.d("INFOAdapter "+type);
            switch (type) {
                case "20":
                    ReadService.getGoodsId(ContrastID)
                            .subscribe(new Subscriber<SpecId>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Logger.e(e.toString());
                                }

                                @Override
                                public void onNext(SpecId specId) {
                                    HotelInfoActivity.launch(mContext,ContrastID);
                                    ((Activity)mContext).finish();
                                }
                            });
                    break;
                case "40":
                    ReadService.getGoodsId(ContrastID)
                            .subscribe(new Subscriber<SpecId>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Logger.e(e.toString());
                                }

                                @Override
                                public void onNext(SpecId specId) {
                                    PlaceInfoActivity.launch(mContext,ContrastID);
                                    ((Activity)mContext).finish();
                                }
                            });
                    break;
                case "30":
                    ReadService.getGoodsId(ContrastID)
                            .subscribe(new Subscriber<SpecId>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Logger.e(e.toString());
                                }

                                @Override
                                public void onNext(SpecId specId) {
                                    Logger.d("INFOAdapter "+specId.getData().get(0));
                                    if (specId.getData().size() != 0) {
                                        GoodsInfoActivity.launch(mContext,specId.getData().get(0));
                                        ((Activity)mContext).finish();
                                    }
                                }
                            });
                    break;
            }
        } else {
            ToastUtils.showToast("对不起，该店不见啦");
        }
    }
    //获取JSON数据
    private void sendRequestWithOkHttpForID(String string,String mid) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            ///    .url("http://193.112.86.153/test/maps")
                            //    .url("http://193.112.86.153/map/all")
                            .url(string+mid)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObjectForID(responseData,mid);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //解析JSON数据，返回jSONForIDList，poitem包括（"",经纬度，店名，详细地址）
    private JSONForID parseJSONWithJSONObjectForID(String jsonData, String mid){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if(jsonObject.getString("status").equals("0")){
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                jsonForID   = new JSONForID(jsonObject1.getString("mid"),jsonObject1.getString("contrastId"));
            }else if (jsonObject.getString("status").equals("-1")){
                jsonForID   = new JSONForID(mid,"");
            }
           return jsonForID;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonForID;
    }

}
