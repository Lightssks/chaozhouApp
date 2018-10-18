package com.example.chaozhou;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.chaozhou.api.RetrofitService;

import com.example.chaozhou.injector.component.ApplicationComponent;
import com.example.chaozhou.injector.component.DaggerApplicationComponent;
import com.example.chaozhou.injector.modules.ApplicationModule;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * Created by long on 2016/8/19.
 * Application
 */

public class AndroidApplication extends Application{

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;
    private static ApplicationComponent sAppComponent;
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        share_init();
        _initInjector();
        _initConfig();
        getScreenSize();
    }

    public static Context getContext() {
        return sContext;
    }

    public Context getApplication() { return sContext; }

    public static ApplicationComponent getAppComponent() {
        return sAppComponent;
    }


    private void _initInjector() {
        // 这里不做注入操作，只提供一些全局单例数据
        sAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void share_init(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    /**
     * 初始化配置
     */
    private void _initConfig() {
        Logger.init("LogTAG");
        RetrofitService.init();
        ToastUtils.init(getApplication());

    }


}
