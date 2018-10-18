package com.example.chaozhou.injector.component;

import android.content.Context;

import com.example.chaozhou.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();
}
