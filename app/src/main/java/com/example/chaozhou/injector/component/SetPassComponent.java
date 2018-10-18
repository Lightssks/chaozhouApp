package com.example.chaozhou.injector.component;


import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.SetPassModule;
import com.example.chaozhou.module.register.setPass.SetPassActivity;

import dagger.Component;

/**
 * Created by yfj on 18-4-12.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = SetPassModule.class)
public interface SetPassComponent {

    void inject(SetPassActivity activity);
}