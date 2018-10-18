package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.PlaceInfoModule;
import com.example.chaozhou.module.place.detail.PlaceInfoActivity;

import dagger.Component;

@PerActivity
@Component(modules = PlaceInfoModule.class)
public interface PlaceInfoComponent {
    void inject(PlaceInfoActivity activity);
}
