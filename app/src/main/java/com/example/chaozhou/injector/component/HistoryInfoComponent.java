package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.HistoryInfoModule;
import com.example.chaozhou.module.history.detail.HistoryInfoActivity;

import dagger.Component;

@PerActivity
@Component(modules = HistoryInfoModule.class)
public interface HistoryInfoComponent {
    void inject(HistoryInfoActivity activity);
}
