package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.injector.modules.HistoryListModule;
import com.example.chaozhou.module.history.list.HistoryListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = HistoryListModule.class)
public interface HistoryListComponent {
    void inject(HistoryListFragment fragment);
}
