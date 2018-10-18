package com.example.chaozhou.injector.modules;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.module.register.setPass.ISetPassView;
import com.example.chaozhou.module.register.setPass.SetPassPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SetPassModule {

    private ISetPassView mView;

    public SetPassModule(ISetPassView mView){
        this.mView = mView;
    }

    @PerActivity
    @Provides
    public SetPassPresenter providePresenter(){
        return new SetPassPresenter(mView);
    }

}