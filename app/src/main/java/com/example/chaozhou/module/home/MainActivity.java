package com.example.chaozhou.module.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.BaseInfo;
import com.example.chaozhou.api.service.Log_RegService;
import com.example.chaozhou.local.table.User;
import com.example.chaozhou.module.base.BaseActivity;
import com.example.chaozhou.module.goods.list.GoodsListFragment;
import com.example.chaozhou.module.history.list.HistoryListFragment;
import com.example.chaozhou.module.hotel.list.HotelListFragment;
import com.example.chaozhou.module.personal_Info.Message_Notification.Notification_MsgActivity;
import com.example.chaozhou.module.personal_Info.detail.Personal_InfoActivity;
import com.example.chaozhou.module.personal_Info.list.Share_ListActivity;
import com.example.chaozhou.module.place.list.PlaceListFragment;
import com.example.chaozhou.module.share.list.ShareListFragment;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.utils.ToastUtils;
import com.hjm.bottomtabbar.BottomTabBar;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import rx.Subscriber;

import static com.example.chaozhou.AndroidApplication.getContext;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_tab_bar)
    BottomTabBar bottomTabBar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.container)
    FrameLayout container;

    private RelativeLayout headerView;
    public static User user;
    private SparseArray<String> mSparseTags = new SparseArray<>();
    private long exitTime = 0;
    private int mItemId = -1;
    private ImageView icon_image;
    private ImageView iconh_back;
    private TextView icon_name;
    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
           /* switch (msg.what) {
                case R.id.nav_news:
                    replaceFragment(R.id.fl_container, new NewsMainFragment(), mSparseTags.get(R.id.nav_news));
                    break;
                case R.id.nav_photos:
                    replaceFragment(R.id.fl_container, new PhotoMainFragment(), mSparseTags.get(R.id.nav_photos));
                    break;
                case R.id.nav_videos:
                    replaceFragment(R.id.fl_container, new VideoMainFragment(), mSparseTags.get(R.id.nav_videos));
                    break;
                case R.id.nav_setting:
                    SettingsActivity.launch(HomeActivity.this);
                    break;
            }*/
            mItemId = -1;
            return true;
        }
    });

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

        headerView = (RelativeLayout) navView.getHeaderView(0);
        //iconh_back = headerView.findViewById(R.id.h_back);
        icon_image = headerView.findViewById(R.id.icon_image);
        icon_name = headerView.findViewById(R.id.icon_name);
        //ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + user.getHead(), iconh_back, DefIconFactory.provideIcon());
        ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + user.getHead(), icon_image, DefIconFactory.provideIcon());
        icon_name.setText(user.getUname());


        bottomTabBar.init(getSupportFragmentManager())
                .setImgSize(65 ,65)
                .setFontSize(10)
                .setTabPadding(4,6,10)
                .setChangeColor(Color.rgb(68,68,68),Color.rgb(176,176,176))
                .addTabItem("历史",R.drawable.ic_tab_history, HistoryListFragment.class)
                .addTabItem("特产",R.drawable.ic_tab_goods,GoodsListFragment.class)
                .addTabItem("住宿",R.drawable.ic_tab_hotel,HotelListFragment.class)
                .addTabItem("景点",R.drawable.ic_tab_place,PlaceListFragment.class)
                .addTabItem("分享",R.drawable.ic_tab_share, ShareListFragment.class)
                .setTabBarBackgroundColor(Color.WHITE)
                .isShowDivider(false);
        _initDrawerLayout(drawerLayout, navView);
    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.isChecked()) {
            return true;
        }
        mItemId = item.getItemId();

        if(mItemId == R.id.nav_1){

            Intent intent = new Intent(this,Personal_InfoActivity.class);
            startActivity(intent);

        }else if(mItemId == R.id.nav_2){

            Intent intent1 = new Intent(this,Share_ListActivity.class);
            startActivity(intent1);
            mItemId = -1;

        }else if(mItemId == R.id.nav_4){
            Intent intent = new Intent(this, Notification_MsgActivity.class);
            startActivity(intent);
            mItemId = -1;

        }else if(mItemId == R.id.nav_5){

            ToastUtils.showToast("尚未开发，敬请期待！");

        }
        return false;
    }

   /* @Override
    public void onBackPressed() {
        // 获取堆栈里有几个
       // final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
       *//* if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }*//* *//*else if (stackEntryCount == 1) {
            // 如果剩一个说明在主页，提示按两次退出app
            _exit();
        }*//* *//*else {
           *//**//* // 获取上一个堆栈中保存的是哪个页面，根据name来设置导航项的选中状态
            final String tagName = getSupportFragmentManager().getBackStackEntryAt(stackEntryCount - 2).getName();
            int index = mSparseTags.indexOfValue(tagName);
            navView.setCheckedItem(mSparseTags.keyAt(index));*//*
            _exit();
            super.onBackPressed();
        //}
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   /* *
     * 初始化 DrawerLayout
     *
     * @param drawerLayout DrawerLayout
     * @param navView      NavigationView
     * */

    private void _initDrawerLayout(DrawerLayout drawerLayout, NavigationView navView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            drawerLayout.setClipToPadding(false);
        }
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                mHandler.sendEmptyMessage(mItemId);
            }
        });
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
            //System.exit(0);
        }
    }

    /**
     * 退出
    */
    /*private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }*/

    public static void launch(Context context, User us) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        user = us;
        Logger.d(user.toString());
        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    protected void onResume() {

        updateUserInfo();
        ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + user.getHead(), icon_image, DefIconFactory.provideIcon());
        icon_name.setText(user.getUname());

        super.onResume();
    }

    public void updateUserInfo(){
        Log_RegService.getUserInfoByid(user.getUid())
                .subscribe(new Subscriber<BaseInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Logger.d(throwable.toString());
                    }

                    @Override
                    public void onNext(BaseInfo baseInfo) {
                        if (baseInfo.getStatus()==0){
                            user = baseInfo.getUser();
                            ToastUtils.showToast("修改成功！");
                        }else{
                            ToastUtils.showToast("网络异常！");
                        }

                    }
                });
    }
}
