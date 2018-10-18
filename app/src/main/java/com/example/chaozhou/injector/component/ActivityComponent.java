package com.example.chaozhou.injector.component;

import android.app.Activity;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.ActivityModule;

import dagger.Component;

/**
 * Created by long on 2016/8/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

}
