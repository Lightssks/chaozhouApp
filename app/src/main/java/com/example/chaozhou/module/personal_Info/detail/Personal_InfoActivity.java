package com.example.chaozhou.module.personal_Info.detail;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaozhou.R;
import com.example.chaozhou.adapter.User_info.UserHeadAdapter;
import com.example.chaozhou.adapter.User_info.UserInfoAdapter;
import com.example.chaozhou.api.bean.BaseResponse;
import com.example.chaozhou.api.service.Log_RegService;
import com.example.chaozhou.api.service.UploadService;
import com.example.chaozhou.local.Dao.MyDatabaseHelper;
import com.example.chaozhou.local.table.Personal_Info;
import com.example.chaozhou.local.table.User;
import com.example.chaozhou.module.home.MainActivity;
import com.example.chaozhou.utils.BitmapUtil;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

public class Personal_InfoActivity extends AppCompatActivity {

    private List<Personal_Info> infoList1 = new ArrayList<>();
    private List<Personal_Info> infoList2 = new ArrayList<>();
    UserHeadAdapter headAdapter;
    UserInfoAdapter infoAdapter;
    private static int RESULT_LOAD_IMAGE = 1;
    private MyDatabaseHelper dbHelper= new MyDatabaseHelper(this);
    private ContentValues values = new ContentValues();
    private User info_us;
    private String path;

//    public static void launch(Context context) {
//        Intent intent = new Intent(context, Personal_InfoActivity.class);
//        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__info);
        final SQLiteDatabase db =dbHelper.getWritableDatabase();
        TextView title = findViewById(R.id.title_center);
        Button rightButton = findViewById(R.id.title_right);
        Button back_Button = findViewById(R.id.title_left);             //title 栏目的返回按钮及监听
        rightButton.setVisibility(View.GONE);
        title.setText("个人信息");
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        info_us = MainActivity.user;
        //initUserInfos(db);
        initInfos();//向listview 添加数据

        headAdapter = new UserHeadAdapter(Personal_InfoActivity.this, R.layout.user_head_item ,infoList2);
        final ListView listView1 = (ListView)findViewById(R.id.list_view1);
        listView1.setAdapter(headAdapter);
        headAdapter.notifyDataSetChanged();
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Personal_Info info = infoList2.get(position);
                if (ContextCompat.checkSelfPermission(Personal_InfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Personal_InfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else {
                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }

                headAdapter.notifyDataSetChanged();
           //     ToastUtils.showToast("头像");
                //ToastUtils.showToast(String.valueOf(position));

            }
        });

        infoAdapter = new UserInfoAdapter(Personal_InfoActivity.this,R.layout.user_info_item,infoList1);
        Logger.d(String.valueOf(infoAdapter.getCount()));
        final ListView listView2 = (ListView)findViewById(R.id.list_view2);
        listView2.setAdapter(infoAdapter);
        infoAdapter.notifyDataSetChanged();

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Personal_Info info = infoList1.get(position);

                switch (position){
                    case 0 :
                        final EditText et = new EditText(Personal_InfoActivity.this);

                        new AlertDialog.Builder(Personal_InfoActivity.this).setTitle("昵称")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setView(et)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = et.getText().toString();
                                        if (input.equals("")) {
                                            Toast.makeText(getApplicationContext(), "昵称不能为空！" + input, Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            info_us.setUname(input);
                                            executeInfo(db);
                                            updateInfo();
                                            updateUserInfo(info_us);
                                            infoAdapter.notifyDataSetChanged();
                                        }
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();

                        break;

                    case 1:

                        final Calendar c = Calendar.getInstance();
                        DatePickerDialog dialog = new DatePickerDialog(Personal_InfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                c.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String date = sdf.format(c.getTime());
                                info_us.setBirthday(date);
                                executeInfo(db);
                                updateInfo();
                                updateUserInfo(info_us);
                                infoAdapter.notifyDataSetChanged();
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
                        );
                        dialog.show();
                        break;

                    case 2:
                        new AlertDialog.Builder(Personal_InfoActivity.this)
                                .setTitle("性别")
                                .setSingleChoiceItems(new String[]{"男","女"}, -1, new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        switch (arg1) {
                                            case 0:
                                                info_us.setSex("男");
                                                break;
                                            case 1:
                                                info_us.setSex("女");
                                                break;

                                            default: break;
                                        }
                                        arg0.dismiss();
                                        executeInfo(db);
                                        updateInfo();
                                        updateUserInfo(info_us);
                                        infoAdapter.notifyDataSetChanged();
                                    }
                                })
                                .show();
                        break;

                    case 3:
                        final EditText et2 = new EditText(Personal_InfoActivity.this);

                        new AlertDialog.Builder(Personal_InfoActivity.this).setTitle("年龄")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setView(et2)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = et2.getText().toString();
                                        int ageequals = Integer.parseInt(input);
                                        if (ageequals>=0&&ageequals<=100){

                                        }
                                        if (input.equals("")) {
                                            Toast.makeText(getApplicationContext(), "年龄不能为空！" + input, Toast.LENGTH_LONG).show();
                                        }
                                        else if (ageequals>=0&&ageequals<=100){
                                            info_us.setAge(Integer.parseInt(input));;
                                            executeInfo(db);
                                            updateInfo();
                                            updateUserInfo(info_us);
                                            infoAdapter.notifyDataSetChanged();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "输入非法字符！" + input, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .show();

                        break;

                }

            }
        });

    }

    private void initInfos(){
        Personal_Info head = new Personal_Info("头像",info_us.getHead());
        infoList2.add(head);
        Personal_Info Pname = new Personal_Info("昵称",info_us.getUname());
        infoList1.add(Pname);
        Personal_Info Pbirthday = new Personal_Info("生日",info_us.getBirthday());
        infoList1.add(Pbirthday);
        Personal_Info Psex = new Personal_Info("性别",info_us.getSex());
        infoList1.add(Psex);
        Personal_Info Page = new Personal_Info("年龄",String.valueOf(info_us.getAge()));
        infoList1.add(Page);


    }

