package com.example.chaozhou.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.example.chaozhou.api.LBSInterface;
import com.example.chaozhou.ui.search_result.SearchResultActivityMap;
import com.orhanobut.logger.Logger;

public class MainActivity2 extends Activity implements LBSInterface {

    private String latitude;
    private String longitude;
    private String title;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  PoiItem poiItem = getPoiItemParams(this,String.valueOf(23.6539150000),String.valueOf(116.6678620000),"彩虹牛肉店","韩师学子客运站(公交站)");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        title = getIntent().getStringExtra("title");
        address = getIntent().getStringExtra("address");

        Logger.d("MainActivity2 poiItem "+latitude+longitude+title+address);

        PoiItem poiItem = getPoiItemParams(this,latitude,longitude,title,address);

        SearchResultActivityMap.newInstance(this,poiItem);
        finish();
        }

    public static void newInstance(Context context, String latitude, String longitude, String title, String address){
        Intent intent = new Intent(context,MainActivity2.class);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        intent.putExtra("title",title);
        intent.putExtra("address",address);
        context.startActivity(intent);
    }

    @Override
    public PoiItem getPoiItemParams(Activity activity, String latitude, String longitude, String title, String address) {
        LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(latitude),Double.valueOf(longitude));

        PoiItem mPoiItem = new PoiItem("",latLonPoint,title,address);
        mPoiItem.setProvinceName("广东");
        mPoiItem.setCityName("潮州");

        return mPoiItem;
    }
}