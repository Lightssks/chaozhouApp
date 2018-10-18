package com.example.chaozhou.ui.search;

import com.amap.api.services.core.LatLonPoint;
import com.example.chaozhou.api.bean.PoiItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class RequestJSONForPoiItem  {
    private  List<PoiItemBean> poiItemList = new ArrayList<PoiItemBean>();

    //获取JSON数据
    private void sendRequestWithOkHttp(String string) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            ///    .url("http://193.112.86.153/test/maps")
                            //    .url("http://193.112.86.153/map/all")
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
    //解析JSON数据，范围POIList，poitem包括（"",经纬度，店名，详细地址）
    private List<PoiItemBean> parseJSONWithJSONObject(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0;i<4;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
              /*  String mlongitude = jsonObject.getString("mlongitude");
                String mlatitude = jsonObject.getString("mlatitude");
                String marea = jsonObject.getString("marea");
                String mroad = jsonObject.getString("mroad");
                System.out.println("mlongitude:"+mlongitude+" mlatitude:"+mlatitude+" marea:"+marea+" mroad:"+mroad);*/
                LatLonPoint latLonPoint = new LatLonPoint(Double.valueOf(jsonObject.getString("mlatitude")),Double.valueOf(jsonObject.getString("mlongitude")));
                PoiItemBean poitem= new PoiItemBean("",latLonPoint,jsonObject.getString("logo"),jsonObject.getString("marea"));

                poiItemList.add(poitem);
                System.out.println(poiItemList.size());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return poiItemList;
    }
    private List<PoiItemBean> getPoiItemList(){
        return poiItemList;
    }
}
