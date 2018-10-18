package com.example.chaozhou.injector.component;


import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.LoginModule;
import com.example.chaozhou.module.login.LoginActivity;

import dagger.Component;

/**
 * Created by yfj on 18-4-12.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

}
