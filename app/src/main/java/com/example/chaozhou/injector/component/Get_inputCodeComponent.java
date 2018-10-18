package com.example.chaozhou.injector.component;


import com.example.chaozhou.injector.PerActivity;
import com.example.chaozhou.injector.modules.Get_inputCodeModule;
import com.example.chaozhou.module.register.get_inputCode.Get_inputCodeActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = Get_inputCodeModule.class)
public interface Get_inputCodeComponent {
    void inject(Get_inputCodeActivity activity);
}
