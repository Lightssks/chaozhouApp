package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.injector.modules.HotelListModule;
import com.example.chaozhou.module.hotel.list.HotelListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = HotelListModule.class)
public interface HotelListComponent {
    void inject(HotelListFragment fragment);
}
