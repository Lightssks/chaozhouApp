package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.injector.modules.GoodsListModule;
import com.example.chaozhou.module.goods.list.GoodsListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = GoodsListModule.class)
public interface GoodsListComponent {
    void inject(GoodsListFragment fragment);
}
