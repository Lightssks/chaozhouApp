package com.example.chaozhou.injector.modules;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.module.login.ILoginView;
import com.example.chaozhou.module.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;


/**
 * Created by yfj on 18-4-12.
 */
@Module
public class LoginModule {
    private ILoginView mView;

    public LoginModule(ILoginView mView){
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public LoginPresenter providePresenter(){
        return new LoginPresenter(mView);
    }
}
