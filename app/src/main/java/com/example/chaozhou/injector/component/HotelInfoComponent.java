package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.HotelInfoModule;
import com.example.chaozhou.module.hotel.detail.HotelInfoActivity;

import dagger.Component;

@PerActivity
@Component(modules = HotelInfoModule.class)
public interface HotelInfoComponent {
    void inject(HotelInfoActivity activity);
}
