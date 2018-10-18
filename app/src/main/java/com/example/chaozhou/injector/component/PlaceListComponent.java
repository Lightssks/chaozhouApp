package com.example.chaozhou.injector.component;

import com.example.chaozhou.injector.PerFragment;
import com.example.chaozhou.injector.modules.PlaceListModule;
import com.example.chaozhou.module.place.list.PlaceListFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = PlaceListModule.class)
public interface PlaceListComponent {
    void inject(PlaceListFragment fragment);
}