//    private void initUserInfos(SQLiteDatabase db){
//
////        Cursor cursor = db.query("user",null,null,null,null,null,null);
////        if (cursor.moveToFirst()){
////
////            Long Uid = cursor.getLong(cursor.getColumnIndex("uid"));
////            String mobile = cursor.getString(cursor.getColumnIndex("phonenumber"));
////            String Head = cursor.getString(cursor.getColumnIndex("head"));
////            String Uname = cursor.getString(cursor.getColumnIndex("uname"));
////            String Birthday = cursor.getString(cursor.getColumnIndex("birthday"));
////            String Sex = cursor.getString(cursor.getColumnIndex("sex"));
////            int age = cursor.getInt(cursor.getColumnIndex("age"));
////
////            info_us = new User(Uid,Uname,mobile,"",Birthday,Sex,age,Head);
////
////        }
////        Logger.d(info_us.toString());
//        info_us = MainActivity.user;

//    }

    private void executeInfo(SQLiteDatabase db){
        values.put("uid",info_us.getUid());
        values.put("uname",info_us.getUname());
        Logger.d(info_us.getUname()+"execute");
        values.put("phonenumber",info_us.getPhonenumber());
        values.put("password","");
        values.put("birthday",info_us.getBirthday());
        values.put("age",info_us.getAge());
        values.put("sex",info_us.getSex());
        values.put("head",info_us.getHead());
        db.update("user",values,"uid = ?",new String[]{String.valueOf(info_us.getUid())});
        values.clear();
    }

    private void updateInfo(){
        infoList1.clear();
        infoList2.clear();
        initInfos();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            //获取返回的数据，这里是android自定义的Uri地址
            Uri selectedImage = data.getData();
            Logger.d(selectedImage.toString());
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            //获取选择照片的数据视图
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            //从数据视图中获取已选择图片的路径
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //将图片上传到服务器上
            Logger.d(picturePath);
            path = picturePath;
            upLoadPicToNet(path);
            Logger.d(path);

        }

    }

    public void upLoadPicToNet(String PictureUrl) {
        File file = new File(BitmapUtil.compressImage(PictureUrl));
        final RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Logger.d(file.getName());
        Logger.d(info_us.getPhonenumber());
        UploadService.uploadFileWithRequestBody(body,MainActivity.user.getPhonenumber())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Logger.d(throwable.toString());
                        ToastUtils.showToast("服务器异常！");
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Logger.d("status="+ baseResponse.getStatus());
                        if(baseResponse.getStatus() == 0){
                            Logger.d("上传成功！");
                        }else {
                            Logger.d("上传失败！");
                        }
                        info_us.setHead(baseResponse.getData());
                        updateInfo();
                        updateUserInfo(info_us);
                        headAdapter.notifyDataSetChanged();
                    }
                });


    }

    public void updateUserInfo(User user){
        Log_RegService.UpdateByPhone(user.getUid(),user.getUname(),user.getPhonenumber(),user.getBirthday(),user.getSex(),user.getAge(),user.getHead())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Logger.d(throwable.toString());
                    }

                    @Override
                    public void onNext(Integer baseInfo) {
                        if (baseInfo==0){
                            ToastUtils.showToast("修改成功！");
                        }else{
                            ToastUtils.showToast("网络异常！");
                        }

                    }
                });
    }

}
