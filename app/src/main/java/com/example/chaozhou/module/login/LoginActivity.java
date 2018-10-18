package com.example.chaozhou.module.login;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.BaseInfo;
import com.example.chaozhou.injector.component.DaggerLoginComponent;
import com.example.chaozhou.injector.modules.LoginModule;
import com.example.chaozhou.local.Dao.MyDatabaseHelper;
import com.example.chaozhou.local.table.User;
import com.example.chaozhou.module.base.BaseActivity;
import com.example.chaozhou.module.home.MainActivity;
import com.example.chaozhou.module.register.get_inputCode.Get_inputCodeActivity;
import com.example.chaozhou.utils.ClearEditText;
import com.example.chaozhou.utils.EditTextListener;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.chaozhou.AndroidApplication.getAppComponent;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, View.OnFocusChangeListener {

    @BindView(R.id.user)
    ClearEditText user;
    @BindView(R.id.pass)
    ClearEditText pass;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forget)
    TextView forget;


    //SharedPreferences 用于记住密码 数据存储
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;


    //用来判断登录按钮是否可点击
    boolean user_size = false;
    boolean pass_size = false;

    //状态码  state  -1/0
    int state = -1;
    private MyDatabaseHelper dbHelper;
    public User us;



    //其他页面只需要调用这个方法就可以跳转到登录页面，例如  xxxActivity.launch(LoginActivity.this);
    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
        ((Activity) context).finish();
    }
    //


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }



    @Override
    protected void initInjector() {
        DaggerLoginComponent
                .builder()
                .applicationComponent(getAppComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        user.setOnFocusChangeListener(this);
        user.addTextChangedListener(userTextWatcher);
        pass.setOnFocusChangeListener(this);
        pass.addTextChangedListener(passTextWatcher);

        //记住密码
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = findViewById(R.id.remember_pass);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember){
            String mobile = pref.getString("mobile", "");
            String password = pref.getString("password", "");
            user.setText(mobile);
            pass.setText(password);
            rememberPass.setChecked(true);
        }


    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void loadInfo(BaseInfo info) {
        dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        state = info.getStatus();
        Logger.d(String.valueOf(state));
        if(state==0){
            us = info.getUser();
            String u_id =String.valueOf(us.getUid());
            db.delete("user","uid= ?", new String[]{u_id});
            Logger.d(us.toString());
            values.put("uid",us.getUid());
            values.put("uname",us.getUname());
            values.put("phonenumber",user.getText().toString());
            values.put("password","");
            values.put("birthday",us.getBirthday());
            values.put("age",us.getAge());
            values.put("sex",us.getSex());
            values.put("head",us.getHead());
            db.insert("user",null,values);
            values.clear();
            us.setPhonenumber(user.getText().toString());
            MainActivity.launch(this,us);
            finish();
        }else if (state == -1){

            user.setText("");
            pass.setText("");
            ToastUtils.showToast("输入账户/密码错误，请重新输入");
        }

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showAnimation(String msg) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        user.startAnimation(shake);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //进入到这个页面,如果账号输入框为空,则账号输入框自动获得焦点,并弹出键盘
        //如果两个输入框都不为空,则登录按钮可点击
        if (!user.getText().toString().equals("") && !pass.getText().toString().equals("")) {
            user_size = true;
            pass_size = true;
            login.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                login.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.style_btn_login));
            }
        } else if (user.getText().toString().equals("")) {
            EditTextListener.showSoftInputFromWindow(LoginActivity.this, user);
        }
    }


    //注册用的   yfj  18-4-12
    @OnClick({R.id.register, R.id.login, R.id.forget})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.register:
                Get_inputCodeActivity.launch(LoginActivity.this,"register");
                break;
            case R.id.login:
                editor = pref.edit();
                if (rememberPass.isChecked()){  //检查复选框是否被选中
                    editor.putBoolean("remember_password", true);
                    editor.putString("mobile",user.getText().toString());
                    editor.putString("password",pass.getText().toString());
                } else {
                    editor.clear();
                }
                editor.apply();
                mPresenter.getUserData(user.getText().toString(), pass.getText().toString());
                break;

            case R.id.forget:
                Get_inputCodeActivity.launch(LoginActivity.this,"forget");
        }
    }

    /**
     * 焦点监听
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.user:
                if (hasFocus) {
                    if (user.getText().toString().equals("")) {
                        user.setDrawableVisible(false);
                        EditTextListener.noTextAndHasFocus(user, LoginActivity.this, R.mipmap.ic_account_blue);
                    } else {
                        EditTextListener.hasTextAndFocus(user, LoginActivity.this, R.mipmap.ic_account_blue, R.mipmap.qingchu3);
                    }

                } else {
                    user.setDrawableVisible(false);
                    EditTextListener.noFocus(user, LoginActivity.this, R.mipmap.ic_account);
                }
                break;
            case R.id.pass:
                if (hasFocus) {
                    if (pass.getText().toString().equals("")) {
                        pass.setDrawableVisible(false);
                        EditTextListener.noTextAndHasFocus(pass, LoginActivity.this, R.mipmap.ic_password_blue);
                    } else {
                        EditTextListener.hasTextAndFocus(pass, LoginActivity.this, R.mipmap.ic_password_blue, R.mipmap.qingchu3);
                    }
                } else {
                    pass.setDrawableVisible(false);
                    EditTextListener.noFocus(pass, LoginActivity.this, R.mipmap.ic_password);
                }
                break;
        }
    }

    /**
     * 账号输入框的文字改变监听
     */
    TextWatcher userTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                user_size = true;
                //有文字，有焦点(显示蓝色,和清除)
                if (user.hasFocus()) {
                    EditTextListener.hasTextAndFocus(user, LoginActivity.this, R.mipmap.ic_account_blue, R.mipmap.qingchu3);
                } else {
                    //没有焦点（显示灰色,不显示清除）
                    user.setDrawableVisible(false);
                    EditTextListener.noTextAndHasFocus(user, LoginActivity.this, R.mipmap.ic_account);
                }
            } else {
                user_size = false;
                //没有文字,有焦点(显示蓝色,不显示清除)
                if (user.hasFocus()) {
                    user.setDrawableVisible(false);
                    EditTextListener.noTextAndHasFocus(user, LoginActivity.this, R.mipmap.ic_account_blue);
                } else {
                    //没有焦点（显示灰色,不显示清除）
                    user.setDrawableVisible(false);
                    EditTextListener.noFocus(user, LoginActivity.this, R.mipmap.ic_account);
                }
            }
            if (user_size && pass_size && s.length()==10) {
                login.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.style_btn_login));
                }
            } else {
                login.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.style_btn_enable));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() < 11 ){
                user.setTextColor(ContextCompat.getColor(LoginActivity.this, android.R.color.holo_red_light));
            }else if(s.length() >= 10){
                s.delete(11,s.length());
                user.setTextColor(ContextCompat.getColor(LoginActivity.this, android.R.color.black));
            }

        }
    };


    /**
     * 密码输入框的文字改变监听
     */
    private TextWatcher passTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                pass_size = true;
                //有文字，有焦点(显示蓝色,和清除)
                if (pass.hasFocus()) {
                    EditTextListener.hasTextAndFocus(pass, LoginActivity.this, R.mipmap.ic_password_blue, R.mipmap.qingchu3);
                } else {
                    //没有焦点（显示灰色,不显示清除）
                    pass.setDrawableVisible(false);
                    EditTextListener.noTextAndHasFocus(pass, LoginActivity.this, R.mipmap.ic_password);
                }
            } else {
                pass_size = false;
                //没有文字,有焦点(显示蓝色,不显示清除)
                if (pass.hasFocus()) {
                    pass.setDrawableVisible(false);
                    EditTextListener.noTextAndHasFocus(pass, LoginActivity.this, R.mipmap.ic_password_blue);
                } else {
                    //没有焦点（显示灰色,不显示清除）
                    pass.setDrawableVisible(false);
                    EditTextListener.noFocus(pass, LoginActivity.this, R.mipmap.ic_password);
                }
            }
            if (user_size && pass_size) {
                login.setEnabled(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.style_btn_login));
                }
            } else {
                login.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    login.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.style_btn_enable));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
