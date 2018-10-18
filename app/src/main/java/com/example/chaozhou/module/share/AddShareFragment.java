package com.example.chaozhou.module.share;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.ShareResponse;
import com.example.chaozhou.api.service.UploadService;
import com.example.chaozhou.local.Dao.MyDatabaseHelper;
import com.example.chaozhou.local.table.User;
import com.example.chaozhou.module.home.MainActivity;
import com.example.chaozhou.utils.ImageTool;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class AddShareFragment extends AppCompatActivity {
    private static final int IMG_COUNT = 8;
    private static final String IMG_ADD_TAG = "a";

    private MyDatabaseHelper dbHelper= new MyDatabaseHelper(this);
    private ContentValues values = new ContentValues();

    private GridView gridView;
    private GVAdapter adapter;
    private TextView textView;
    private TextView titleView;
    private EditText share_content;
    private ImageView img;
    private List<String> list;
    private Button leftButton;
    private Topic topic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_add);
        final SQLiteDatabase db =dbHelper.getWritableDatabase();

        leftButton = (Button) findViewById(R.id.title_left);
        titleView  = (TextView) findViewById(R.id.title_center);
        titleView.setText("分享");
        leftButton.setText("返回");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = (GridView) findViewById(R.id.gridview);
        share_content = findViewById(R.id.share_content);
        textView = (TextView) findViewById(R.id.title_right);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("发送：" + Integer.toString(list.size() - 1));
                upLoad();
                topic.setShatext(share_content.getText().toString());
                Logger.d(share_content.getText().toString());
                for(int i =0;i<list.size();i++)
                if(list.get(i).equals(IMG_ADD_TAG)){
                    list.remove(i);
                }
                if(list.size()==0){
                    upLoadPicToNet2(topic);
                }else {
                    upLoadPicToNet(list,topic);
                }

                Logger.d(topic.getShauid().toString());
                finish();

            }
        });
        initData();
        initUserInfos();
    }

    private void initUserInfos(){

        User us = MainActivity.user;
        topic = new Topic(us.getUid(),us.getPhonenumber(),us.getHead(),us.getUname());

    }


    public void upLoadPicToNet(List<String> allpath, Topic topic) {
        List<MultipartBody.Part> parts = new ArrayList<>(allpath.size());

            for(String path : allpath){
                File file = new File(path);
                final RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                Logger.d("images"+file.getName());
                parts.add(part);
            }
            UploadService.shareinfo(parts,topic.getShauid(),topic.getShatext())
                    .subscribe(new Subscriber<ShareResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Logger.d(throwable.toString());
                            ToastUtils.showToast("服务器异常！");
                        }

                        @Override
                        public void onNext(ShareResponse shareResponse) {
                            Logger.d("status"+shareResponse.getStatus());
                            if(shareResponse.getStatus() == 0){
                                Logger.d("上传成功！");
                                ToastUtils.showToast("分享成功！");
                            }else {
                                Logger.d(shareResponse.toString());
                                Logger.d("上传失败！");
                                ToastUtils.showToast("分享失败！");
                            }
                        }

                    });



    }

    private void upLoadPicToNet2(Topic topic){
        UploadService.shareinfo2(topic.getShauid(),topic.getShatext())
                .subscribe(new Subscriber<ShareResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.d(throwable.toString());
                        ToastUtils.showToast("服务器异常！");
                    }

                    @Override
                    public void onNext(ShareResponse shareResponse) {
                        Logger.d("status="+ shareResponse.getStatus());
                        if(shareResponse.getStatus() == 0){
                            Logger.d("上传成功！");
                            ToastUtils.showToast("分享成功！");
                        }else {
                            Logger.d(shareResponse.toString());
                            Logger.d("上传失败！");
                            ToastUtils.showToast("分享失败！");
                        }
                    }

                });
    }


    private void upLoad() {
        Bitmap bitmap;
        Bitmap bmpCompressed;
        for (int i = 0; i < list.size() - 1; i++) {
            bitmap = BitmapFactory.decodeFile(list.get(i));
            bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            System.out.println(data);
        }

    }

    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
            list.add(IMG_ADD_TAG);
        }
        adapter = new GVAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).equals(IMG_ADD_TAG)) {
                    if (list.size() < IMG_COUNT) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 0);
                } else
                    Toast.makeText(AddShareFragment.this, "最多只能选择9张照片！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshAdapter();
    }

    private void refreshAdapter() {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (adapter == null) {
            adapter = new GVAdapter();
        }
        adapter.notifyDataSetChanged();
    }

    private class GVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplication()).inflate(R.layout.share_add_items, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String s = list.get(position);
            if (!s.equals(IMG_ADD_TAG)) {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
            } else {
                holder.checkBox.setVisibility(View.GONE);
                holder.imageView.setImageResource(R.drawable.logo_photo_white);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    refreshAdapter();
                }
            });
            return convertView;
        }

        private class ViewHolder {
            ImageView imageView;
            CheckBox checkBox;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {

            return;
        }
        if (requestCode == 0) {
            final Uri uri = data.getData();
            String path = ImageTool.getImageAbsolutePath(this, uri);
            if (list.size() == IMG_COUNT) {
                removeItem();
                refreshAdapter();
                return;
            }
            removeItem();
            list.add(path);
            list.add(IMG_ADD_TAG);
            refreshAdapter();
        }
    }

    private void removeItem() {
        if (list.size() != IMG_COUNT) {
            if (list.size() != 0) {
                list.remove(list.size() - 1);
            }
        }
    }

}