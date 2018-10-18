package com.example.chaozhou.module.personal_Info.list;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.adapter.User_info.UserShareAdapter;
import com.example.chaozhou.api.bean.MyShare;
import com.example.chaozhou.api.service.UploadService;
import com.example.chaozhou.local.Dao.MyDatabaseHelper;
import com.example.chaozhou.module.home.MainActivity;
import com.example.chaozhou.module.share.Topic;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class Share_ListActivity extends AppCompatActivity {

    private UserShareAdapter adapter;
    private List<Topic> Topic_list = new ArrayList<Topic>();
    private MyDatabaseHelper dbHelper= new MyDatabaseHelper(this);
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share__list);
        final SQLiteDatabase db =dbHelper.getWritableDatabase();
        Button title_left = findViewById(R.id.title_left);
        title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title_centent = findViewById(R.id.title_center);
        title_centent.setText("我的分享");
        getLocalUserInfo(db);

        adapter = new UserShareAdapter(Share_ListActivity.this,Topic_list);
        final ListView imageList = findViewById(R.id.list_share);
        imageList.setAdapter(adapter);
        init();

    }


    public void getLocalUserInfo(SQLiteDatabase db){

        Cursor cursor = db.query("user",null,null,null,null,null,null);
        if (cursor.moveToFirst()){

            Long Uid = cursor.getLong(cursor.getColumnIndex("uid"));
//            id = cursor.getString(cursor.getColumnIndex("phonenumber"));


            id = String.valueOf(Uid);


        }

    }
    public void init(){
        UploadService.getMyShareinfo(MainActivity.user.getUid())

                .subscribe(new  Subscriber<MyShare>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.e(throwable.toString() + " 1234" );


                    }

                    @Override
                    public void onNext(MyShare info) {
//                        mView.loadData(shareListInfos);
                        if (info.getStatus()==0){
                            Logger.d("获取个人分享成功！");
                        }else {
                            Logger.d("获取个人分享失败！");
                        }
                        Logger.d(info.getData().toString());
                        for (int i = info.getData().size()-1;i>=0;i--){
                            adapter.addShareList(info.getData().get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
