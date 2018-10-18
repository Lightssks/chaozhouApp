package com.example.chaozhou.injector.modules;


import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.module.register.get_inputCode.Get_inputCodePresenter;
import com.example.chaozhou.module.register.get_inputCode.IGet_inputCodeView;

import dagger.Module;
import dagger.Provides;

@Module
public class Get_inputCodeModule {

    private IGet_inputCodeView mView;

    public Get_inputCodeModule(IGet_inputCodeView mView){
        this.mView = mView;
    }
    @PerActivity
    @Provides
    public Get_inputCodePresenter  providePresenter(){
        return new Get_inputCodePresenter(mView);
    }
}
