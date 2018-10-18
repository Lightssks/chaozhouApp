package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.GoodsInfoModule;
import com.example.chaozhou.module.goods.detail.GoodsInfoActivity;

import dagger.Component;

@PerActivity
@Component(modules = GoodsInfoModule.class)
public interface GoodsInfoComponent {
    void inject(GoodsInfoActivity activity);
}
