package com.example.chaozhou.module.register.get_inputCode;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.injector.component.DaggerGet_inputCodeComponent;
import com.example.chaozhou.injector.modules.Get_inputCodeModule;
import com.example.chaozhou.module.base.BaseActivity;
import com.example.chaozhou.module.register.setPass.SetPassActivity;
import com.example.chaozhou.utils.ClearEditText;
import com.example.chaozhou.utils.TimeUtils;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.chaozhou.AndroidApplication.getAppComponent;

public class Get_inputCodeActivity extends BaseActivity<Get_inputCodePresenter> implements IGet_inputCodeView {

    private static final String TAG = "Get_inputCodeActivity";
    private static final String INPUT = "input";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.phone)
    ClearEditText phone;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.next)
    Button next;

    String input;
    String mobile;

    TimeUtils time;

    /**
     * 跳转activity
     *
     * @param context 上个页面的context
     */
    public static void launch(Context context,String input) {
        Intent intent = new Intent(context, Get_inputCodeActivity.class);
        intent.putExtra(INPUT, input);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_get_input_code;
    }

    @Override
    protected void initInjector() {
        Intent intent = getIntent();
        input = intent.getStringExtra("input");
        DaggerGet_inputCodeComponent
                .builder()
                .applicationComponent(getAppComponent())
                .get_inputCodeModule(new Get_inputCodeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {

        phone.addTextChangedListener(listener);
        code.addTextChangedListener(listener);
//        time = new TimeUtils(60000, 1000, send, Get_inputCodeActivity.this);
//        time.start();
//        if (!input.equals("modify")) {
//            time.start();
//        }

    }

    @Override
    protected void onResume() {

        send.setEnabled(true);
        super.onResume();
        Log.e(TAG, "onResume");
        //判断是注册还是忘记密码还是修改密码,还是修改手机号
        if (input.equals("register")) {
            initToolBar(toolbar, true, "注册账号");
        } else if (input.equals("forget")) {
            initToolBar(toolbar, true, "忘记密码");
        } else if (input.equals("modify")) {
            send.setEnabled(true);
        }
        try {
            phone.setText(mobile);
         }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void updateViews(boolean isRefresh) {

    }



    /**
     * 输入框文字改变监听
     */
    TextWatcher listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            phone.setTextColor(ContextCompat.getColor(Get_inputCodeActivity.this, android.R.color.black));
            if (s.length() > 0) {

                next.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(Get_inputCodeActivity.this, R.drawable.style_btn_login));
                }
            } else {
                next.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackground(ContextCompat.getDrawable(Get_inputCodeActivity.this, R.drawable.style_btn_enable));
                }
            }
        }
        @Override
        public void afterTextChanged(Editable s) {

            mobile = phone.getText().toString();
            if(s.length() < 11 ){
                phone.setTextColor(ContextCompat.getColor(Get_inputCodeActivity.this, android.R.color.holo_red_light));
            }else if(s.length() >11){
                s.delete(11,s.length());
                phone.setTextColor(ContextCompat.getColor(Get_inputCodeActivity.this, android.R.color.black));
            }
        }

    };

    @OnClick({R.id.send, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                Logger.d("click send");
                time = new TimeUtils(60000, 1000, send, Get_inputCodeActivity.this);
                time.start();
                mPresenter.getData(phone.getText().toString());
                break;
            case R.id.next:
                mPresenter.checkCode(code.getText().toString());

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        if (time != null) {
            time = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }


    @Override
    public void loadInfo(String msg) {
        if(msg.toString().equals("-2")){
            ToastUtils.showToast("该手机已注册！");

        }else if(msg.equals(code.getText().toString())){
            SetPassActivity.launch(Get_inputCodeActivity.this, input,mobile);
        } else if(code.getText().toString().equals("")) {

         }else {
                Animation shake = AnimationUtils.loadAnimation(Get_inputCodeActivity.this, R.anim.shake);
                code.startAnimation(shake);
                ToastUtils.showToast("验证码有误！");
                phone.setText(mobile);
                code.setText("");

            }


    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showAnimation(String msg) {
        Animation shake = AnimationUtils.loadAnimation(Get_inputCodeActivity.this, R.anim.shake);
        phone.startAnimation(shake);
        phone.setTextColor(ContextCompat.getColor(Get_inputCodeActivity.this, android.R.color.holo_red_light));

    }

}
