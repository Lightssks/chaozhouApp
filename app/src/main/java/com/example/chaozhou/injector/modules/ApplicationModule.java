package com.example.chaozhou.injector.modules;

import android.content.Context;

import com.example.chaozhou.AndroidApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final AndroidApplication mApplication;


    public ApplicationModule(AndroidApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication.getApplication();
    }

}
